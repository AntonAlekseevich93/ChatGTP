package markdown

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import markdown.handler.handleElement
import markdown.linkhandler.LocalReferenceLinkHandler
import markdown.linkhandler.ReferenceLinkHandlerImpl
import markdown.theme.LocalMarkdownColors
import markdown.theme.LocalMarkdownPadding
import markdown.theme.LocalMarkdownTypography
import markdown.theme.MarkdownColors
import markdown.theme.MarkdownPadding
import markdown.theme.MarkdownTypography
import markdown.theme.markdownColor
import markdown.theme.markdownPadding
import markdown.theme.markdownTypography
import org.intellij.markdown.flavours.MarkdownFlavourDescriptor
import org.intellij.markdown.flavours.gfm.GFMFlavourDescriptor
import org.intellij.markdown.parser.MarkdownParser

@Composable
fun Markdown(
    content: String,
    colors: MarkdownColors = markdownColor(),
    typography: MarkdownTypography = markdownTypography(),
    padding: MarkdownPadding = markdownPadding(),
    modifier: Modifier = Modifier.padding(16.dp),
    flavour: MarkdownFlavourDescriptor = GFMFlavourDescriptor()
) {
    CompositionLocalProvider(
        LocalReferenceLinkHandler provides ReferenceLinkHandlerImpl(),
        LocalMarkdownPadding provides padding,
        LocalMarkdownColors provides colors,
        LocalMarkdownTypography provides typography,
    ) {
        Column(modifier) {
            val parsedTree = MarkdownParser(flavour).buildMarkdownTreeFromString(content)
            parsedTree.children.forEach { node ->
                if (!node.handleElement(content)) {
                    node.children.forEach { child ->
                        child.handleElement(content)
                    }
                }
            }
        }
    }
}

