package markdown.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

val LocalMarkdownPadding = staticCompositionLocalOf<MarkdownPadding> {
    error("No local Padding")
}

interface MarkdownPadding {
    val block: Dp
    val list: Dp
    val indentList: Dp
}

@Composable
fun markdownPadding(
    block: Dp = 0.dp,
    list: Dp = 0.dp,
    indentList: Dp = 0.dp
): MarkdownPadding = DefaultMarkdownPadding(
    block = block,
    list = list,
    indentList = indentList
)

@Immutable
private class DefaultMarkdownPadding(
    override val block: Dp,
    override val list: Dp,
    override val indentList: Dp
) : MarkdownPadding