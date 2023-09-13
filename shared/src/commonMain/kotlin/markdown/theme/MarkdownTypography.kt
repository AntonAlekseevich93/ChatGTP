package markdown.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import themes.ApplicationTheme

val LocalMarkdownTypography = compositionLocalOf<MarkdownTypography> {
    error("No local MarkdownTypography")
}

interface MarkdownTypography {
    val text: TextStyle
    val code: TextStyle
    val h1: TextStyle
    val h2: TextStyle
    val h3: TextStyle
    val h4: TextStyle
    val h5: TextStyle
    val h6: TextStyle
    val quote: TextStyle
    val paragraph: TextStyle
    val ordered: TextStyle
    val bullet: TextStyle
    val list: TextStyle
}

@Composable
fun markdownTypography(
    h1: TextStyle = androidx.compose.material.MaterialTheme.typography.h1,
    h2: TextStyle = androidx.compose.material.MaterialTheme.typography.h2,
    h3: TextStyle = androidx.compose.material.MaterialTheme.typography.h3,
    h4: TextStyle = androidx.compose.material.MaterialTheme.typography.h4,
    h5: TextStyle = androidx.compose.material.MaterialTheme.typography.h5,
    h6: TextStyle = androidx.compose.material.MaterialTheme.typography.h6,
    text: TextStyle = androidx.compose.material.MaterialTheme.typography.body1,
    code: TextStyle = androidx.compose.material.MaterialTheme.typography.body2.copy(fontFamily = FontFamily.Monospace),
    quote: TextStyle = androidx.compose.material.MaterialTheme.typography.body2.plus(
        SpanStyle(
            fontStyle = FontStyle.Italic
        )
    ),
    paragraph: TextStyle = androidx.compose.material.MaterialTheme.typography.body1,
    ordered: TextStyle = androidx.compose.material.MaterialTheme.typography.body1,
    bullet: TextStyle = androidx.compose.material.MaterialTheme.typography.body1,
    list: TextStyle = androidx.compose.material.MaterialTheme.typography.body1
): MarkdownTypography = DefaultMarkdownTypography(
    h1 = h1, h2 = h2, h3 = h3, h4 = h4, h5 = h5, h6 = h6,
    text = text, quote = quote, code = code, paragraph = paragraph,
    ordered = ApplicationTheme.typography.bodyRegular, bullet = bullet, list = list
)

@Immutable
private class DefaultMarkdownTypography(
    override val h1: TextStyle,
    override val h2: TextStyle,
    override val h3: TextStyle,
    override val h4: TextStyle,
    override val h5: TextStyle,
    override val h6: TextStyle,
    override val text: TextStyle,
    override val code: TextStyle,
    override val quote: TextStyle,
    override val paragraph: TextStyle,
    override val ordered: TextStyle,
    override val bullet: TextStyle,
    override val list: TextStyle
) : MarkdownTypography