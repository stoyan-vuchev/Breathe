package choehaualen.breath.core.ui.colors

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

val BreathDefaultColors = Colors(
    primarySoul = Color(0xFFFFD3D3),
    secondarySoul = Color(0xFFBBD6FF),
    background = Color(0xFFFFFFFF),
    backgroundGradientStart = Color(0xFFC7DEFF),
    backgroundGradientEnd = Color(0xFFFFDBDB),
    card = Color(0xFFEAEBF7),
    text = Color(0xFF121212)
)

val DreamyNightColors = Colors(
    primarySoul = Color(0xFFFFD3D3),
    secondarySoul = Color(0xFFBBD6FF),
    background = Color(0xFFFFFFFF),
    backgroundGradientStart = Color(0xFFC5D3F8),
    backgroundGradientEnd = Color(0xFF1A3C92),
    card = Color(0xFFEAEBF7),
    text = Color(0xFF121212)
)

val SleepColors = Colors(
    primarySoul = Color(0xFFFFD3D3),
    secondarySoul = Color(0xFFBBD6FF),
    background = Color(0xFFFFFFFF),
    backgroundGradientStart = Color(0xFFE8E6F7),
    backgroundGradientEnd = Color(0xFF8B88E6),
    card = Color(0xFFEAEBF7),
    text = Color(0xFF121212)
)

val MelonColors = Colors(
    primarySoul = Color(0xFFFFD3D3),
    secondarySoul = Color(0xFFBBD6FF),
    background = Color(0xFFFFFFFF),
    backgroundGradientStart = Color(0xFFCDEFD9),
    backgroundGradientEnd = Color(0xFF85D6C8),
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