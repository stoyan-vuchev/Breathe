package choehaualen.breath.core.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.ReadOnlyComposable
import choehaualen.breath.core.ui.colors.BreathDefaultColors
import choehaualen.breath.core.ui.colors.Colors
import choehaualen.breath.core.ui.colors.LocalColors
import choehaualen.breath.core.ui.colors.ProvideBreathColors
import com.stoyanvuchev.systemuibarstweaker.LocalSystemUIBarsTweaker
import com.stoyanvuchev.systemuibarstweaker.ProvideSystemUIBarsTweaker
import com.stoyanvuchev.systemuibarstweaker.ScrimStyle

@Composable
fun BreathTheme(
    colors: Colors = BreathDefaultColors,
    content: @Composable () -> Unit
) = ProvideSystemUIBarsTweaker {

    val tweaker = LocalSystemUIBarsTweaker.current

    DisposableEffect(tweaker) {
        tweaker.tweakSystemBarsStyle(
            statusBarStyle = tweaker.statusBarStyle.copy(
                darkIcons = true,
                scrimStyle = ScrimStyle.None
            ),
            navigationBarStyle = tweaker.navigationBarStyle.copy(
                darkIcons = true,
                scrimStyle = if (tweaker.isGestureNavigationEnabled) ScrimStyle.None
                else ScrimStyle.Custom(
                    lightThemeColor = colors.backgroundGradientEnd,
                    darkThemeColor = colors.backgroundGradientEnd,
                    lightThemeColorOpacity = .67f,
                    darkThemeColorOpacity = .67f
                )
            )
        )
        onDispose {}
    }

    ProvideBreathColors(colors) {

        MaterialTheme(
            typography = Typography,
            content = content
        )

    }

}

/**
 * The Application theme used everywhere when it comes to the UI.
 */
object BreathTheme {

    /** The provided theme colors used across the UI. */
    val colors: Colors
        @ReadOnlyComposable
        @Composable
        get() = LocalColors.current

    val typography: Typography
        @ReadOnlyComposable
        @Composable
        get() = Typography

}