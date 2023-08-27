package markdown.elements

import androidx.compose.runtime.Composable
import markdown.MarkdownCode
import org.intellij.markdown.ast.ASTNode

@Composable
internal fun MarkdownCodeBlock(
    content: String,
    node: ASTNode
) {
    val start = node.children[0].startOffset
    val end = node.children[node.children.size - 1].endOffset
    MarkdownCode(content.subSequence(start, end).toString().replaceIndent())
}