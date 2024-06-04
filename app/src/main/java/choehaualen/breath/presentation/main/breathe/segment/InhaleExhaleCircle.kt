package choehaualen.breath.presentation.main.breathe.segment

import androidx.compose.animation.core.Ease
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFontFamilyResolver
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import choehaualen.breath.core.etc.transformFraction
import choehaualen.breath.core.ui.theme.BreathTheme

@Composable
fun InhaleExhaleCircle(
    modifier: Modifier = Modifier,
    segmentState: BreatheScreenSegmentState,
    circleColor: Color = Color(0xFFF8EDA2),
) {

    val text by rememberUpdatedState(stringResource(id = segmentState.phase.label))
    val textStyle = BreathTheme.typography.titleMedium.copy(
        color = BreathTheme.colors.text,
        textAlign = TextAlign.Center
    )

    val fontFamilyResolver = LocalFontFamilyResolver.current
    val density = LocalDensity.current

    val animDuration by rememberUpdatedState(segmentState.modeDurationInSeconds * 1000)
    val animatedRadius by animateDpAsState(
        targetValue = transformFraction(
            value = segmentState.phase.fraction,
            startX = 0f,
            endX = 1f,
            startY = 72f,
            endY = 128f
        ).dp,
        label = "",
        animationSpec = tween(
            durationMillis = animDuration,
            easing = Ease
        )
    )

    Canvas(
        modifier = modifier.size(256.dp)
    ) {

        drawCircle(
            color = circleColor,
            center = this.center,
            radius = animatedRadius.toPx()
        )

        val textMeasurer = TextMeasurer(
            defaultFontFamilyResolver = fontFamilyResolver,
            defaultDensity = density,
            defaultLayoutDirection = this.layoutDirection
        )

        val textMeasurements = textMeasurer.measure(
            text = text,
            style = textStyle
        )

        drawText(
            textMeasurer = textMeasurer,
            text = text,
            topLeft = Offset(
                x = center.x - textMeasurements.size.width.toFloat() / 2,
                y = center.y - textMeasurements.size.height.toFloat() / 2
            ),
            style = textStyle
        )

    }

}