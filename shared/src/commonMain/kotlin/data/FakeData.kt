package data

import composables.messages.MessageType

val initialMessages = listOf(
    MessageVo(
        MessageType.SYSTEM,
        "Добро пожаловать!\nДля быстрой отправки в Desktop версии используйте сочетание клавиш Ctr+Enter",
    ),
)

val exampleUiState = mapOf(
    "Chat GPT-4" to ConversationUiState(
        initialMessages = initialMessages,
        appBarTitle = "Chat GPT-4",
    )
)