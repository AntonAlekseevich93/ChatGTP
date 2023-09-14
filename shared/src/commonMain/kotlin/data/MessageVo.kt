package data

import androidx.compose.runtime.Immutable
import com.aallam.openai.api.BetaOpenAI
import com.aallam.openai.api.chat.ChatMessage
import com.aallam.openai.api.chat.ChatRole
import com.chatgpt.database.Messages
import composables.messages.MessageType
import database.SqlDelightDataSource.Companion.GPT_TYPE
import database.SqlDelightDataSource.Companion.SYSTEM_TYPE
import database.SqlDelightDataSource.Companion.USER_TYPE
import kotlinx.serialization.Serializable

@Immutable
@Serializable
data class MessageVo(
    val id: Long,
    val messageType: MessageType,
    val content: String,
    val parentMessageId: Long? = null,
    val parentMessageText: String? = null,
    var childMessageId: Long? = null
)

fun MessageVo.toDto() = entity.MessageDto(
    message = content,
    messageType = when (messageType) {
        MessageType.USER -> USER_TYPE
        MessageType.GPT -> GPT_TYPE
        MessageType.SYSTEM -> SYSTEM_TYPE
    },
    messageId = id,
    parentMessageId = parentMessageId,
    parentMessageText = parentMessageText,
    childMessageId = childMessageId
)

fun Messages.toVo() = MessageVo(
    id = messageId,
    messageType = if (isUser == 0L) MessageType.USER else MessageType.GPT,
    content = message,
    parentMessageId = parentMessageId,
    parentMessageText = parentMessageText,
    childMessageId = childMessageId
)

@OptIn(BetaOpenAI::class)
fun MessageVo.toChatMessage(): ChatMessage? {
    val role =
        if (messageType == MessageType.USER) ChatRole.User else if (messageType == MessageType.GPT) ChatRole.Assistant else null
    return if (role != null) {
        ChatMessage(
            role = ChatRole.User,
            content = this.content
        )
    } else null
}

