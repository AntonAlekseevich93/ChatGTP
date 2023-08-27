package markdown.elements

import androidx.compose.runtime.Composable
import markdown.MarkdownCode
import org.intellij.markdown.ast.ASTNode

@Composable
internal fun MarkdownCodeFence(
    content: String,
    node: ASTNode,
) {
    // CODE_FENCE_START, FENCE_LANG, {content}, CODE_FENCE_END
    if (node.children.size >= 3) {
        val start = node.children[2].startOffset
        val end = node.children[node.children.size - 2].endOffset
        MarkdownCode(content.subSequence(start, end).toString().replaceIndent())
    } else {
        // invalid code block, skipping
    }
}