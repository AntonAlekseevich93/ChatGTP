package themes

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

data class AppTypography(
    val title1Regular: TextStyle,
    val title1Medium: TextStyle,
    val title1Bold: TextStyle,
    val title2Regular: TextStyle,
    val title2Medium: TextStyle,
    val title2Bold: TextStyle,
    val title3Regular: TextStyle,
    val title3Medium: TextStyle,
    val title3Bold: TextStyle,
    val headlineRegular: TextStyle,
    val headlineMedium: TextStyle,
    val headlineBold: TextStyle,
    val bodyRegular: TextStyle,
    val bodyMedium: TextStyle,
    val bodyBold: TextStyle,
    val buttonRegular: TextStyle,
    val buttonMain: TextStyle,
    val buttonBold: TextStyle,
    val buttonSmall: TextStyle,
    val footnoteRegular: TextStyle,
    val footnoteMedium: TextStyle,
    val footnoteBold: TextStyle,
    val captionRegular: TextStyle,
    val captionMedium: TextStyle,
    val captionBold: TextStyle,
)

val ottTypography = AppTypography(
    title1Regular = TextStyle(
        fontSize = 24.sp,
        fontWeight = FontWeight.Normal,
        letterSpacing = 0.15.sp,
        lineHeight = 32.sp,
    ),
    title1Medium = TextStyle(
        fontSize = 24.sp,
        fontWeight = FontWeight.Medium,
        letterSpacing = 0.15.sp,
        lineHeight = 32.sp,
    ),
    title1Bold = TextStyle(
        fontSize = 24.sp,
        fontWeight = FontWeight.Bold,
        letterSpacing = 0.15.sp,
        lineHeight = 32.sp,
    ),
    title2Regular = TextStyle(
        fontSize = 20.sp,
        fontWeight = FontWeight.Normal,
        letterSpacing = 0.15.sp,
        lineHeight = 28.sp,
    ),
    title2Medium = TextStyle(
        fontSize = 20.sp,
        fontWeight = FontWeight.Medium,
        letterSpacing = 0.15.sp,
        lineHeight = 28.sp,
    ),
    title2Bold = TextStyle(
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold,
        letterSpacing = 0.15.sp,
        lineHeight = 28.sp,
    ),
    title3Regular = TextStyle(
        fontSize = 18.sp,
        fontWeight = FontWeight.Normal,
        letterSpacing = 0.15.sp,
        lineHeight = 24.sp,
    ),
    title3Medium = TextStyle(
        fontSize = 18.sp,
        fontWeight = FontWeight.Medium,
        letterSpacing = 0.15.sp,
        lineHeight = 24.sp,
    ),
    title3Bold = TextStyle(
        fontSize = 18.sp,
        fontWeight = FontWeight.Bold,
        letterSpacing = 0.15.sp,
        lineHeight = 24.sp,
    ),
    headlineRegular = TextStyle(
        fontSize = 16.sp,
        fontWeight = FontWeight.Normal,
        letterSpacing = 0.45.sp,
        lineHeight = 24.sp,
    ),
    headlineMedium = TextStyle(
        fontSize = 16.sp,
        fontWeight = FontWeight.Medium,
        letterSpacing = 0.45.sp,
        lineHeight = 24.sp,
    ),
    headlineBold = TextStyle(
        fontSize = 16.sp,
        fontWeight = FontWeight.Bold,
        letterSpacing = 0.45.sp,
        lineHeight = 24.sp,
    ),
    bodyRegular = TextStyle(
        fontSize = 14.sp,
        fontWeight = FontWeight.Normal,
        letterSpacing = 0.25.sp,
        lineHeight = 20.sp,
    ),
    bodyMedium = TextStyle(
        fontSize = 14.sp,
        fontWeight = FontWeight.Medium,
        letterSpacing = 0.25.sp,
        lineHeight = 20.sp,
    ),
    bodyBold = TextStyle(
        fontSize = 14.sp,
        fontWeight = FontWeight.Bold,
        letterSpacing = 0.25.sp,
        lineHeight = 20.sp,
    ),
    buttonRegular = TextStyle(
        fontSize = 14.sp,
        fontWeight = FontWeight.Normal,
        letterSpacing = 1.25.sp,
        lineHeight = 20.sp,
    ),
    buttonMain = TextStyle(
        fontSize = 14.sp,
        fontWeight = FontWeight.Medium,
        letterSpacing = 1.25.sp,
        lineHeight = 20.sp,
    ),
    buttonBold = TextStyle(
        fontSize = 14.sp,
        fontWeight = FontWeight.Bold,
        letterSpacing = 1.25.sp,
        lineHeight = 20.sp,
    ),
    buttonSmall = TextStyle(
        fontSize = 12.sp,
        fontWeight = FontWeight.Medium,
        letterSpacing = 0.4.sp,
        lineHeight = 16.sp,
    ),
    footnoteRegular = TextStyle(
        fontSize = 12.sp,
        fontWeight = FontWeight.Normal,
        letterSpacing = 0.4.sp,
        lineHeight = 16.sp,
    ),
    footnoteMedium = TextStyle(
        fontSize = 12.sp,
        fontWeight = FontWeight.Medium,
        letterSpacing = 0.4.sp,
        lineHeight = 16.sp,
    ),
    footnoteBold = TextStyle(
        fontSize = 12.sp,
        fontWeight = FontWeight.Bold,
        letterSpacing = 0.4.sp,
        lineHeight = 16.sp,
    ),
    captionRegular = TextStyle(
        fontSize = 10.sp,
        fontWeight = FontWeight.Normal,
        letterSpacing = 0.8.sp,
        lineHeight = 12.sp,
    ),
    captionMedium = TextStyle(
        fontSize = 10.sp,
        fontWeight = FontWeight.Medium,
        letterSpacing = 0.8.sp,
        lineHeight = 12.sp,
    ),
    captionBold = TextStyle(
        fontSize = 10.sp,
        fontWeight = FontWeight.Bold,
        letterSpacing = 0.8.sp,
        lineHeight = 12.sp,
    )
)