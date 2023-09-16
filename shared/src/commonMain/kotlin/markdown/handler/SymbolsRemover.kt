package markdown.handler

class SymbolsRemover {
    companion object {
        private val regexBold = "\\*\\*([^*]+)\\*\\*".toRegex() // **ANY**
        private val regexItalics = "\\*([^*]+)\\*".toRegex() // *ANY*
        private val regexBoldSymbol = "\\*\\*".toRegex()
        private val regexItalicsSymbol = "\\*".toRegex()
        private const val EMPTY = ""

        fun removeSymbolsIfExist(content: String): String {
            return if (content.contains(regexBold)) {
                content.replace(regexBoldSymbol, EMPTY)
            } else if (content.contains(regexItalics)) {
                content.replace(regexItalicsSymbol, EMPTY)
            } else content
        }
    }
}