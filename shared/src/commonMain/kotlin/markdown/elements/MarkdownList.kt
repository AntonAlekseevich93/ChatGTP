package markdown.elements

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.unit.dp
import markdown.buildMarkdownAnnotatedString
import markdown.handler.LocalBulletListHandler
import markdown.handler.LocalOrderedListHandler
import markdown.theme.LocalMarkdownColors
import markdown.theme.LocalMarkdownPadding
import markdown.theme.LocalMarkdownTypography
import markdown.utils.filterNonListTypes
import org.intellij.markdown.MarkdownElementTypes
import org.intellij.markdown.MarkdownElementTypes.ORDERED_LIST
import org.intellij.markdown.MarkdownElementTypes.UNORDERED_LIST
import org.intellij.markdown.MarkdownTokenTypes
import org.intellij.markdown.ast.ASTNode
import org.intellij.markdown.ast.findChildOfType
import org.intellij.markdown.ast.getTextInNode
import themes.ApplicationTheme


@Composable
private fun MarkdownListItems(
    content: String,
    node: ASTNode,
    style: TextStyle = LocalMarkdownTypography.current.list,
    level: Int = 0,
    item: @Composable (child: ASTNode) -> Unit
) {
    val listDp = LocalMarkdownPadding.current.list
    val indentListDp = LocalMarkdownPadding.current.indentList
    Column(
        modifier = Modifier.padding(
            start = (indentListDp) * level,
            top = listDp,
            bottom = listDp
        )
    ) {
        node.children.forEach { child ->
            when (child.type) {
                MarkdownElementTypes.LIST_ITEM -> {
                    item(child)
                    when (child.children.last().type) {
                        ORDERED_LIST -> MarkdownOrderedList(content, child, style, level + 1)
                        UNORDERED_LIST -> MarkdownBulletList(content, child, style, level + 1)
                    }
                }

                ORDERED_LIST -> MarkdownOrderedList(content, child, style, level + 1)
                UNORDERED_LIST -> MarkdownBulletList(content, child, style, level + 1)
            }
        }
    }
}


@Composable
internal fun MarkdownOrderedList(
    content: String,
    node: ASTNode,
    style: TextStyle = LocalMarkdownTypography.current.ordered,
    level: Int = 0
) {
    val orderedListHandler = LocalOrderedListHandler.current
    MarkdownListItems(content, node, style, level) { child ->
        Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = orderedListHandler.transform(
                    child.findChildOfType(MarkdownTokenTypes.LIST_NUMBER)?.getTextInNode(content)
                ),
                style = style.copy(color = ApplicationTheme.colors.iconCopyColor) // todo
            )
            val text = buildAnnotatedString {
                pushStyle(style.toSpanStyle())
                buildMarkdownAnnotatedString(content, child.children.filterNonListTypes())
                pop()
            }
            MarkdownText(text, Modifier.padding(), style = style)
        }
    }
}

@Composable
internal fun MarkdownBulletList(
    content: String,
    node: ASTNode,
    style: TextStyle = LocalMarkdownTypography.current.bullet,
    level: Int = 0
) {
    val bulletHandler = LocalBulletListHandler.current
    MarkdownListItems(content, node, style, level) { child ->
        Row(Modifier.fillMaxWidth()) {
            Text(
                bulletHandler.transform(
                    child.findChildOfType(MarkdownTokenTypes.LIST_BULLET)?.getTextInNode(content)
                ),
                style = style,
                color = LocalMarkdownColors.current.text
            )
            val text = buildAnnotatedString {
                pushStyle(style.toSpanStyle())
                buildMarkdownAnnotatedString(content, child.children.filterNonListTypes())
                pop()
            }
            MarkdownText(text, Modifier.padding(bottom = 4.dp), style = style)
        }
    }
}
