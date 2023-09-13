package markdown.elements

import androidx.compose.runtime.Composable
import markdown.MarkdownCode
import markdown.theme.CustomColor
import org.intellij.markdown.ast.ASTNode

@Composable
internal fun MarkdownCodeFence(
    content: String,
    node: ASTNode,
    customColor: CustomColor? = null
) {
    // CODE_FENCE_START, FENCE_LANG, {content}, CODE_FENCE_END
    if (node.children.size >= 3) {
        val start = node.children[2].startOffset
        val end = node.children[node.children.size - 2].endOffset
        MarkdownCode(
            code = content.subSequence(start, end).toString().replaceIndent(),
            customColor = customColor
        )
    } else {
        // invalid code block, skipping
    }
}