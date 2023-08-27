package composables.messages

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import markdown.Markdown
import data.MessageVo
import themes.ApplicationTheme

@Composable
fun MessageItem(
    message: MessageVo,
    messageType: MessageType,
) {
    val backgroundBubbleColor = when (messageType) {
        MessageType.USER -> ApplicationTheme.colors.messagesBackgroundUserColor
        MessageType.GPT -> ApplicationTheme.colors.messagesBackgroundGPTColor
        MessageType.SYSTEM -> ApplicationTheme.colors.messagesBackgroundSystemColor
    }

    Row {
        Column(
            modifier = Modifier
                .padding(end = 16.dp, top = 12.dp)
                .weight(1f)
        ) {
            if (messageType == MessageType.GPT)
                GptCopyBlock(messageText = message.content)
            Surface(
                modifier = if (messageType == MessageType.GPT) Modifier.padding(start = 24.dp) else Modifier,
                color = backgroundBubbleColor,
                shape = if (messageType == MessageType.SYSTEM) chatBubbleShapeSystemResponse else chatBubbleShapeGptResponse
            ) {
                Markdown(message.content)
            }
        }
    }
}

private val chatBubbleShapeGptResponse = RoundedCornerShape(
    topStart = 0.dp,
    topEnd = 20.dp, bottomEnd = 20.dp, bottomStart = 20.dp
)

private val chatBubbleShapeSystemResponse = RoundedCornerShape(
    topStart = 20.dp,
    topEnd = 20.dp, bottomEnd = 20.dp, bottomStart = 20.dp
)