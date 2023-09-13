package markdown.handler

import androidx.compose.runtime.staticCompositionLocalOf

val LocalBulletListHandler = staticCompositionLocalOf {
    return@staticCompositionLocalOf BulletHandler { "• " }
}

val LocalOrderedListHandler = staticCompositionLocalOf {
    return@staticCompositionLocalOf BulletHandler { "$it " }
}

fun interface BulletHandler {
    fun transform(bullet: CharSequence?): String
}