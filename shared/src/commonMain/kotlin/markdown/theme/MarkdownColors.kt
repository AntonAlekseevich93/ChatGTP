package markdown.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.graphics.Color
import themes.ApplicationTheme

val LocalMarkdownColors = compositionLocalOf<MarkdownColors> {
    error("No local MarkdownColors")
}

@Stable
interface MarkdownColors {
    val text: Color
    val codeText: Color
    val codeBackground: Color
}


@Composable
fun markdownColor(
    text: Color = ApplicationTheme.colors.mainTextColor,
    codeText: Color = ApplicationTheme.colors.textCodeBlockColor,
    codeBackground: Color = androidx.compose.material.MaterialTheme.colors.onBackground.copy(alpha = 0.1f)
): MarkdownColors = DefaultMarkdownColors(
    text = text,
    codeText = codeText,
    codeBackground = codeBackground
)

@Immutable
private class DefaultMarkdownColors(
    override val text: Color,
    override val codeText: Color,
    override val codeBackground: Color,
) : MarkdownColors

