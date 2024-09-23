package io.proxima.breathe.presentation.boarding.user

import androidx.compose.runtime.Stable

@Stable
data class UserScreenState(
    val currentSegment: UserScreenSegment = UserScreenSegment.Username,
    val username: String? = null,
    val usernameText: String = "",
    val usernameValidationResult: UsernameValidationResult = UsernameValidationResult.ValidUsername,
    val bedtimeHour: Int = 22,
    val bedtimeMinute: Int = 0,
    val wakeUpHour: Int = 8,
    val wakeUpMinute: Int = 0,
    val sleepValidationResult: SleepValidationResult = SleepValidationResult.ValidSleepDuration
)