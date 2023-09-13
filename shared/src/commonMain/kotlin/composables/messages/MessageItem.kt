package composables.messages

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import composables.messages.quote.QuoteBackgroundSelector
import composables.messages.quote.QuoteButton
import data.MessageVo
import markdown.Markdown
import markdown.theme.CustomColor
import themes.ApplicationTheme

@Composable
fun MessageItem(
    message: MessageVo,
    position: Int,
    messageType: MessageType,
    customColor: CustomColor?,
    modifier: Modifier = Modifier,
    showQuoteBackgroundSelector: Boolean,
    showQuoteButton: Boolean,
    quoteListener: (message: String, position: Int, parentMessageId: Long) -> Unit,
) {
    Row {
        Column(
            modifier = modifier
                .weight(1f)
        ) {
            if (messageType == MessageType.GPT) {
                GptCopyBlock(message = message)
            }
            Box(
                Modifier.wrapContentWidth()
            ) {
                if (showQuoteBackgroundSelector) {
                    QuoteBackgroundSelector(modifier = Modifier.matchParentSize())
                }

                Surface(
                    modifier = if (messageType == MessageType.GPT) Modifier.padding(
                        start = 24.dp,
                        top = if (messageType == MessageType.GPT && message.parentMessageId == null && message.childMessageId == null)
                            12.dp
                        else
                            0.dp
                    ) else Modifier,
                    color = customColor?.mainBackground ?: getDefaultMessageColor(messageType),
                    shape = if (messageType == MessageType.SYSTEM) chatBubbleShapeSystemResponse else chatBubbleShapeGptResponse
                ) {
                    Markdown(
                        message.content,
                        customColor = customColor
                    )
                }
                if (showQuoteButton && message.parentMessageId == null && message.childMessageId == null) {
                    QuoteButton(
                        modifier = Modifier.matchParentSize(),
                        message = message.content,
                        position = position,
                        messageId = message.id,
                        quoteListener = quoteListener
                    )
                }
            }
        }
    }
}

@Composable
fun getDefaultMessageColor(messageType: MessageType) = when (messageType) {
    MessageType.USER -> ApplicationTheme.colors.messagesBackgroundUserColor
    MessageType.GPT -> ApplicationTheme.colors.messagesBackgroundGPTColor
    MessageType.SYSTEM -> ApplicationTheme.colors.messagesBackgroundSystemColor
}

private val chatBubbleShapeGptResponse = RoundedCornerShape(
    topStart = 0.dp,
    topEnd = 20.dp, bottomEnd = 20.dp, bottomStart = 20.dp
)

private val chatBubbleShapeSystemResponse = RoundedCornerShape(
    topStart = 20.dp,
    topEnd = 20.dp, bottomEnd = 20.dp, bottomStart = 20.dp
)