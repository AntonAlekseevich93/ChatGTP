package markdown.utils

import org.intellij.markdown.MarkdownElementTypes
import org.intellij.markdown.MarkdownTokenTypes
import org.intellij.markdown.ast.ASTNode

internal fun List<ASTNode>.filterNonListTypes(): List<ASTNode> = this.filter { n ->
    n.type != MarkdownElementTypes.ORDERED_LIST && n.type != MarkdownElementTypes.UNORDERED_LIST && n.type != MarkdownTokenTypes.EOL
}