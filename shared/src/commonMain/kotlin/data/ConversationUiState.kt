package data

import MainViewModel.Companion.DEFAULT_MESSAGE_ID
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.toMutableStateList

class ConversationUiState(
    val appBarTitle: String,
    initialMessages: List<MessageVo>,
) {
    private val _messages: MutableList<MessageVo> = initialMessages.toMutableStateList()
    val messages: List<MessageVo> = _messages

    private val _showQuoteMessagesBranch: MutableState<Boolean> = mutableStateOf(false)
    val showQuoteMessagesBranch: State<Boolean> = _showQuoteMessagesBranch

    private val _quoteMessagesId: MutableState<Long> = mutableStateOf(DEFAULT_MESSAGE_ID)
    val quoteMessagesId: State<Long> = _quoteMessagesId

    private val _quoteMessagesBranch: MutableList<MessageVo> =
        emptyList<MessageVo>().toMutableStateList()
    val quoteMessagesBranch: List<MessageVo> = _quoteMessagesBranch

    fun addMessageToBeginningOfList(message: MessageVo) {
        _messages.add(0, message) // Add to the beginning of the list
    }

    fun addMessage(message: MessageVo) {
        _messages.add(message) // Add to the end of the list
    }

    fun updateQuoteMessagesBranch(list: List<MessageVo>) {
        _quoteMessagesBranch.clear()
        _quoteMessagesBranch.addAll(list)
    }

    fun addMessageToQuoteScreen(message: MessageVo) {
        _quoteMessagesBranch.add(0, message)
    }

    fun openQuoteMessagesBranch(clickedMessageId: Long) {
        _quoteMessagesId.value = clickedMessageId
        _showQuoteMessagesBranch.value = true
    }

    fun closeQuoteMessagesBranch() {
        _showQuoteMessagesBranch.value = false
        _quoteMessagesId.value = DEFAULT_MESSAGE_ID
        _quoteMessagesBranch.clear()
    }

    fun updateChildIdForParentMessage(childMessageId: Long, parentMessageId: Long) {
        _messages.find { it.id == parentMessageId }?.apply {
            this.childMessageId = childMessageId
        }
    }
}
