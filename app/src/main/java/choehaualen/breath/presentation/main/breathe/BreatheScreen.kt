package choehaualen.breath.presentation.main.breathe

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import choehaualen.breath.core.ui.ScreenTransitions
import choehaualen.breath.core.ui.colors.MelonColors
import choehaualen.breath.core.ui.colors.ProvideBreathColors
import choehaualen.breath.core.ui.colors.backgroundBrush
import choehaualen.breath.core.ui.theme.BreathTheme
import choehaualen.breath.presentation.main.breathe.segment.BreatheScreenMainSegment
import choehaualen.breath.presentation.main.breathe.segment.BreatheScreenSegment
import choehaualen.breath.presentation.main.breathe.segment.BreatheScreenSegmentType

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