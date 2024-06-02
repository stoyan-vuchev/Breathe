package choehaualen.breath.presentation.main.sleep

import androidx.compose.runtime.Immutable
import choehaualen.breath.presentation.main.sleep.sleep_goal.SleepScreenSetSleepGoalUIComponentUIAction

@Immutable
sealed interface SleepScreenUIAction {

    data object NavigateUp : SleepScreenUIAction

    data class SleepGoalUIAction(
        val value: SleepScreenSetSleepGoalUIComponentUIAction
    ) : SleepScreenUIAction

}