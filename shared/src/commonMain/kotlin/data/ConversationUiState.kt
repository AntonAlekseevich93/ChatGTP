package data

import androidx.compose.runtime.toMutableStateList

class ConversationUiState(
    val appBarTitle: String,
    initialMessages: List<MessageVo>,
) {
    private val _messages: MutableList<MessageVo> = initialMessages.toMutableStateList()
    val messages: List<MessageVo> = _messages

    fun addMessage(message: MessageVo) {
        _messages.add(0, message) // Add to the beginning of the list
    }
}
