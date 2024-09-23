package io.proxima.breathe.presentation.main.habit.main

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFontFamilyResolver
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.text.drawText
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.proxima.breathe.core.ui.theme.ProvideBreathColors
import io.proxima.breathe.core.ui.theme.ZoneColors
import io.proxima.breathe.core.ui.theme.BreathTheme
import kotlin.math.roundToInt

@Composable
fun HabitMainProgressComponent(
    modifier: Modifier = Modifier,
    goalDuration: Int,
    goalProgress: Int,
    emptyProgressColor: Color = BreathTheme.colors.card,
    currentProgressColor: Color = Color.Cyan,
    completedProgressColor: Color = Color.Green,
    textColor: Color = BreathTheme.colors.text
) {

    val defaultFontFamilyResolver = LocalFontFamilyResolver.current
    val density = LocalDensity.current
    val layoutDirection = LocalLayoutDirection.current
    val textStyle = BreathTheme.typography.titleSmall.copy(color = textColor)

    Canvas(
        modifier = modifier
    ) {

        // Base Values

        val horizontalPadding = 12.dp.toPx()
        val currentProgressCircleRadius = 24.dp.toPx()
        val baseProgressCircleRadius = 20.dp.toPx()

        // Goal Duration Circle & Text (The last ones).

        val isLast = goalProgress == goalDuration

        val goalDurationCircleColor = if (isLast) currentProgressColor
        else emptyProgressColor

        val goalDurationCircleRadius = if (isLast) {
            currentProgressCircleRadius
        } else baseProgressCircleRadius

        val goalDurationCircleCenterOffset = Offset(
            x = this.size.width - (goalDurationCircleRadius + horizontalPadding),
            y = this.center.y
        )

        drawCircle(
            color = goalDurationCircleColor,
            center = goalDurationCircleCenterOffset,
            radius = goalDurationCircleRadius
        )

        val goalDurationTextMeasurer = TextMeasurer(
            defaultFontFamilyResolver = defaultFontFamilyResolver,
            defaultDensity = density,
            defaultLayoutDirection = layoutDirection
        )

        val goalDurationTextSize = goalDurationTextMeasurer.measure(
            text = goalDuration.toString(),
            style = textStyle
        ).size

        val goalDurationTextOffset = goalDurationCircleCenterOffset.copy(
            x = goalDurationCircleCenterOffset.x - (goalDurationTextSize.width / 2),
            y = goalDurationCircleCenterOffset.y - (goalDurationTextSize.height / 2)
        )

        drawText(
            textMeasurer = goalDurationTextMeasurer,
            text = goalDuration.toString(),
            style = textStyle,
            topLeft = goalDurationTextOffset
        )

        // Halfway Circle & Text.

        val isHalfway = goalProgress == (goalDuration.toDouble() / 2).roundToInt()
        val isPastHalfway = goalProgress > (goalDuration.toDouble() / 2).roundToInt()

        val halfwayCircleRadius = if (isHalfway) {
            currentProgressCircleRadius
        } else baseProgressCircleRadius

        val halfwayCircleCenterOffset = Offset(
            x = this.center.x,
            y = this.center.y
        )

        val halfwayCircleColor = when {
            isHalfway -> currentProgressColor
            isPastHalfway -> completedProgressColor
            else -> emptyProgressColor
        }

        drawCircle(
            color = halfwayCircleColor,
            center = halfwayCircleCenterOffset,
            radius = halfwayCircleRadius
        )

        val halfwayTextMeasurer = TextMeasurer(
            defaultFontFamilyResolver = defaultFontFamilyResolver,
            defaultDensity = density,
            defaultLayoutDirection = layoutDirection
        )

        val halfwayText = (goalDuration.toDouble() / 2).roundToInt().toString()
        val halfwayTextSize = goalDurationTextMeasurer.measure(
            text = halfwayText,
            style = textStyle
        ).size

        val halfwayTextOffset = halfwayCircleCenterOffset.copy(
            x = halfwayCircleCenterOffset.x - (halfwayTextSize.width / 2),
            y = halfwayCircleCenterOffset.y - (halfwayTextSize.height / 2)
        )

        drawText(
            textMeasurer = halfwayTextMeasurer,
            text = halfwayText,
            style = textStyle,
            topLeft = halfwayTextOffset
        )

        // Fourth Circle & Text.

        val isFourth = goalProgress == (goalDuration.toDouble() * .8f).roundToInt() + 1
        val isPastFourth = goalProgress > (goalDuration.toDouble() * .8f).roundToInt() + 1

        val fourthCircleColor = when {
            isFourth -> currentProgressColor
            isPastFourth -> completedProgressColor
            else -> emptyProgressColor
        }

        val fourthCircleRadius = if (isFourth) {
            currentProgressCircleRadius
        } else baseProgressCircleRadius

        val fourthCircleCenterOffset = Offset(
            x = (this.size.width * .8f) - fourthCircleRadius - halfwayCircleRadius + (horizontalPadding / 2),
            y = this.center.y
        )

        drawCircle(
            color = fourthCircleColor,
            center = fourthCircleCenterOffset,
            radius = fourthCircleRadius
        )

        val fourthTextMeasurer = TextMeasurer(
            defaultFontFamilyResolver = defaultFontFamilyResolver,
            defaultDensity = density,
            defaultLayoutDirection = layoutDirection
        )

        val fourthText = ((goalDuration.toDouble() * .8f).roundToInt() + 1).toString()
        val fourthTextSize = goalDurationTextMeasurer.measure(
            text = fourthText,
            style = textStyle
        ).size

        val fourthTextOffset = fourthCircleCenterOffset.copy(
            x = fourthCircleCenterOffset.x - (fourthTextSize.width / 2),
            y = fourthCircleCenterOffset.y - (fourthTextSize.height / 2)
        )

        drawText(
            textMeasurer = fourthTextMeasurer,
            text = fourthText,
            style = textStyle,
            topLeft = fourthTextOffset
        )

        // First Circle & Text.

        val isFirst = goalProgress == 0
        val isPastFirst = goalProgress > 0

        val firstCircleColor = when {
            isFirst -> currentProgressColor
            isPastFirst -> completedProgressColor
            else -> emptyProgressColor
        }

        val firstCircleRadius = if (goalProgress == 0) {
            currentProgressCircleRadius
        } else baseProgressCircleRadius

        val firstCircleCenterOffset = Offset(
            x = firstCircleRadius + horizontalPadding,
            y = this.center.y
        )

        drawCircle(
            color = firstCircleColor,
            center = firstCircleCenterOffset,
            radius = firstCircleRadius
        )

        val firstTextMeasurer = TextMeasurer(
            defaultFontFamilyResolver = defaultFontFamilyResolver,
            defaultDensity = density,
            defaultLayoutDirection = layoutDirection
        )

        val firstTextSize = goalDurationTextMeasurer.measure(
            text = 0.toString(),
            style = textStyle
        ).size

        val firstTextOffset = firstCircleCenterOffset.copy(
            x = firstCircleCenterOffset.x - (firstTextSize.width / 2),
            y = firstCircleCenterOffset.y - (firstTextSize.height / 2)
        )

        drawText(
            textMeasurer = firstTextMeasurer,
            text = 0.toString(),
            style = textStyle,
            topLeft = firstTextOffset
        )

        // Second Circle & Text.

        val isSecond = goalProgress == (goalDuration.toDouble() / 3).roundToInt()
        val isPastSecond = goalProgress > (goalDuration.toDouble() / 3).roundToInt()

        val secondCircleColor = when {
            isSecond -> currentProgressColor
            isPastSecond -> completedProgressColor
            else -> emptyProgressColor
        }

        val secondCircleRadius = if (isSecond) {
            currentProgressCircleRadius
        } else baseProgressCircleRadius

        val secondCircleCenterOffset = Offset(
            x = (this.center.x / 2) + secondCircleRadius - (halfwayCircleRadius - secondCircleRadius),
            y = this.center.y
        )

        drawCircle(
            color = secondCircleColor,
            center = secondCircleCenterOffset,
            radius = secondCircleRadius
        )

        val secondTextMeasurer = TextMeasurer(
            defaultFontFamilyResolver = defaultFontFamilyResolver,
            defaultDensity = density,
            defaultLayoutDirection = layoutDirection
        )

        val secondText = (goalDuration.toDouble() / 3).roundToInt().toString()
        val secondTextSize = goalDurationTextMeasurer.measure(
            text = secondText,
            style = textStyle
        ).size

        val secondTextOffset = secondCircleCenterOffset.copy(
            x = secondCircleCenterOffset.x - (secondTextSize.width / 2),
            y = secondCircleCenterOffset.y - (secondTextSize.height / 2)
        )

        drawText(
            textMeasurer = secondTextMeasurer,
            text = secondText,
            style = textStyle,
            topLeft = secondTextOffset
        )

    }

}

@Preview
@Composable
fun HabitMainProgressComponentPreview() = BreathTheme {
    ProvideBreathColors(ZoneColors) {

        HabitMainProgressComponent(
            modifier = Modifier
                .width(360.dp)
                .height(48.dp),
            goalDuration = 180,
            goalProgress = 180
        )

    }
}