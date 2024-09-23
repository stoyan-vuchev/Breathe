package io.proxima.breathe.core.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import io.proxima.breathe.R

val QuickSandFontFam = FontFamily(
    fonts = listOf(
        Font(R.font.quicksand_light, weight = FontWeight.Light),
        Font(R.font.quicksand_regular, weight = FontWeight.Normal),
        Font(R.font.quicksand_medium, weight = FontWeight.Medium),
        Font(R.font.quicksand_semibold, weight = FontWeight.SemiBold),
        Font(R.font.quicksand_bold, weight = FontWeight.Bold)
    )
)

val RobotoFontFam = FontFamily(
    fonts = listOf(
        Font(R.font.roboto_bold, weight = FontWeight.Bold)
    )
)

// Set of Material typography styles to start with
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = RobotoFontFam,
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = (-0.75).sp
    ),
    bodyMedium = TextStyle(
        fontFamily = RobotoFontFam,
        fontWeight = FontWeight.Bold,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = (-0.75).sp
    ),
    bodySmall = TextStyle(
        fontFamily = RobotoFontFam,
        fontWeight = FontWeight.Bold,
        fontSize = 12.sp,
        lineHeight = 16.sp,
        letterSpacing = (-0.75).sp
    ),
    titleLarge = TextStyle(
        fontFamily = QuickSandFontFam,
        fontWeight = FontWeight.Bold,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = (-0.75).sp
    ),
    labelSmall = TextStyle(
        fontFamily = RobotoFontFam,
        fontWeight = FontWeight.Bold,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = (-0.75).sp
    ),
    labelMedium = TextStyle(
        fontFamily = QuickSandFontFam,
        fontWeight = FontWeight.Bold,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = (-0.75).sp
    ),
    labelLarge = TextStyle(
        fontFamily = QuickSandFontFam,
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = (-0.75).sp
    ),
    headlineLarge = TextStyle(
        fontFamily = QuickSandFontFam,
        fontWeight = FontWeight.Bold,
        fontSize = 32.sp,
        lineHeight = 36.sp,
        letterSpacing = (-0.75).sp
    ),
    headlineSmall = TextStyle(
        fontFamily = QuickSandFontFam,
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp,
        lineHeight = 26.sp,
        letterSpacing = (-0.75).sp
    ),
    displayLarge = TextStyle(
        fontFamily = QuickSandFontFam,
        fontWeight = FontWeight.Bold,
        fontSize = 80.sp,
        lineHeight = 42.sp,
        letterSpacing = (-0.75).sp
    ),
    titleMedium = TextStyle(
        fontFamily = QuickSandFontFam,
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp,
        lineHeight = 28.sp,
        letterSpacing = (-0.75).sp
    ),
)

// Wait ....