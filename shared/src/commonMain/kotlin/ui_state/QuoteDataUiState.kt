package ui_state

import MainViewModel.Companion.DEFAULT_MESSAGE_ID

data class QuoteDataUiState(
    val showingQuote: Boolean = false,
    val parentMessage: String = "",
    val parentMessageId: Long = DEFAULT_MESSAGE_ID,
    val parentMessagePosition: Int = -1,
)