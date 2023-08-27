package composables.messages

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import resourceBindings.drawable_gpt_ai_icon
import themes.ApplicationTheme

@OptIn(ExperimentalResourceApi::class)
@Composable
fun GptCopyBlock(messageText: String) {
    val clipboardManager = LocalClipboardManager.current
    val scope = rememberCoroutineScope()
    val showCopiedInfo = remember { mutableStateOf(false) }
    Row(verticalAlignment = Alignment.CenterVertically) {
        Image(
            modifier = Modifier.size(36.dp).padding(end = 12.dp),
            painter = painterResource(drawable_gpt_ai_icon),
            contentDescription = null,
            colorFilter = ColorFilter.tint(ApplicationTheme.colors.logoAiTintColor)
        )
        Text("ChatGPT", color = ApplicationTheme.colors.mainTextColor)
        Icon(imageVector = Icons.Default.ContentCopy,
            null,
            modifier = Modifier.padding(start = 8.dp).size(16.dp).clickable {
                clipboardManager.setText(AnnotatedString(messageText))
                scope.launch {
                    showCopiedInfo.value = true
                    delay(1000L)
                    showCopiedInfo.value = false
                }
            },
            tint = ApplicationTheme.colors.iconCopyColor
        )

        AnimatedVisibility(showCopiedInfo.value) {
            Text(
                "Скопировано",
                color = ApplicationTheme.colors.textCopyColor,
                modifier = Modifier.padding(start = 8.dp),
                fontSize = 12.sp
            )
        }
    }
}