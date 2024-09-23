package io.proxima.breathe.presentation.main.sleep

import androidx.compose.runtime.Stable
import io.proxima.breathe.presentation.main.sleep.set_sleep_goal.SleepScreenSetSleepGoalUIComponentState
import kotlin.time.Duration.Companion.hours

@Stable
data class SleepScreenState(
    val currentSleepDuration: Long = 0.hours.inWholeMilliseconds,
    val sleepGoalDuration: Long? = null,
    val sleepGoalUIComponentState: SleepScreenSetSleepGoalUIComponentState = SleepScreenSetSleepGoalUIComponentState()
)