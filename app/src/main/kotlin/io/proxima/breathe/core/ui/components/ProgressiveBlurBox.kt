package io.proxima.breathe.core.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.HazeStyle
import dev.chrisbanes.haze.haze
import dev.chrisbanes.haze.hazeChild



/**
 * A composable that overlays a progressive blur on its content.
 *
 * @param progress A float from 0f (no blur) to 1f (maximum blur).
 * @param modifier Modifier to be applied.
 * @param maxBlur Maximum blur radius applied at progress = 1f.
 * @param tintAlpha Maximum tint alpha when progress = 1f.
 */
@Composable
fun ProgressiveBlurBox(
    progress: Float,
    modifier: Modifier = Modifier,
    maxBlur: Dp = 20.dp,
    tintAlpha: Float = 0.5f
) {
    val hazeState = remember { HazeState() }
    // Activate the haze mechanism for children.
    Box(modifier = modifier.haze(hazeState)) {
        Box(
            modifier = Modifier
                .matchParentSize() // Fill the parent's size.
                .background(Color.Black.copy(alpha = 0.2f))
                .hazeChild(
                    state = hazeState,
                    style = HazeStyle(
                        blurRadius = (maxBlur.value * progress).dp,
                        tint = Color.Black.copy(alpha = tintAlpha * progress)
                    )
                )
                .drawWithCache {
                    // Create a vertical gradient that is opaque at the top and fades to transparent by half the height.
                    val gradientBrush = Brush.verticalGradient(
                        colors = listOf(Color.White, Color.Transparent),
                        startY = 0f,
                        endY = size.height / 2f
                    )
                    onDrawWithContent {
                        drawContent()
                        drawRect(
                            brush = gradientBrush,
                            size = size,
                            blendMode = BlendMode.DstIn
                        )
                    }
                }
        )
    }
}
