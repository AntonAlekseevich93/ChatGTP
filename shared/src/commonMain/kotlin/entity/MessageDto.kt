package entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MessageDto(
    @SerialName("message")
    val message: String,
    @SerialName("isUser")
    val messageType: Long,
    @SerialName("messageId")
    val messageId: Long,
    @SerialName("parentMessageId")
    val parentMessageId: Long?,
    @SerialName("parentMessageText")
    val parentMessageText: String?,
    @SerialName("childMessageId")
    val childMessageId: Long?,
)
