package themes

import androidx.compose.ui.graphics.Color

data class AppColors(
    val chatBackgroundColor: Color,
    val mainIconColor: Color,
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
    val quoteBackgroundSelectorLeftLineColor: Color,
    val quoteBackgroundSelectorColor: Color,
    val quoteButtonBackgroundColor: Color,
    val quoteButtonIconColor: Color,
    val quoteArrowImageGptColor: Color,
    val quoteArrowImageUserColor: Color,
    val quoteGptBackgroundColor: Color,
    val quoteUserBackgroundColor: Color,
    val quoteTextColor: Color,
    val quoteChatDividerColor: Color,
    val quoteAboveUserInputBackgroundColor: Color,
    val quoteAboveUserInputTitleColor: Color,
    val quoteAboveUserInputMessageColor: Color,
)

val lightPaletteTheme = AppColors(
    chatBackgroundColor = Color(0xFFfdfcf7),
    mainIconColor = Color.Black,
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
    logoAiTintColor = Color.Black,

    quoteBackgroundSelectorLeftLineColor = Color(0xFFde985f),
    quoteBackgroundSelectorColor = Color(0xFFf2d6bf),
    quoteButtonBackgroundColor = Color(0xFFd6e2e4),
    quoteButtonIconColor = Color(0xFF6b7174),
    quoteArrowImageGptColor = Color(0xFF000000),
    quoteArrowImageUserColor = Color(0xFF000000),
    quoteGptBackgroundColor = Color(0xFF000000),
    quoteUserBackgroundColor = Color(0xFF000000),
    quoteTextColor = Color(0xFF4c4c4c),
    quoteChatDividerColor = Color(0xFFb2b2b2),
    quoteAboveUserInputBackgroundColor = Color(0xFFe6e6e6),
    quoteAboveUserInputTitleColor = Color(0xFF000000),
    quoteAboveUserInputMessageColor = Color(0xFF4d4d4d),

    )

val darkPaletteTheme = AppColors(
    chatBackgroundColor = Color(0xFF1E1E1E),

    mainIconColor = Color(0xFF007AFF),

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
    logoAiTintColor = Color(0xFF007AFF),

    quoteBackgroundSelectorLeftLineColor = Color(0xFF007AFF),
    quoteBackgroundSelectorColor = Color(0xFF00244c),
    quoteButtonBackgroundColor = Color(0xFF1f1f1f),
    quoteButtonIconColor = Color(0xFF0055b2),
    quoteArrowImageGptColor = Color(0xFF007AFF),
    quoteArrowImageUserColor = Color(0xFFFF8400),
    quoteGptBackgroundColor = Color(0xFF007AFF),
    quoteUserBackgroundColor = Color(0xFFFF8400),
    quoteTextColor = Color(0xFF989ba2),
    quoteChatDividerColor = Color(0xFF007AFF),
    quoteAboveUserInputBackgroundColor = Color(0xFF3d3d3d),
    quoteAboveUserInputTitleColor = Color(0xFF007AFF),
    quoteAboveUserInputMessageColor = Color(0xFF989ba2),
)