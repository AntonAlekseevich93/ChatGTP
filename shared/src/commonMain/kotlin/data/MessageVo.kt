package data

import androidx.compose.runtime.Immutable
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

