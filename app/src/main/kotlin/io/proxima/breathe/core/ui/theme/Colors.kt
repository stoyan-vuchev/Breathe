package io.proxima.breathe.core.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.compositeOver

val BreathDefaultColors = Colors(
    primarySoul = Color(0xFFFFD3D3),
    secondarySoul = Color(0xFFBBD6FF),
    background = Color(0xFFFFFFFF),
    backgroundGradientStart = Color(0xFFFFDBDB),
    backgroundGradientEnd = Color(0xFFC7DEFF),
    card = Color(0xFFEAEBF7),
    text = Color(0xFF121212)
)

val DreamyNightColors = Colors(
    primarySoul = Color(0xFF8DADFF),
    secondarySoul = Color(0xFF002686),
    background = Color(0xFFFFFFFF),
    backgroundGradientStart = Color(0xFFE6EDFF),
    backgroundGradientEnd = Color(0xFFA3C1F5),
    card = Color(0xFFEAEBF7),
    text = Color(0xFF121212)
)

val SleepColors = Colors(
    primarySoul = Color(0xFFA9A1DD),
    secondarySoul = Color(0xFF5856B7),
    background = Color(0xFFFFFFFF),
    backgroundGradientStart = Color(0xFFE8E6F7),
    backgroundGradientEnd = Color(0xFF8B88E6),
    card = Color(0xFFEAEBF7),
    text = Color(0xFF121212)
)

val MelonColors = Colors(
    primarySoul = Color(0xFF96D3A9),
    secondarySoul = Color(0xFF438980),
    background = Color(0xFFFFFFFF),
    backgroundGradientStart = Color(0xFFCDEFD9),
    backgroundGradientEnd = Color(0xFF85D6C8),
    card = Color(0xFFEAEBF7),
    text = Color(0xFF121212)
)

val MangoColors = Colors(
    primarySoul = Color(0xFFF3CC0F),
    secondarySoul = Color(0xFFE38001),
    background = Color(0xFFFFFFFF),
    backgroundGradientStart = Color(0xFFFFF5C3),
    backgroundGradientEnd = Color(0xFFFFB862),
    card = Color(0xFFEAEBF7),
    text = Color(0xFF121212)
)

val SilverColors = Colors(
    primarySoul = Color(0xFFFFD3D3),
    secondarySoul = Color(0xFFBBD6FF),
    background = Color(0xFFFFFFFF),
    backgroundGradientStart = Color(0xFFFFFFFF),
    backgroundGradientEnd = Color(0xFF6B6B6B),
    card = Color(0xFFEAEBF7),
    text = Color(0xFF121212)
)

val SkyBlueColors = Colors(
    primarySoul = Color(0xFF6DCADB),
    secondarySoul = Color(0xFF1C7FC4),
    background = Color(0xFFFFFFFF),
    backgroundGradientStart = Color(0xFFD6F0F5),
    backgroundGradientEnd = Color(0xFF90C7EF),
    card = Color(0xFFE7EEF3),
    text = Color(0xFF121212)
)

val ZoneColors = Colors(
    primarySoul = Color(0xFFF49692),
    secondarySoul = Color(0xFFD3425D),
    background = Color(0xFFFFFFFF),
    backgroundGradientStart = Color(0xFFF9D2D2),
    backgroundGradientEnd = Color(0xFFECACB6),
    card = Color(0xFFEAEBF7),
    text = Color(0xFF121212)
)

val MlAssist = Colors(
    primarySoul = Color(0xFFB85DE2),
    secondarySoul = Color(0xFF4683DE),
    background = Color(0xFFFFFFFF),
    backgroundGradientStart = Color(0xFFD6F0F5),
    backgroundGradientEnd = Color(0xFFD6F0F5),
    card = Color(0xFFE7EEF3),
    text = Color(0xFF121212)
)

@Immutable
class Colors(
    val primarySoul: Color,
    val secondarySoul: Color,
    val background: Color,
    val backgroundGradientStart: Color,
    val backgroundGradientEnd: Color,
    val card: Color,
    val text: Color
) {

    override fun equals(other: Any?): Boolean {

        if (this === other) return true
        if (other == null || other !is Colors) return false

        if (primarySoul != other.primarySoul) return false
        if (secondarySoul != other.secondarySoul) return false

        if (background != other.background) return false
        if (backgroundGradientStart != other.backgroundGradientStart) return false
        if (backgroundGradientEnd != other.backgroundGradientEnd) return false

        if (card != other.card) return false
        if (text != other.text) return false

        return true

    }

    override fun hashCode(): Int {
        var result = primarySoul.hashCode()
        result = 31 * result + secondarySoul.hashCode()
        result = 31 * result + background.hashCode()
        result = 31 * result + backgroundGradientStart.hashCode()
        result = 31 * result + backgroundGradientEnd.hashCode()
        result = 31 * result + card.hashCode()
        result = 31 * result + text.hashCode()
        return result
    }

    override fun toString() = "Colors(" +
            "primarySoul=$primarySoul," +
            "secondarySoul=$secondarySoul," +
            "background=$background," +
            "backgroundGradientStart=$backgroundGradientStart," +
            "backgroundGradientEnd=$backgroundGradientEnd," +
            "card=$card," +
            "text=$text," +
            ")"

}

/** A CompositionLocal key used for providing [Colors] instance down the composition. */
val LocalColors = staticCompositionLocalOf { BreathDefaultColors }

/**
 * A function to swap a specific color with it's counterpart. e.g. ***background*** with ***text***.
 * @param color The color to be swapped.
 */
fun Colors.swapColor(color: Color) = when (color) {
    background -> text
    text -> background
    else -> Color.Unspecified
}

@Composable
fun ProvideBreathColors(
    colors: Colors = BreathDefaultColors,
    content: @Composable () -> Unit
) = CompositionLocalProvider(
    value = LocalColors provides colors,
    content = content
)

/**
 * A custom function to apply background gradient with an optional overlay
 * from the theme itself.
 */
fun Colors.backgroundBrush(
    startOverlay: Color = Color.Unspecified,
    endOverlay: Color = Color.Unspecified
) = Brush.verticalGradient(
    colors = listOf(
        startOverlay.compositeOver(backgroundGradientStart),
        endOverlay.compositeOver(backgroundGradientEnd)
    )
)