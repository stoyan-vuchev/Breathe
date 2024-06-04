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

@Composable
fun BreathTheme(content: @Composable () -> Unit) = ProvideSystemUIBarsTweaker {

    val tweaker = LocalSystemUIBarsTweaker.current

    DisposableEffect(tweaker) {
        tweaker.tweakSystemBarsStyle(
            statusBarStyle = tweaker.statusBarStyle.copy(
                darkIcons = true,
                enforceContrast = false
            ),
            navigationBarStyle = tweaker.navigationBarStyle.copy(
                darkIcons = true,
                enforceContrast = false
            )
        )
        onDispose {}
    }

    ProvideBreathColors(BreathDefaultColors) {

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