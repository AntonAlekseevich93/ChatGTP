package markdown.handler

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import markdown.elements.MarkdownBulletList
import markdown.elements.MarkdownCodeFence
import markdown.elements.MarkdownOrderedList
import markdown.elements.MarkdownParagraph
import markdown.elements.MarkdownText
import markdown.theme.CustomColor
import markdown.theme.LocalMarkdownTypography
import org.intellij.markdown.MarkdownElementTypes
import org.intellij.markdown.MarkdownTokenTypes
import org.intellij.markdown.ast.ASTNode
import org.intellij.markdown.ast.getTextInNode

@Composable
internal fun ASTNode.handleElement(
    content: String,
    customColor: CustomColor? = null,
): Boolean {
    val typography = LocalMarkdownTypography.current
    var handled = true
    when (type) {
        MarkdownTokenTypes.TEXT -> {
            MarkdownText(getTextInNode(content).toString())
        }

        MarkdownTokenTypes.EOL -> {}
////            MarkdownText(getTextInNode(content).toString())
//        }
//        MarkdownElementTypes.CODE_BLOCK -> MarkdownCodeBlock(content, this)
//        MarkdownElementTypes.CODE_BLOCK -> { println("CODE BLOCK ${getTextInNode(content)}")}
        MarkdownElementTypes.CODE_FENCE -> {
            MarkdownCodeFence(
                content = content,
                node = this,
                customColor = customColor
            )
        }
//        MarkdownElementTypes.ATX_1 -> MarkdownHeader(content, this, typography.h1)
//        MarkdownTokenTypes.LPAREN -> { println("LPAREN ${getTextInNode(content)}")}
//        MarkdownElementTypes.ATX_2 -> MarkdownHeader(content, this, typography.h2)

//        MarkdownElementTypes.ATX_3 -> MarkdownHeader(content, this, typography.h3)

//        MarkdownElementTypes.ATX_4 -> MarkdownHeader(content, this, typography.h4)

//        MarkdownElementTypes.ATX_5 -> MarkdownHeader(content, this, typography.h5)

//        MarkdownElementTypes.ATX_6 -> MarkdownHeader(content, this, typography.h6)

//        MarkdownElementTypes.BLOCK_QUOTE -> MarkdownBlockQuote(content, this)
//        MarkdownElementTypes.BLOCK_QUOTE -> {
//            println("QUOTE ${getTextInNode(content)}")
//        }
        MarkdownElementTypes.PARAGRAPH -> {
            MarkdownParagraph(
                content = content,
                node = this,
                style = typography.paragraph,
            )
        }

        MarkdownElementTypes.ORDERED_LIST -> Column(modifier = Modifier) {
            MarkdownOrderedList(content, this@handleElement, style = typography.ordered)
        }

//        MarkdownElementTypes.ORDERED_LIST -> {println("ORDERED LIST ${getTextInNode(content)}")}
//        MarkdownElementTypes.UNORDERED_LIST -> {println("UNORDERED_LIST ${getTextInNode(content)}")}
//
        MarkdownElementTypes.UNORDERED_LIST -> Column(modifier = Modifier) {
            MarkdownBulletList(content, this@handleElement, style = typography.bullet)
        }
//
//        MarkdownElementTypes.IMAGE -> MarkdownImage(content, this)
//        MarkdownElementTypes.LINK_DEFINITION -> {
//            val linkLabel = findChildOfType(MarkdownElementTypes.LINK_LABEL)?.getTextInNode(content)?.toString()
//            if (linkLabel != null) {
//                val destination = findChildOfType(MarkdownElementTypes.LINK_DESTINATION)?.getTextInNode(content)?.toString()
//                LocalReferenceLinkHandler.current.store(linkLabel, destination)
//            }
//    }
//        MarkdownElementTypes.HTML_BLOCK -> {
//            println("ПОПАЛИ")
//          val text =  getTextInNode(content).forEach {
//              Text(text = it.toString(), color = Color.Red)
//          }
////
//        }


//        else -> {
//            MarkdownText(getTextInNode(content).toString())
//        }
//
        else -> {
            MarkdownText(getTextInNode(content).toString())
        }

//        else -> handled = false
    }
    return handled
}