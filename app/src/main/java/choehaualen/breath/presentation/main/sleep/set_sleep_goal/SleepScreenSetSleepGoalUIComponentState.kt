package choehaualen.breath.presentation.main.sleep.set_sleep_goal

import androidx.compose.runtime.Stable
import choehaualen.breath.core.etc.UiString

@Stable
data class SleepScreenSetSleepGoalUIComponentState(
    val sleepTimeHours: String = "8",
    val sleepTimeMinutes: String = "0",
    val isSupportAlertShown: Boolean = false,
    val enabled: Boolean = true,
    val error: UiString = UiString.Empty
)