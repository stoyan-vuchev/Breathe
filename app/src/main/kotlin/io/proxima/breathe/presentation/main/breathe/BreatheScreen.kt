package io.proxima.breathe.presentation.main.breathe

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import io.proxima.breathe.core.ui.ScreenTransitions
import io.proxima.breathe.core.ui.theme.MelonColors
import io.proxima.breathe.core.ui.theme.ProvideBreathColors
import io.proxima.breathe.core.ui.theme.backgroundBrush
import io.proxima.breathe.core.ui.theme.BreathTheme
import io.proxima.breathe.presentation.main.breathe.segment.BreatheScreenMainSegment
import io.proxima.breathe.presentation.main.breathe.segment.BreatheScreenSegment
import io.proxima.breathe.presentation.main.breathe.segment.BreatheScreenSegmentType

@Composable
fun BreatheScreen(
    screenState: BreatheScreenState,
    onUIAction: (BreatheScreenUIAction) -> Unit
) {

    BackHandler(
        enabled = screenState.segmentType !is BreatheScreenSegmentType.Main,
        onBack = {
            onUIAction(
                BreatheScreenUIAction.SetSegment(
                    BreatheScreenSegmentType.Main
                )
            )
        }
    )

    AnimatedContent(
        modifier = Modifier
            .fillMaxSize()
            .background(BreathTheme.colors.backgroundBrush()),
        targetState = screenState.segmentType,
        label = "",
        transitionSpec = {

            if (this.targetState !is BreatheScreenSegmentType.Main) {

                ScreenTransitions.enterTransition
                    .togetherWith(ScreenTransitions.exitTransition)

            } else ScreenTransitions.popEnterTransition
                .togetherWith(ScreenTransitions.popExitTransition)

        }
    ) { currentSegment ->

        when (currentSegment) {

            is BreatheScreenSegmentType.Main -> BreatheScreenMainSegment(
                onUIAction = onUIAction
            )

            is BreatheScreenSegmentType.DeepBreathing -> BreatheScreenSegment(
                segmentType = BreatheScreenSegmentType.DeepBreathing,
                segmentState = screenState.segmentState,
                onUIAction = onUIAction
            )

            is BreatheScreenSegmentType.AlertMode -> BreatheScreenSegment(
                segmentType = BreatheScreenSegmentType.AlertMode,
                segmentState = screenState.segmentState,
                onUIAction = onUIAction
            )

            is BreatheScreenSegmentType.CalmMode -> BreatheScreenSegment(
                segmentType = BreatheScreenSegmentType.CalmMode,
                segmentState = screenState.segmentState,
                onUIAction = onUIAction
            )

        }

    }

}

@Preview
@Composable
private fun BreatheScreenPreview() = BreathTheme {
    ProvideBreathColors(MelonColors) {
        BreatheScreen(
            screenState = BreatheScreenState(),
            onUIAction = {}
        )
    }
}