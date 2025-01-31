package io.proxima.breathe.core.ui.theme

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.stoyanvuchev.systemuibarstweaker.LocalSystemUIBarsTweaker
import com.stoyanvuchev.systemuibarstweaker.ProvideSystemUIBarsTweaker
import com.stoyanvuchev.systemuibarstweaker.ScrimStyle
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.HazeStyle
import dev.chrisbanes.haze.haze
import dev.chrisbanes.haze.hazeChild

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
                scrimStyle = ScrimStyle.None
            )
        )
        onDispose {}
    }

    ProvideBreathColors(colors) {

        val hazeState = remember { HazeState() }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .haze(state = hazeState)
        ) {

            MaterialTheme(
                typography = Typography,
                content = content
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .hazeChild(
                        state = hazeState,
                        style = HazeStyle(
                            blurRadius = 20.dp
                        )
                    )
            ) {

                Spacer(modifier = Modifier.navigationBarsPadding())

            }

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