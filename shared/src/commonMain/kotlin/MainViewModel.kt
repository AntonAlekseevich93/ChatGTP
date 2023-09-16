import androidx.compose.runtime.Stable
import com.aallam.openai.api.BetaOpenAI
import com.aallam.openai.api.chat.ChatCompletion
import com.aallam.openai.api.chat.ChatCompletionRequest
import com.aallam.openai.api.chat.ChatMessage
import com.aallam.openai.api.chat.ChatRole
import com.aallam.openai.api.http.Timeout
import com.aallam.openai.api.model.Model
import com.aallam.openai.api.model.ModelId
import com.aallam.openai.client.OpenAI
import composables.messages.MessageType
import data.AppScreenState
import data.ConversationUiState
import data.MessageVo
import data.exampleUiState
import data.toChatMessage
import di.Inject.instance
import io.ktor.client.*
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import repository.Repository
import themes.AppTheme
import ui_state.CommonUiState
import ui_state.QuoteDataUiState
import kotlin.time.Duration.Companion.seconds

@Stable
class MainViewModel() {
    private val scope = CoroutineScope(Dispatchers.Unconfined + SupervisorJob())
    private val repository = Repository(instance())
    private var authorizationKey: String = ""
    private var selectedThemeFromStorage = AppTheme.LIGHT
    private val _keyList: MutableStateFlow<MutableList<String>> = MutableStateFlow(mutableListOf())
    private val modelId = ModelId(MODEL_GPT)
    private var openAI: OpenAI? = null
    private var gptModel: Model? = null
    private val defaultQuoteData = QuoteDataUiState(
        showingQuote = false,
        parentMessage = "",
        parentMessagePosition = -1
    )
    private var lastCreatedMessageID = DEFAULT_MESSAGE_ID
    private var errorMessage: MessageVo? = null
    private var isLoadingMessagesFromDb = false
    val commonUiState: CommonUiState = CommonUiState()
    val keyList: StateFlow<List<String>> = _keyList

    init {
        getLastAuthorizationKeyFromDb()
        scope.launch {
            selectedThemeFromStorage = getSelectedThemeFromStorage()
        }
    }

    private val _conversationUiState: MutableStateFlow<ConversationUiState> =
        MutableStateFlow(exampleUiState.getValue("Chat GPT-4"))
    val conversationUiState: StateFlow<ConversationUiState> = _conversationUiState
    var notificationUiState: ((notificationMessage: String) -> Unit)? = null

    private val httpClient = HttpClient {
        install(HttpTimeout)

        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
            })

        }
    }

    private val _waitingForResponseState: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val waitingForResponseState: StateFlow<Boolean> = _waitingForResponseState

    private val _screenState: MutableStateFlow<AppScreenState> =
        if (authorizationKey.isNotEmpty())
            MutableStateFlow(AppScreenState.CHAT)
        else
            MutableStateFlow(AppScreenState.AUTHORIZATION)
    val screenState: StateFlow<AppScreenState> = _screenState

    private val _themeMode: MutableStateFlow<AppTheme> = MutableStateFlow(selectedThemeFromStorage)
    val themeMode: StateFlow<AppTheme> = _themeMode

    init {
        getLastMessagesIfExist()
    }

    fun logIn(key: String) {
        if (key.isNotEmpty() && key.isNotBlank()) {
            initializeOpenAi(key)
            scope.launch {
                repository.saveAuthorizationKey(key = key, modelGpt = MODEL_GPT)
            }
            authorizationKey = key
            _keyList.value.add(key)
            _screenState.value = AppScreenState.CHAT
        }
    }

    fun switchTheme() {
        scope.launch {
            when (_themeMode.value) {
                AppTheme.DARK -> {
                    _themeMode.value = AppTheme.LIGHT
                    repository.saveSelectedTheme(AppTheme.LIGHT.id)
                }

                else -> {
                    _themeMode.value = AppTheme.DARK
                    repository.saveSelectedTheme(AppTheme.DARK.id)
                }
            }
        }
    }

    fun sendMessage(message: String) {
        if (message.messageIsCommand()) {
            scope.launch {
                executeCommand(message)
            }
        } else {
            if (openAI != null && gptModel != null) {
                _waitingForResponseState.value = true
                val messageVo = MessageVo(
                    id = getNewMessageId(),
                    messageType = MessageType.USER,
                    content = message
                )
                _conversationUiState.value.addMessageToBeginningOfList(messageVo)

                scope.launch {
                    launch {
                        repository.insertMessageToDb(messageVo)
                    }
                    sendGptRequest(message)
                }
            } else if (errorMessage != null) {
                _conversationUiState.value.addMessageToBeginningOfList(errorMessage!!)
            } else {
                _conversationUiState.value.addMessageToBeginningOfList(
                    MessageVo(
                        id = DEFAULT_MESSAGE_ID,
                        messageType = MessageType.SYSTEM,
                        content = "Что-то пошло не так!\nПроверьте подключение к интернету и повторите попытку."
                    )
                )
            }
        }
    }

    fun sendMessageWithQuote(
        message: String,
        parentMessageId: Long,
        fromQuoteScreen: Boolean = false
    ) {
        if (message.messageIsCommand()) {
            scope.launch {
                executeCommand(message)
            }
        } else {
            if (openAI != null && gptModel != null) {
                closeQuote()
                _waitingForResponseState.value = true
                val childMessageId = getNewMessageId()
                scope.launch {
                    val parentMessage = repository.getMessageById(parentMessageId)
                    val messageVo = MessageVo(
                        id = childMessageId,
                        messageType = MessageType.USER,
                        content = message,
                        parentMessageId = parentMessageId,
                        parentMessageText = parentMessage?.content
                    )
                    _conversationUiState.value.addMessageToBeginningOfList(messageVo)
                    if (fromQuoteScreen) {
                        _conversationUiState.value.addMessageToQuoteScreen(messageVo)
                    }
                    launch {
                        repository.insertMessageToDb(messageVo)
                        updateChildIdForParentMessage(
                            childMessageId = childMessageId,
                            parentMessageId = parentMessageId
                        )
                    }
                    sendGptRequest(
                        messageRequest = message,
                        parentMessageId = childMessageId, //it`s correct
                        fromQuoteScreen = fromQuoteScreen
                    )
                }
            } else if (errorMessage != null) {
                _conversationUiState.value.addMessageToBeginningOfList(errorMessage!!)
            }
        }
    }

    fun deleteApiKeys() {
        scope.launch {
            repository.deleteApiKeys()
            _keyList.value = mutableListOf()
            _screenState.value = AppScreenState.AUTHORIZATION
        }
    }

    fun closeQuote() {
        commonUiState.quoteDataUiState.value = defaultQuoteData
    }

    fun showQuote(message: String, position: Int, parentMessageId: Long) {
        commonUiState.quoteDataUiState.value = QuoteDataUiState(
            showingQuote = true,
            parentMessage = message,
            parentMessagePosition = position,
            parentMessageId = parentMessageId
        )
    }

    fun openQuoteScreen(childMessageId: Long) {
        scope.launch {
            _conversationUiState.value.apply {
                updateQuoteMessagesBranch(getSortedListMessagesForBranch(childMessageId))
                openQuoteMessagesBranch(clickedMessageId = childMessageId)
            }
        }
    }

    fun closeMessagesThread() {
        _conversationUiState.value.closeQuoteMessagesBranch()
    }

    fun uploadData() {
        if (!isLoadingMessagesFromDb) {
            isLoadingMessagesFromDb = true
            scope.launch {
                try {
                    val lastItem =
                        _conversationUiState.value.messages.last { it.messageType != MessageType.SYSTEM }
                    if (lastItem.id != FIRST_MESSAGE_ID) {
                        val messages =
                            repository.getMessagesFromIdToId(
                                fromId = lastItem.id - SHIFT_ID_BY_ONE,
                                toId = lastItem.id - MESSAGES_ID_OFFSET
                            ).reversed()
                        messages.forEach { message ->
                            withContext(this.coroutineContext) {
                                _conversationUiState.value.addMessage(message)
                            }
                        }
                        isLoadingMessagesFromDb = false
                    }
                } catch (_: Exception) {
                    //nop
                }
            }
        }
    }

    @OptIn(BetaOpenAI::class)
    private suspend fun sendGptRequest(
        messageRequest: String,
        parentMessageId: Long? = null,
        fromQuoteScreen: Boolean = false
    ) {
        openAI?.let { openAI ->
            val chatCompletionRequest = if (parentMessageId == null) {
                ChatCompletionRequest(
                    model = modelId,
                    messages = listOf(
                        ChatMessage(
                            role = ChatRole.User,
                            content = messageRequest
                        )
                    )
                )
            } else {
                getMessageQuotesRequestModel(
                    parentMessageId = parentMessageId,
                    lastUserMessage = messageRequest
                )
            }
            try {
                val completion: ChatCompletion = openAI.chatCompletion(chatCompletionRequest)
                completion.choices.forEach { chatChoice ->
                    chatChoice.message?.content?.let { content ->
                        val gptMessageId = getNewMessageId()
                        val message = MessageVo(
                            id = gptMessageId,
                            messageType = MessageType.GPT,
                            content = content,
                            parentMessageId = parentMessageId,
                            parentMessageText = if (parentMessageId != null) messageRequest else null
                        )
                        _conversationUiState.value.addMessageToBeginningOfList(message)
                        if (fromQuoteScreen) {
                            _conversationUiState.value.addMessageToQuoteScreen(message)
                        }
                        repository.insertMessageToDb(message)
                        sendNotification(message = content)
                        if (parentMessageId != null) {
                            updateChildIdForParentMessage(
                                childMessageId = gptMessageId,
                                parentMessageId = parentMessageId
                            )
                        }
                    }
                }
            } catch (e: Exception) {
                errorMessage = MessageVo(
                    id = -1,
                    messageType = MessageType.SYSTEM,
                    content = "ОШИБКА: ${e.message}"
                )
                _conversationUiState.value.addMessageToBeginningOfList(errorMessage!!)
            } finally {
                _waitingForResponseState.value = false
            }
        }
    }

    private fun getLastMessagesIfExist() {
        scope.launch {
            repository.getLastMessageId()?.let { lastMessageId ->
                val messages = repository.getMessagesFromIdToId(
                    fromId = lastMessageId,
                    toId = lastMessageId - MESSAGES_ID_OFFSET
                )
                if (messages.isNotEmpty()) {
                    lastCreatedMessageID = messages.last().id
                }
                messages.forEach { message ->
                    withContext(this.coroutineContext) {
                        _conversationUiState.value.addMessageToBeginningOfList(message)
                    }
                }
            }
        }
    }

    private fun getLastAuthorizationKeyFromDb() {
        scope.launch {
            val key = repository.getLastAuthorizationKeyFromDb()
            if (key.isNotEmpty()) {
                initializeOpenAi(key)
                authorizationKey = key
                _keyList.value = mutableListOf(key)
            }
        }
    }

    private fun sendNotification(message: String) {
        if (message.isNotEmpty()) {
            val resultMessage = if (message.length > 120) {
                message.substring(0..120).plus("...")
            } else message
            notificationUiState?.invoke(resultMessage)
        }
    }

    private suspend fun getSelectedThemeFromStorage(): AppTheme {
        val defaultTheme = AppTheme.LIGHT
        val selectedThemeId = repository.getSelectedThemeIdOrDefault()
        return if (selectedThemeId != null) {
            when (selectedThemeId) {
                AppTheme.LIGHT.id -> AppTheme.LIGHT
                AppTheme.DARK.id -> AppTheme.DARK
                else -> defaultTheme
            }
        } else {
            defaultTheme
        }
    }

    private fun initializeOpenAi(apiKey: String) {
        scope.launch {
            try {
                openAI = OpenAI(
                    token = apiKey,
                    timeout = Timeout(socket = 60.seconds)
                )
                gptModel = openAI?.model(modelId)
            } catch (e: Exception) {
                errorMessage = MessageVo(
                    id = DEFAULT_MESSAGE_ID,
                    messageType = MessageType.SYSTEM,
                    content = "ОШИБКА: ${e.message}"
                )
                _conversationUiState.value.addMessageToBeginningOfList(errorMessage!!)
            }
        }
    }

    private suspend fun updateChildIdForParentMessage(childMessageId: Long, parentMessageId: Long) {
        repository.updateChildIdForParentMessage(
            childMessageId = childMessageId,
            parentMessageId = parentMessageId
        )
        _conversationUiState.value.updateChildIdForParentMessage(
            childMessageId = childMessageId,
            parentMessageId = parentMessageId
        )
    }

    private fun getNewMessageId(): Long {
        lastCreatedMessageID += 1
        return lastCreatedMessageID
    }

    private suspend fun getMessageForBranch(messageId: Long?): MessageVo? {
        return if (messageId != null) {
            repository.getMessageById(messageId)
        } else null
    }

    private suspend fun getSortedListMessagesForBranch(childMessageId: Long): List<MessageVo> {
        val messagesBranch = mutableMapOf<Long, MessageVo>()
        var searchingKey: Long? = childMessageId
        val parentMessage: MessageVo? = getMessageForBranch(
            getMessageForBranch(childMessageId)?.parentMessageId
        )
        var lastFoundMessageId = searchingKey

        //sampling up
        while (searchingKey != null) {
            val message = getMessageForBranch(searchingKey)
            if (message != null) {
                messagesBranch[searchingKey] = message
                searchingKey = message.parentMessageId
                lastFoundMessageId = message.id
            } else {
                searchingKey = null
            }
        }

        /**
         * This is a temporary solution to how to show the very first message from the thread.
         * If we change the logic of working with the message ID, this code will break
         */
        val mainParentMessage = getMessageForBranch(lastFoundMessageId?.minus(1))
        if (mainParentMessage != null) {
            messagesBranch[mainParentMessage.id] = mainParentMessage
        }

        searchingKey = parentMessage?.childMessageId

        //sampling down
        while (searchingKey != null) {
            val message = getMessageForBranch(searchingKey)
            if (message != null) {
                messagesBranch[searchingKey] = message
                searchingKey = message.childMessageId
            } else {
                searchingKey = null
            }
        }

        return messagesBranch.values.sortedByDescending { it.id }
    }

    @OptIn(BetaOpenAI::class)
    private suspend fun getMessageQuotesRequestModel(
        parentMessageId: Long,
        lastUserMessage: String
    ): ChatCompletionRequest {
        val lastMessage = ChatMessage(
            role = ChatRole.User,
            content = lastUserMessage
        )
        val listMessages = getSortedListMessagesForBranch(parentMessageId)
            .mapNotNull { it.toChatMessage() }.reversed().toMutableList()
        listMessages.add(lastMessage)
        return ChatCompletionRequest(
            model = modelId,
            messages = listMessages
        )
    }

    private fun String.messageIsCommand(): Boolean {
        return if (this.isNotEmpty()) {
            this.first() == COMMAND_SIGN
        } else false
    }

    private suspend fun executeCommand(command: String) {
        when (command) {
            COMMAND_DELETE_ALL -> {
                deleteApiKeys()
                repository.deleteAllMessages()
            }

            COMMAND_DELETE_ALL_MESSAGES -> {
                repository.deleteAllMessages()
            }

            else -> {
                _conversationUiState.value.addMessageToBeginningOfList(
                    MessageVo(
                        id = -1,
                        messageType = MessageType.SYSTEM,
                        content = "Command not found"
                    )
                )
            }
        }
    }

    companion object {
        private const val MODEL_GPT = "gpt-4"
        private const val FIRST_MESSAGE_ID = 0L
        private const val SHIFT_ID_BY_ONE = 1L
        private const val MESSAGES_ID_OFFSET = 15L
        private const val COMMAND_SIGN = '$'
        private const val COMMAND_DELETE_ALL = "\$delete all"
        private const val COMMAND_DELETE_ALL_MESSAGES = "\$delete all messages"
        const val DEFAULT_MESSAGE_ID = -1L
    }
}

