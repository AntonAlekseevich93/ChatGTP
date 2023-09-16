package markdown.elements

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.unit.sp
import markdown.buildMarkdownAnnotatedString
import markdown.theme.LocalMarkdownTypography
import org.intellij.markdown.ast.ASTNode

@Composable
internal fun MarkdownParagraph(
    content: String,
    node: ASTNode,
    style: TextStyle = LocalMarkdownTypography.current.paragraph,
) {
    val styledText = buildAnnotatedString {
        pushStyle(LocalMarkdownTypography.current.text.toSpanStyle().copy(fontSize = 14.sp))
        buildMarkdownAnnotatedString(content, node)
        pop()
    }
    MarkdownText(styledText, style = style)
}