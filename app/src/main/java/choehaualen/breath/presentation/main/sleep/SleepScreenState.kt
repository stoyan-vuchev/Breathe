package choehaualen.breath.presentation.main.sleep

import androidx.compose.runtime.Stable
import choehaualen.breath.presentation.main.sleep.set_sleep_goal.SleepScreenSetSleepGoalUIComponentState
import kotlin.time.Duration.Companion.hours

@Stable
data class SleepScreenState(
    val currentSleepDuration: Long = 0.hours.inWholeMilliseconds,
    val sleepGoalDuration: Long? = null,
    val sleepGoalUIComponentState: SleepScreenSetSleepGoalUIComponentState = SleepScreenSetSleepGoalUIComponentState()
)