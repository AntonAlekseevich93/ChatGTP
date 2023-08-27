package data

import androidx.compose.runtime.Immutable
import composables.messages.MessageType
import database.SqlDelightDataSource.Companion.GPT_TYPE
import database.SqlDelightDataSource.Companion.SYSTEM_TYPE
import database.SqlDelightDataSource.Companion.USER_TYPE
import kotlinx.serialization.Serializable

@Immutable
@Serializable
data class MessageVo(
    val messageType: MessageType,
    val content: String,
)

fun MessageVo.toDto() = entity.MessageDto(
    message = content,
    messageType = when (messageType) {
        MessageType.USER -> USER_TYPE
        MessageType.GPT -> GPT_TYPE
        MessageType.SYSTEM -> SYSTEM_TYPE
    }
)

