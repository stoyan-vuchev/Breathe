package io.proxima.breathe.presentation.main.breathe.segment

import androidx.compose.animation.core.Ease
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.compositeOver
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFontFamilyResolver
import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.text.drawText
import androidx.compose.ui.unit.dp
import io.proxima.breathe.core.etc.transformFraction
import io.proxima.breathe.core.ui.theme.BreathTheme
import sv.lib.squircleshape.CornerSmoothing
import sv.lib.squircleshape.drawSquircle

@Composable
fun BreathMeter(
    modifier: Modifier = Modifier,
    segmentState: BreatheScreenSegmentState,
    emptyLineColor: Color = BreathTheme.colors.background,
    expandedLineColor: Color = Color.Black.copy(.16f)
        .compositeOver(BreathTheme.colors.secondarySoul),
    shrunkLineColor: Color = Color.Black.copy(.32f)
        .compositeOver(BreathTheme.colors.secondarySoul),
) {

    val textStyle = BreathTheme.typography.labelMedium.copy(color = BreathTheme.colors.text)
    val fontFamilyResolver = LocalFontFamilyResolver.current
    val density = LocalDensity.current

    val animDuration by rememberUpdatedState(segmentState.modeDurationInSeconds * 1000)
    val inhaleFraction by animateFloatAsState(
        targetValue = segmentState.phase.fraction,
        label = "",
        animationSpec = tween(
            durationMillis = animDuration,
            easing = Ease
        )
    )

    val lineColor by rememberUpdatedState(
        lerp(
            start = shrunkLineColor,
            stop = expandedLineColor,
            fraction = inhaleFraction
        )
    )

    Canvas(
        modifier = Modifier
            .width(256.dp)
            .height(86.dp)
            .then(modifier)
    ) {

        val baseLineHeight = 32.dp.toPx()
        val lineHeight = transformFraction(
            value = inhaleFraction,
            startX = 0f,
            endX = 1f,
            startY = 16.dp.toPx(),
            endY = baseLineHeight
        )

        val baseCornerSize = baseLineHeight / 2
        val cornerSize = baseLineHeight / 2
        val cornerSmoothing = transformFraction(
            value = inhaleFraction,
            startX = 0f,
            endX = 1f,
            startY = CornerSmoothing.None,
            endY = CornerSmoothing.Small
        )

        drawSquircle(
            color = emptyLineColor,
            topLeft = Offset.Zero,
            size = this.size.copy(height = baseLineHeight),
            topLeftCorner = baseCornerSize,
            topRightCorner = baseCornerSize,
            bottomRightCorner = baseCornerSize,
            bottomLeftCorner = baseCornerSize,
            cornerSmoothing = CornerSmoothing.Small
        )

        val lineSize = Size(
            width = transformFraction(
                value = inhaleFraction,
                startX = 0f,
                endX = 1f,
                startY = lineHeight,
                endY = this.size.width
            ),
            height = lineHeight
        )

        val lineOffset = Offset(
            x = transformFraction(
                value = 1f - inhaleFraction,
                startX = 0f,
                endX = 1f,
                startY = 0f,
                endY = this.center.x - lineHeight.div(2)
            ),
            y = (baseLineHeight - lineHeight) / 2
        )

        drawSquircle(
            color = lineColor,
            topLeft = lineOffset,
            size = lineSize,
            topLeftCorner = cornerSize,
            topRightCorner = cornerSize,
            bottomRightCorner = cornerSize,
            bottomLeftCorner = cornerSize,
            cornerSmoothing = cornerSmoothing
        )

        val textMeasurer = TextMeasurer(
            defaultFontFamilyResolver = fontFamilyResolver,
            defaultDensity = density,
            defaultLayoutDirection = this.layoutDirection
        )

        val text = "Breath Meter"
        val textMeasurements = textMeasurer.measure(
            text = text,
            style = textStyle
        )

        drawText(
            textMeasurer = textMeasurer,
            text = text,
            topLeft = Offset(
                x = center.x - textMeasurements.size.width.toFloat() / 2,
                y = center.y.plus(baseLineHeight / 2) - textMeasurements.size.height.toFloat() / 2
            ),
            style = textStyle
        )

    }

}