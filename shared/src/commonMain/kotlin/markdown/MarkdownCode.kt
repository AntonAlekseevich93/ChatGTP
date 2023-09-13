package markdown

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import markdown.theme.LocalMarkdownColors
import markdown.theme.LocalMarkdownTypography
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import markdown.theme.CustomColor
import themes.ApplicationTheme

@Composable
internal fun MarkdownCode(
    code: String,
    customColor: CustomColor? = null,
    style: TextStyle = LocalMarkdownTypography.current.code
) {
    val clipboardManager = LocalClipboardManager.current
    val scope = rememberCoroutineScope()
    val showCopiedInfo = remember { mutableStateOf(false) }

    Surface(
        color = customColor?.backgroundCodeColor ?: LocalMarkdownColors.current.codeBackground,
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier.fillMaxWidth().padding(top = 4.dp, bottom = 4.dp)
    ) {
        Column {
            Row(
                modifier = Modifier.fillMaxWidth().padding(top = 4.dp),
                horizontalArrangement = Arrangement.End
            ) {
                AnimatedVisibility(showCopiedInfo.value) {
                    Text(
                        "Скопировано",
                        color = ApplicationTheme.colors.textCopyColor,
                        modifier = Modifier.padding(end = 8.dp),
                        fontSize = 12.sp
                    )
                }
                Icon(
                    imageVector = Icons.Default.ContentCopy,
                    null,
                    modifier = Modifier.padding(end = 8.dp, top = 2.dp).size(16.dp).clickable {
                        clipboardManager.setText(AnnotatedString(code))
                        scope.launch {
                            showCopiedInfo.value = true
                            delay(1000L)
                            showCopiedInfo.value = false
                        }
                    },
                    tint = ApplicationTheme.colors.iconCopyColor
                )

            }
            SelectionContainer() {
                Text(
                    code,
                    color = LocalMarkdownColors.current.codeText,
                    modifier = Modifier
                        .horizontalScroll(rememberScrollState())
                        .padding(8.dp),
                    style = style
                )
            }
        }
    }
}
