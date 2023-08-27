package composables.messages

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import animation.LoadingAnimation
import data.ConversationUiState
import data.MessageVo
import themes.ApplicationTheme

@Composable
fun Messages(
    conversationUiState: ConversationUiState,
    scrollState: LazyListState,
    waitingForResponseState: Boolean,
) {
    val messages = conversationUiState.messages
    LazyColumn(
        reverseLayout = true,
        contentPadding = PaddingValues(start = 10.dp, end = 10.dp, top = 20.dp, bottom = 90.dp),
        modifier = Modifier
            .padding(top = 50.dp)
            .fillMaxSize()
            .background(ApplicationTheme.colors.chatBackgroundColor),
        state = scrollState
    ) {
        if (waitingForResponseState) {
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
            MessageItem(
                message = message,
                messageType = message.messageType,
            )
        }
    }
}

enum class MessageType {
    USER,
    GPT,
    SYSTEM
}