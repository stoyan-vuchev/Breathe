package choehaualen.breath.presentation.main.sleep

import androidx.compose.runtime.Stable
import choehaualen.breath.presentation.main.sleep.sleep_goal.SleepScreenSetSleepGoalUIComponentState

@Stable
data class SleepScreenState(
    val sleepGoalDuration: Long? = null,
    val sleepGoalUIComponentState: SleepScreenSetSleepGoalUIComponentState = SleepScreenSetSleepGoalUIComponentState()
)