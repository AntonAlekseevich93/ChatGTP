package composables.messages.quote

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Reply
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import themes.ApplicationTheme

@Composable
fun QuoteButton(
    modifier: Modifier,
    message: String,
    position: Int,
    messageId: Long,
    quoteListener: (message: String, position: Int, parentMessageId: Long) -> Unit
) {
    val shape = RoundedCornerShape(8.dp)
    Box(
        modifier = modifier
            .padding(end = 34.dp),
        contentAlignment = Alignment.TopEnd
    ) {
        Card(
            modifier = Modifier.wrapContentWidth()
                .clip(shape)
                .clickable { quoteListener.invoke(message, position, messageId) },
            shape = shape,
            colors = CardDefaults.cardColors(containerColor = ApplicationTheme.colors.quoteButtonBackgroundColor),
        ) {
            Icon(
                modifier = Modifier.padding(horizontal = 16.dp),
                imageVector = Icons.Outlined.Reply,
                contentDescription = null,
                tint = ApplicationTheme.colors.quoteButtonIconColor
            )
        }
    }
}