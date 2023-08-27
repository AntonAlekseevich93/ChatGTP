package data

import kotlinx.serialization.Serializable

enum class AppScreenState {
    CHAT,
    AUTHORIZATION
}

@Serializable
data class BodyDto(
    val model: String,
    val messages: List<MessagesDto>
)

@Serializable
data class MessagesDto(
    val role: String,
    val content: String
)

@Serializable
data class ResponseDto(
    val id: String? = null,
    val created: Long? = null,
    val model: String? = null,
    val choices: List<ResponseGpt>? = null,
    val usage: UsageDto? = null,
    val error: ErrorDto? = null
)

@Serializable
data class ResponseGpt(
    val index: Int? = null,
    val message: MessageDto? = null,
    val finish_reason: String? = null
)

@Serializable
data class MessageDto(
    val role: String? = null,
    val content: String? = null,
)

@Serializable
data class UsageDto(
    val prompt_tokens: Int? = null,
    val completion_tokens: Int? = null,
    val total_tokens: Int? = null,
)

@Serializable
data class ErrorDto(
    val message: String? = null,
    val type: String? = null,
    val code: String? = null,
)

