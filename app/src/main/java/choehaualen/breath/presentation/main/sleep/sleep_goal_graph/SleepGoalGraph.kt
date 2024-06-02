package choehaualen.breath.presentation.main.sleep.sleep_goal_graph

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFontFamilyResolver
import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.text.drawText
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import choehaualen.breath.core.ui.colors.ProvideBreathColors
import choehaualen.breath.core.ui.colors.SleepColors
import choehaualen.breath.core.ui.theme.BreathTheme
import sv.lib.squircleshape.drawSquircle
import kotlin.time.Duration
import kotlin.time.Duration.Companion.hours
import kotlin.time.Duration.Companion.milliseconds

@Composable
fun SleepGoalGraph(
    modifier: Modifier = Modifier,
    canvasHeight: Dp = 320.dp,
    currentSleepDuration: Long = 6.hours.inWholeMilliseconds,
    sleepGoalDuration: Long = 8.hours.inWholeMilliseconds,
    sleepLineGradientStart: Color = BreathTheme.colors.primarySoul,
    sleepLineGradientEnd: Color = BreathTheme.colors.secondarySoul,
    textColor: Color = BreathTheme.colors.text
) {

    val smallTextStyle = BreathTheme.typography.bodyMedium.copy(color = textColor)
    val largeTextStyle = BreathTheme.typography.titleLarge.copy(color = textColor)

    val fontFamilyResolver = LocalFontFamilyResolver.current
    val density = LocalDensity.current

    Canvas(
        modifier = Modifier
            .height(canvasHeight)
            .then(modifier),
        onDraw = {

            val graphSize = Size(
                width = this.size.width,
                height = this.size.height - 64.dp.toPx()
            )

            val bottomLineHeight = 2.dp.toPx()

            drawLine(
                color = textColor,
                start = Offset(
                    x = 0f,
                    y = graphSize.height
                ),
                end = Offset(
                    x = graphSize.width,
                    y = graphSize.height
                )
            )

            // Sleep Lines

            val sleepLineWidth = 40.dp.toPx() // XD

            val currentSleepLineCenterOffsetX = graphSize.width / 4
            val currentSleepLineHeight = calculateHeight(
                value = currentSleepDuration,
                maxValue = sleepGoalDuration,
                maxHeightPx = graphSize.height - bottomLineHeight
            )

            val currentSleepLineOffset = Offset(
                x = currentSleepLineCenterOffsetX - sleepLineWidth / 2,
                y = graphSize.height - currentSleepLineHeight
            )

            drawSquircle(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        sleepLineGradientStart,
                        sleepLineGradientEnd
                    ),
                    startY = currentSleepLineOffset.y,
                    endY = graphSize.height - (graphSize.height - currentSleepLineHeight)
                ),
                topLeft = currentSleepLineOffset,
                size = Size(
                    width = sleepLineWidth,
                    height = currentSleepLineHeight
                ),
                topLeftCorner = sleepLineWidth / 2,
                topRightCorner = sleepLineWidth / 2,
                bottomRightCorner = 0f,
                bottomLeftCorner = 0f
            )

            // Sleep Goal line

            val sleepGoalLineCenterOffsetX = graphSize.width - (graphSize.width / 4)
            val sleepGoalLineHeight = calculateHeight(
                value = sleepGoalDuration,
                maxValue = sleepGoalDuration,
                maxHeightPx = graphSize.height - bottomLineHeight
            )

            val sleepGoalLineOffset = Offset(
                x = sleepGoalLineCenterOffsetX - sleepLineWidth / 2,
                y = graphSize.height - sleepGoalLineHeight
            )

            drawSquircle(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        sleepLineGradientStart,
                        sleepLineGradientEnd
                    ),
                    startY = sleepGoalLineOffset.y,
                    endY = graphSize.height - (graphSize.height - sleepGoalLineHeight)
                ),
                topLeft = sleepGoalLineOffset,
                size = Size(
                    width = sleepLineWidth,
                    height = sleepGoalLineHeight
                ),
                topLeftCorner = sleepLineWidth / 2,
                topRightCorner = sleepLineWidth / 2,
                bottomRightCorner = 0f,
                bottomLeftCorner = 0f
            )

            // Now let's draw the text under the graph,yes

            val textMeasurer = TextMeasurer(
                defaultFontFamilyResolver = fontFamilyResolver,
                defaultDensity = density,
                defaultLayoutDirection = this.layoutDirection
            )

            val currentSleepText = "Current sleep rate"
            val currentSleepTextMeasurements = textMeasurer.measure(
                text = currentSleepText,
                style = smallTextStyle
            )

            drawText(
                textMeasurer = textMeasurer,
                text = currentSleepText,
                topLeft = Offset(
                    x = currentSleepLineCenterOffsetX -
                            currentSleepTextMeasurements.size.width.toFloat() * .5f,
                    y = this.size.height - currentSleepTextMeasurements.size.height * 1.5f
                ),
                style = smallTextStyle.copy(color = textColor.copy(.86f))
            )

            val sleepGoalText = "Your goal"
            val sleepGoalTextMeasurements = textMeasurer.measure(
                text = sleepGoalText,
                style = smallTextStyle
            )

            drawText(
                textMeasurer = textMeasurer,
                text = sleepGoalText,
                topLeft = Offset(
                    x = sleepGoalLineCenterOffsetX -
                            sleepGoalTextMeasurements.size.width.toFloat() * .5f,
                    y = this.size.height - sleepGoalTextMeasurements.size.height * 1.5f
                ),
                style = smallTextStyle.copy(color = textColor.copy(.86f))
            )

            // Yes, it was needed.

            val currentSleepDurationText = convertMillisToString(currentSleepDuration)
            val currentSleepDurationTextMeasurements = textMeasurer.measure(
                text = currentSleepDurationText,
                style = largeTextStyle
            )

            drawText(
                textMeasurer = textMeasurer,
                text = currentSleepDurationText,
                style = largeTextStyle,
                topLeft = Offset(
                    x = currentSleepLineCenterOffsetX -
                            currentSleepDurationTextMeasurements.size.width.toFloat() * .5f,
                    y = this.size.height - currentSleepTextMeasurements.size.height -
                            currentSleepDurationTextMeasurements.size.height * 1.4f
                )
            )

            val sleepGoalDurationText = convertMillisToString(sleepGoalDuration)
            val sleepGoalDurationTextMeasurements = textMeasurer.measure(
                text = sleepGoalDurationText,
                style = largeTextStyle
            )

            drawText(
                textMeasurer = textMeasurer,
                text = sleepGoalDurationText,
                style = largeTextStyle,
                topLeft = Offset(
                    x = sleepGoalLineCenterOffsetX -
                            sleepGoalDurationTextMeasurements.size.width.toFloat() * .5f,
                    y = this.size.height - sleepGoalTextMeasurements.size.height -
                            sleepGoalDurationTextMeasurements.size.height * 1.4f
                )
            )

        }
    )

}

private fun calculateHeight(
    value: Long,
    maxValue: Long,
    maxHeightPx: Float
): Float {
    return ((value.toFloat() / maxValue.toFloat()) * maxHeightPx).coerceIn(
        minimumValue = 0f,
        maximumValue = maxHeightPx
    )
}

private fun convertMillisToString(
    millis: Long
): String {
    val duration = Duration.parse(millis.milliseconds.toString())
    return duration.toString()
}

@Preview(showBackground = true)
@Composable
private fun SleepGoalGraphPreview() = BreathTheme {
    ProvideBreathColors(SleepColors) {
        SleepGoalGraph(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            currentSleepDuration = 6.2.hours.inWholeMilliseconds,
            sleepGoalDuration = 8.8.hours.inWholeMilliseconds
        )
    }
}