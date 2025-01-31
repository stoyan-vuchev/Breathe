package io.proxima.breathe.presentation.main.sleep

import androidx.compose.runtime.Immutable
import io.proxima.breathe.presentation.main.sleep.set_sleep_goal.SleepScreenSetSleepGoalUIComponentUIAction

@Immutable
sealed interface SleepScreenUIAction {

    data object NavigateUp : SleepScreenUIAction

    data class SleepGoalUIAction(
        val value: SleepScreenSetSleepGoalUIComponentUIAction
    ) : SleepScreenUIAction

    data object Next : SleepScreenUIAction

}