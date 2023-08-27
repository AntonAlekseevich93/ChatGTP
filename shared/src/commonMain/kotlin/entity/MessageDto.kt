package entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MessageDto(
    @SerialName("message")
    val message: String,
    @SerialName("isUser")
    val messageType: Long,
)
