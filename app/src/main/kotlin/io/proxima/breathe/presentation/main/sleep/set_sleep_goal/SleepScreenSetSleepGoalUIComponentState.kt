package io.proxima.breathe.presentation.main.sleep.set_sleep_goal

import androidx.compose.runtime.Stable
import io.proxima.breathe.core.etc.UiString

@Stable
data class SleepScreenSetSleepGoalUIComponentState(
    val sleepTimeHours: String = "8",
    val sleepTimeMinutes: String = "0",
    val isSupportAlertShown: Boolean = false,
    val enabled: Boolean = true,
    val error: UiString = UiString.Empty
)