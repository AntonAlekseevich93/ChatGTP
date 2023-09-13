package composables.messages.quote

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import animation.LoadingAnimation
import composables.messages.MessageItem
import data.ConversationUiState
import data.MessageVo
import themes.ApplicationTheme

@Composable
fun QuoteThreadScreen(
    conversationUiState: ConversationUiState,
    scrollState: LazyListState,
    loaderIsShowing: Boolean,
) {
    val messages = conversationUiState.quoteMessagesBranch
    val chatModifier = Modifier
        .padding(top = 50.dp)
        .fillMaxSize()
        .background(ApplicationTheme.colors.chatBackgroundColor)

    LazyColumn(
        reverseLayout = true,
        contentPadding = PaddingValues(start = 10.dp, end = 10.dp, top = 20.dp, bottom = 90.dp),
        modifier = chatModifier,
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
            val message: MessageVo = messages[index]
            val firstItemPosition = messages.size - 1
            Column {
                Box {
                    MessageItem(
                        message = message,
                        position = index,
                        messageType = message.messageType,
                        modifier = Modifier
                            .padding(end = 16.dp, top = 12.dp),
                        customColor = null,
                        showQuoteBackgroundSelector = false,
                        showQuoteButton = false,
                        quoteListener = { _, _, _ ->
                        }
                    )
                }

                if (index == firstItemPosition) {
                    Divider(
                        modifier = Modifier.fillMaxWidth().padding(top = 24.dp, bottom = 8.dp)
                            .height(1.dp),
                        color = ApplicationTheme.colors.quoteChatDividerColor.copy(alpha = 0.5f)
                    )
                }
            }
        }
    }
}