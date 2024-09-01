package choehaualen.breath.presentation.main.habit.main

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import choehaualen.breath.core.ui.ScreenTransitions
import choehaualen.breath.core.ui.colors.backgroundBrush
import choehaualen.breath.core.ui.theme.BreathTheme
import choehaualen.breath.presentation.main.habit.main.segment.HabitMainScreenHabitNameSegment

@Composable
fun HabitMainScreen(
    screenState: HabitMainScreenState,
    onUIAction: (HabitMainScreenUIAction) -> Unit
) {

    BackHandler(
        enabled = screenState.currentSegment !is HabitMainScreenSegment.HabitName
                && screenState.currentSegment !is HabitMainScreenSegment.HabitMain
                && screenState.currentSegment !is HabitMainScreenSegment.HabitConfirmation,
        onBack = {
            onUIAction(
                HabitMainScreenUIAction.SetSegment(
                    HabitMainScreenSegment.HabitMain
                )
            )
        }
    )

    AnimatedContent(
        modifier = Modifier
            .fillMaxSize()
            .background(BreathTheme.colors.backgroundBrush()),
        targetState = screenState.currentSegment,
        label = "",
        transitionSpec = {

            // this random fly in is kinda desturbing yeah but ig fine? yeah, lemme see sth,

            if (
                this.targetState !is HabitMainScreenSegment.HabitMain
            ) {
                ScreenTransitions.enterTransition
                    .togetherWith(ScreenTransitions.exitTransition)
            } else ScreenTransitions.popEnterTransition
                .togetherWith(ScreenTransitions.popExitTransition)

        }
    ) { currentSegment ->

        when (currentSegment) {

            HabitMainScreenSegment.HabitMain -> {}

            HabitMainScreenSegment.HabitName -> HabitMainScreenHabitNameSegment(
                screenState = screenState,
                onUIAction = onUIAction
            )

            HabitMainScreenSegment.HabitQuote -> {}

            HabitMainScreenSegment.HabitDuration -> {}

            HabitMainScreenSegment.HabitConfirmation -> {}

            null -> Box(modifier = Modifier.fillMaxSize())

            // there we gooo yes now it looks simliar to all oteer ones
            // yes, it follows the Breath design system.:)

        }

    }

}