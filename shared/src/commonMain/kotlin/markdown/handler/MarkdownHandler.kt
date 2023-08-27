package markdown.handler

import androidx.compose.runtime.Composable
import markdown.elements.MarkdownCodeFence
import markdown.elements.MarkdownParagraph
import markdown.elements.MarkdownText
import markdown.theme.LocalMarkdownTypography
import org.intellij.markdown.MarkdownElementTypes
import org.intellij.markdown.MarkdownTokenTypes
import org.intellij.markdown.ast.ASTNode
import org.intellij.markdown.ast.getTextInNode

@Composable
internal fun ASTNode.handleElement(content: String): Boolean {
    val typography = LocalMarkdownTypography.current
    var handled = true
//    Spacer(Modifier.height(LocalMarkdownPadding.current.block))
    when (type) {
        MarkdownTokenTypes.TEXT -> MarkdownText(getTextInNode(content).toString())
        MarkdownTokenTypes.EOL -> {
//            MarkdownText(getTextInNode(content).toString())
        }
//        MarkdownElementTypes.CODE_BLOCK -> MarkdownCodeBlock(content, this)
        MarkdownElementTypes.CODE_FENCE -> MarkdownCodeFence(content, node = this)
//        MarkdownElementTypes.ATX_1 -> MarkdownHeader(content, this, typography.h1)
//        MarkdownTokenTypes.LPAREN ->
//        MarkdownElementTypes.ATX_2 -> MarkdownHeader(content, this, typography.h2)

//        MarkdownElementTypes.ATX_3 -> MarkdownHeader(content, this, typography.h3)

//        MarkdownElementTypes.ATX_4 -> MarkdownHeader(content, this, typography.h4)

//        MarkdownElementTypes.ATX_5 -> MarkdownHeader(content, this, typography.h5)

//        MarkdownElementTypes.ATX_6 -> MarkdownHeader(content, this, typography.h6)

//        MarkdownElementTypes.BLOCK_QUOTE -> MarkdownBlockQuote(content, this)
        MarkdownElementTypes.PARAGRAPH -> MarkdownParagraph(
            content,
            this,
            style = typography.paragraph,
        )
//        MarkdownElementTypes.ORDERED_LIST -> Column(modifier = Modifier) {
//            MarkdownOrderedList(content, this@handleElement, style = typography.ordered)
//        }
//
//        MarkdownElementTypes.UNORDERED_LIST -> Column(modifier = Modifier) {
//            MarkdownBulletList(content, this@handleElement, style = typography.bullet)
//        }
//
//        MarkdownElementTypes.IMAGE -> MarkdownImage(content, this)
//        MarkdownElementTypes.LINK_DEFINITION -> {
//            val linkLabel = findChildOfType(MarkdownElementTypes.LINK_LABEL)?.getTextInNode(content)?.toString()
//            if (linkLabel != null) {
//                val destination = findChildOfType(MarkdownElementTypes.LINK_DESTINATION)?.getTextInNode(content)?.toString()
//                LocalReferenceLinkHandler.current.store(linkLabel, destination)
//            }
//    }


//        else -> MarkdownText(getTextInNode(content).toString())
//        else -> {
////            println("type = == $type")
//            MarkdownText(getTextInNode(content).toString())
//        }
        else -> handled = false
    }
    return handled
}