package composables.messages.quote

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import resourceBindings.drawable_gpt_ai_icon
import resourceBindings.drawable_line_short_icon
import themes.ApplicationTheme

@OptIn(ExperimentalResourceApi::class)
@Composable
fun GptQuoteBlock(
    childMessageId: Long,
    quoteMessageText: String,
    quoteOpenBranchListener: (parent: Long) -> Unit
) {
    val shape = remember { RoundedCornerShape(8.dp) }
    Row(
        modifier = Modifier.padding(start = 2.dp, top = 24.dp, end = 42.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            modifier = Modifier.size(45.dp).padding(start = 6.dp),
            painter = painterResource(drawable_line_short_icon),
            contentDescription = null,
            colorFilter = ColorFilter.tint(ApplicationTheme.colors.quoteArrowImageGptColor)
        )
        Card(
            modifier = Modifier.wrapContentWidth()
                .padding(bottom = 16.dp, start = 8.dp)
                .clip(shape)
                .clickable {
                    quoteOpenBranchListener.invoke(childMessageId)
                },
            shape = shape,
            colors = CardDefaults.cardColors(
                containerColor = ApplicationTheme.colors.quoteGptBackgroundColor.copy(
                    alpha = 0.09f
                )
            )
        ) {
            Row(
                Modifier.padding(start = 16.dp, end = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    modifier = Modifier.size(24.dp).padding(end = 12.dp),
                    painter = painterResource(drawable_gpt_ai_icon),
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(ApplicationTheme.colors.logoAiTintColor)
                )
                Text(
                    "ChatGPT:",
                    color = ApplicationTheme.colors.mainTextColor,
                    modifier = Modifier.padding(end = 6.dp),
                    style = ApplicationTheme.typography.footnoteRegular,
                )
                Text(
                    text = quoteMessageText,
                    color = ApplicationTheme.colors.quoteTextColor,
                    style = ApplicationTheme.typography.footnoteRegular,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
            }
        }
    }
}

@OptIn(ExperimentalResourceApi::class)
@Composable
fun UserQuoteBlock(
    childMessageId: Long,
    quoteMessageText: String,
    quoteOpenBranchListener: (parent: Long) -> Unit
) {
    val shape = remember { RoundedCornerShape(8.dp) }
    Row(
        modifier = Modifier.padding(start = 80.dp, top = 24.dp, end = 20.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Image(
            modifier = Modifier.size(35.dp),
            painter = painterResource(drawable_line_short_icon),
            contentDescription = null,
            colorFilter = ColorFilter.tint(ApplicationTheme.colors.quoteArrowImageUserColor)
        )
        Card(
            modifier = Modifier.wrapContentWidth()
                .padding(start = 8.dp, bottom = 16.dp)
                .clip(shape)
                .clickable {
                    quoteOpenBranchListener.invoke(childMessageId)
                },
            shape = shape,
            colors = CardDefaults.cardColors(
                containerColor = ApplicationTheme.colors.quoteUserBackgroundColor.copy(
                    alpha = 0.09f
                )
            )
        ) {
            Row(
                Modifier.padding(start = 16.dp, end = 16.dp, top = 3.dp, bottom = 3.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(
                    "User:",
                    color = ApplicationTheme.colors.mainTextColor,
                    modifier = Modifier.padding(end = 6.dp),
                    style = ApplicationTheme.typography.footnoteRegular,
                )
                Text(
                    text = quoteMessageText,
                    color = ApplicationTheme.colors.quoteTextColor,
                    style = ApplicationTheme.typography.footnoteRegular,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
            }
        }
    }
}