import androidx.compose.runtime.Stable
import composables.messages.MessageType
import data.AppScreenState
import data.BodyDto
import data.ConversationUiState
import data.MessageVo
import data.MessagesDto
import data.ResponseDto
import data.exampleUiState
import di.Inject.instance
import io.ktor.client.*
import io.ktor.client.call.body
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.retry
import io.ktor.client.plugins.timeout
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
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

@Stable
class MainViewModel() {
    private val scope = CoroutineScope(Dispatchers.Unconfined + SupervisorJob())
    private val repository = Repository(instance())
    private var authorizationKey: String = ""
    private var selectedThemeFromStorage = AppTheme.LIGHT
    private val _keyList: MutableStateFlow<MutableList<String>> = MutableStateFlow(mutableListOf())
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
        getMessages()
    }

    fun logIn(key: String) {
        if (key.isNotEmpty() && key.isNotBlank()) {
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
        _waitingForResponseState.value = true
        val messageVo = MessageVo(
            messageType = MessageType.USER,
            content = message
        )
        _conversationUiState.value.addMessage(messageVo)
        scope.launch {
//            launch {
//                delay(2000L)
//                sendRequestTesting()
//            }
            launch {
                repository.insertMessageToDb(messageVo)
            }
            sendRequest(message)

        }
    }

    fun deleteApiKeys() {
        scope.launch {
            repository.deleteApiKeys()
            _keyList.value = mutableListOf()
            _screenState.value = AppScreenState.AUTHORIZATION
        }
    }

    private suspend fun sendRequestTesting() {
        _conversationUiState.value.addMessage(
            MessageVo(
                messageType = MessageType.GPT,
                content = "Ответ чата GPt",
            )
        )
        _waitingForResponseState.value = false
        sendNotification(message = "I'm sorry for the misunderstanding, but as an AI text-based model developed by OpenAI")
    }

    private suspend fun sendRequest(messageRequest: String) {
        val httpResponse = httpClient
            .post("https://api.openai.com/v1/chat/completions") {
                timeout {
                    requestTimeoutMillis = 60000
                }
                url {
                    contentType(ContentType.Application.Json)
                    parameters.append("model", "gpt-4")
                    retry {
                        retryOnException(retryOnTimeout = true, maxRetries = 10)
                    }
                    header(
                        "Authorization",
                        "Bearer $authorizationKey"
                    )
                    setBody(
                        BodyDto(
                            model = MODEL_GPT,
                            messages = listOf(
                                MessagesDto(
                                    role = USER_ROLE,
                                    content = messageRequest
                                )
                            )
                        )
                    )
                }
            }.body<ResponseDto>()

        if (httpResponse.error?.message != null && httpResponse.error.message.isNotEmpty()) {
            val message = MessageVo(
                messageType = MessageType.SYSTEM,
                content = httpResponse.error.message
            )
            _conversationUiState.value.addMessage(message)
            _waitingForResponseState.value = false
            sendNotification(message = httpResponse.error.message)
        } else {
            httpResponse.choices?.forEach {
                it.message?.content?.let { content ->
                    val message = MessageVo(
                        messageType = MessageType.GPT,
                        content = content,
                    )
                    _conversationUiState.value.addMessage(message)
                    _waitingForResponseState.value = false
                    repository.insertMessageToDb(message)
                    sendNotification(message = content)
                }
            }
        }
    }

    private fun getMessages() {
        scope.launch {
            repository.getAllMessagesFromDb().forEach {
                withContext(this.coroutineContext) {
                    _conversationUiState.value.addMessage(it)
                }
            }
        }
    }

    private fun getLastAuthorizationKeyFromDb() {
        scope.launch {
            val key = repository.getLastAuthorizationKeyFromDb()
            if (key.isNotEmpty()) {
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

    companion object {
        private const val MODEL_GPT = "gpt-4"
        private const val USER_ROLE = "user"
    }
}

