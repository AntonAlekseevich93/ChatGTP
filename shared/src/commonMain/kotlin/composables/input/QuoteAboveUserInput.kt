package composables.input

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import themes.ApplicationTheme

@Composable
fun QuoteAboveUserInput(parentMessage: String, closeQuoteListener: () -> Unit) {
    val interactionSource = remember { MutableInteractionSource() }
    Column(Modifier.fillMaxWidth().padding(horizontal = 16.dp).background(Color.Transparent)) {
        Card(
            modifier = Modifier.fillMaxWidth().height(36.dp),
            shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
            colors = CardDefaults.cardColors(
                containerColor = ApplicationTheme.colors.quoteAboveUserInputBackgroundColor.copy(
                    alpha = 0.5F
                )
            ),
        ) {
            Row(
                modifier = Modifier.fillMaxSize().padding(horizontal = 24.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "Ответ на сообщение: ",
                    color = ApplicationTheme.colors.quoteAboveUserInputTitleColor,
                    style = ApplicationTheme.typography.footnoteRegular
                )
                Text(
                    text = parentMessage,
                    color = ApplicationTheme.colors.quoteAboveUserInputMessageColor,
                    style = ApplicationTheme.typography.footnoteRegular,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.weight(8f)
                )
                Box(
                    modifier = Modifier.weight(1f),
                    contentAlignment = Alignment.CenterEnd
                ) {
                    Icon(
                        modifier = Modifier
                            .clickable(
                                interactionSource = interactionSource,
                                indication = null
                            ) { closeQuoteListener.invoke() },
                        imageVector = Icons.Outlined.Close,
                        contentDescription = null,
                        tint = ApplicationTheme.colors.mainIconColor
                    )
                }
            }
        }
    }
}
