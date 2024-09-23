package io.proxima.breathe.core.ui

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

fun Modifier.fadingEdges(
    minFade: Float = 0.5f,
    maxFade: Float = 0f,
    active: Boolean = true,
    startOffset: Dp = 32.dp,
    endOffset: Dp = 32.dp
) = composed {

    val startAlpha by animateFloatAsState(
        targetValue = if (active) maxFade else minFade,
        label = ""
    )

    this
        .graphicsLayer { alpha = 0.99f }
        .drawWithContent {
            drawContent()
            drawRect(
                brush = Brush.horizontalGradient(
                    colors = listOf(
                        Color.Black.copy(alpha = startAlpha),
                        Color.Black
                    ),
                    startX = 0f,
                    endX = startOffset.toPx()
                ),
                blendMode = BlendMode.DstIn
            )
            drawRect(
                topLeft = Offset(
                    x = size.width - 32.dp.toPx(),
                    y = 0f
                ),
                brush = Brush.horizontalGradient(
                    colors = listOf(Color.Black, Color.Transparent),
                    startX = size.width - endOffset.toPx(),
                    endX = size.width
                ),
                blendMode = BlendMode.DstIn
            )
        }

}