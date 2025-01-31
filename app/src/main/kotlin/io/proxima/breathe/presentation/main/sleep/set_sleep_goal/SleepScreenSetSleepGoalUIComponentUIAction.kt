package io.proxima.breathe.presentation.main.sleep.set_sleep_goal

import androidx.compose.runtime.Immutable

@Immutable
sealed interface SleepScreenSetSleepGoalUIComponentUIAction {

    data class SetSleepHours(
        val sleepTimeHours: String
    ) : SleepScreenSetSleepGoalUIComponentUIAction

    data class SetSleepMinutes(
        val sleepMinutes: String
    ) : SleepScreenSetSleepGoalUIComponentUIAction

}