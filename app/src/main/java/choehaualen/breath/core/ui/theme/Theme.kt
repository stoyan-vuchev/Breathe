package choehaualen.breath.core.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import choehaualen.breath.core.ui.colors.BreathDefaultColors
import choehaualen.breath.core.ui.colors.Colors
import choehaualen.breath.core.ui.colors.LocalColors
import com.stoyanvuchev.systemuibarstweaker.ProvideSystemUIBarsTweaker
import com.stoyanvuchev.systemuibarstweaker.SystemBarStyle
import com.stoyanvuchev.systemuibarstweaker.SystemUIBarsConfiguration
import com.stoyanvuchev.systemuibarstweaker.rememberSystemUIBarsTweaker

@Composable
fun BreathTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {

    val tweaker = rememberSystemUIBarsTweaker(
        initialConfiguration = SystemUIBarsConfiguration.default(
            statusBarStyle = SystemBarStyle.defaultStatusBarStyle(
                darkIcons = false
            )
        )
    )

    ProvideSystemUIBarsTweaker(systemUIBarsTweaker = tweaker) {

        // I have to explain what happens here.
        // We've basically sent a [Colors] class down the composition.
        // think of it as idk like ... an electrical outlet providing electricity to a device/s(bro typos are fine just
        // ok :D
        CompositionLocalProvider(
            LocalColors provides BreathDefaultColors // it is now xD
        ) {

            // the colors can be consumed anywhere inside the {} including in composables even furhter down

            MaterialTheme(
                typography = Typography, // No color scheme for Material :)
                content = content
            )

            // I think we've already made a milestone with the app XD
            // yes ALL thanks to you!

            // Not really. You've taken enough courage to explore such thing.
            // Not sure if what I'm writting makes sense due to language barrier..
            // im not good at eng either but i do can understand
            // Awesome:)
        }

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