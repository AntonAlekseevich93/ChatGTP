package ui_state

import kotlinx.coroutines.flow.MutableStateFlow

data class CommonUiState(
    val quoteDataUiState: MutableStateFlow<QuoteDataUiState> = MutableStateFlow(QuoteDataUiState())
)