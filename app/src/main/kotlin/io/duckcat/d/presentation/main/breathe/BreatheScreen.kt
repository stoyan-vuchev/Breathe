package io.duckcat.d.presentation.main.breathe

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import io.duckcat.d.R
import io.duckcat.d.core.ui.ScreenTransitions
import io.duckcat.d.core.ui.theme.BreathTheme
import io.duckcat.d.core.ui.theme.MelonColors
import io.duckcat.d.core.ui.theme.ProvideBreathColors
import io.duckcat.d.presentation.main.breathe.segment.BreatheScreenMainSegment
import io.duckcat.d.presentation.main.breathe.segment.BreatheScreenSegment
import io.duckcat.d.presentation.main.breathe.segment.BreatheScreenSegmentType

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

    Box(modifier = Modifier.fillMaxSize()) {

        Image(
            painter = painterResource(id = R.drawable.forest_blurred), // Change this to your image
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )


        AnimatedContent(
            modifier = Modifier.fillMaxSize(),
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
