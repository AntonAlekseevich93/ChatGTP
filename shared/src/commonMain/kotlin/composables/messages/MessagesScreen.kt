package composables.messages

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import animation.LoadingAnimation
import composables.messages.quote.GptQuoteBlock
import composables.messages.quote.UserQuoteBlock
import data.ConversationUiState
import data.MessageVo
import markdown.theme.CustomColor
import themes.ApplicationTheme
import ui_state.QuoteDataUiState

@Composable
fun MessagesScreen(
    conversationUiState: ConversationUiState,
    quoteDataUiState: QuoteDataUiState,
    scrollState: LazyListState,
    loaderIsShowing: Boolean,
    uploadDataListener: () -> Unit,
    quoteOpenBranchListener: (parent: Long) -> Unit,
    quoteListener: (message: String, position: Int, parentMessageId: Long) -> Unit
) {
    val messages = conversationUiState.messages
    val chatModifier = Modifier
        .fillMaxSize()
        .background(ApplicationTheme.colors.chatBackgroundColor)

    LazyColumn(
        reverseLayout = true,
        contentPadding = PaddingValues(
            start = 10.dp,
            end = 10.dp,
            top = 20.dp,
            bottom = 20.dp,
        ),
        modifier = if (quoteDataUiState.showingQuote) chatModifier.padding(bottom = 32.dp) else chatModifier,
        state = scrollState
    ) {
        if (loaderIsShowing) {
            item {
                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                    LoadingAnimation(
                        modifier = Modifier.padding(start = 0.dp, top = 24.dp),
                        circleColor = ApplicationTheme.colors.loadingCircleBackgroundColor
                    )
                }
            }
        }
        items(count = messages.size) { index ->
            if (messages.size - index in 1..2) uploadDataListener.invoke()
            Column {
                val message: MessageVo = messages[index]
                val customColor = CustomColor(
                    backgroundCodeColor = if (quoteDataUiState.showingQuote && index == quoteDataUiState.parentMessagePosition) Color.Transparent else null,
                    mainBackground = if (quoteDataUiState.showingQuote && index == quoteDataUiState.parentMessagePosition) Color.Transparent else getDefaultMessageColor(
                        message.messageType
                    )
                )

                if (message.parentMessageId != null && message.parentMessageText != null) {
                    if (message.messageType == MessageType.USER) {
                        GptQuoteBlock(
                            childMessageId = message.id,
                            quoteMessageText = message.parentMessageText,
                            quoteOpenBranchListener = quoteOpenBranchListener
                        )
                    } else {
                        UserQuoteBlock(
                            childMessageId = message.id,
                            quoteMessageText = message.parentMessageText,
                            quoteOpenBranchListener = quoteOpenBranchListener
                        )
                    }
                }
                MessageItem(
                    message = message,
                    position = index,
                    messageType = message.messageType,
                    customColor = customColor,
                    modifier = if (message.parentMessageId != null) Modifier
                        .padding(end = 16.dp)
                    else Modifier
                        .padding(end = 16.dp, top = 12.dp),
                    showQuoteBackgroundSelector = quoteDataUiState.showingQuote && quoteDataUiState.parentMessagePosition == index,
                    showQuoteButton = message.messageType == MessageType.GPT && !quoteDataUiState.showingQuote && index != quoteDataUiState.parentMessagePosition,
                    quoteListener = { quoteMessage, position, parentMessageId ->
                        quoteListener.invoke(
                            quoteMessage,
                            position,
                            parentMessageId
                        )
                    }
                )
            }
        }
    }
}

enum class MessageType {
    USER,
    GPT,
    SYSTEM
}