package themes

import androidx.compose.ui.graphics.Color

data class AppColors(
    val chatBackgroundColor: Color,
    val messagesBackgroundUserColor: Color,
    val messagesBackgroundGPTColor: Color,
    val messagesBackgroundSystemColor: Color,
    val keyInfoCardBackgroundColor: Color,
    val cardBackgroundColor: Color,
    val mainTextColor: Color,
    val loadingCircleBackgroundColor: Color,
    val textFieldFocusedBorderColor: Color,
    val textFieldFocusedLabelColor: Color,
    val textCodeBlockColor: Color,
    val mainButtonBackgroundColor: Color,
    val mainButtonTextColor: Color,
    val appBarBackgroundColor: Color,
    val appbarIconsColor: Color,
    val iconCopyColor: Color,
    val textCopyColor: Color,
    val userInputBackgroundColor: Color,
    val userInputInnerBackgroundColor: Color,
    val userInputSendDisableButtonColor: Color,
    val userInputSendEnableButtonColor: Color,
    val userInputTextColor: Color,
    val switchCheckedColor: Color,
    val logoAiTintColor: Color,
)

val lightPaletteTheme = AppColors(
    chatBackgroundColor = Color(0xFFfdfcf7),
    messagesBackgroundUserColor = Color(0xFFDEEBFE),
    messagesBackgroundGPTColor = Color(0xFFd6e2e4),
    messagesBackgroundSystemColor = Color(0xFFeab676),
    keyInfoCardBackgroundColor = Color(0xFFeeeee4),
    cardBackgroundColor = Color(0xFFFFFFFF),

    mainTextColor = Color.Black,

    textCodeBlockColor = Color.Black,

    loadingCircleBackgroundColor = Color(0xFFe78444),

    textFieldFocusedBorderColor = Color(0xFF07518b),
    textFieldFocusedLabelColor = Color(0xFF07518b),

    mainButtonBackgroundColor = Color(0xFF07518b),
    mainButtonTextColor = Color(0xFFFFFFFF),

    appBarBackgroundColor = Color(0xFFFFFDFA),
    appbarIconsColor = Color(0xFF45464F),

    iconCopyColor = Color.Black,
    textCopyColor = Color.Black,

    userInputBackgroundColor = Color(0xFFf3f1e7),
    userInputInnerBackgroundColor = Color(0xFFfafaf0),
    userInputSendDisableButtonColor = Color.Gray,
    userInputSendEnableButtonColor = Color(0xFF0067ff),
    userInputTextColor = Color.Black,

    switchCheckedColor = Color(0xFF154c79),
    logoAiTintColor = Color.Black
)

val darkPaletteTheme = AppColors(
    chatBackgroundColor = Color(0xFF1E1E1E),

    messagesBackgroundUserColor = Color(0xFF232323),
    messagesBackgroundGPTColor = Color(0xFF232323),
    messagesBackgroundSystemColor = Color(0xFF464646),

    keyInfoCardBackgroundColor = Color(0xFF282828),

    cardBackgroundColor = Color(0xFF1E1E1E),

    mainTextColor = Color(0xFFbdbdbd),
    textCodeBlockColor = Color(0xFF938e93),

    loadingCircleBackgroundColor = Color(0xFF007AFF),

    textFieldFocusedBorderColor = Color(0xFF007AFF),
    textFieldFocusedLabelColor = Color(0xFF007AFF),

    mainButtonBackgroundColor = Color(0xFF007AFF),
    mainButtonTextColor = Color(0xFF000000),

    appBarBackgroundColor = Color(0xFF282828),
    appbarIconsColor = Color(0xFF007AFF),

    iconCopyColor = Color(0xFF007AFF),
    textCopyColor = Color(0xFF989ba2),

    userInputBackgroundColor = Color(0xFF282828),
    userInputInnerBackgroundColor = Color(0xFF323232),
    userInputSendDisableButtonColor = Color.Gray,
    userInputSendEnableButtonColor = Color(0xFF007AFF),
    userInputTextColor = Color(0xFFbdbdbd),

    switchCheckedColor = Color(0xFF007AFF),
    logoAiTintColor = Color(0xFF007AFF)
)