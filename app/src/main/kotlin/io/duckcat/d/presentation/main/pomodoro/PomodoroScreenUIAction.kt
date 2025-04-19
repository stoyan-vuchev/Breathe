package io.duckcat.d.presentation.main.pomodoro

import androidx.compose.runtime.Immutable

@Immutable
sealed interface PomodoroScreenUIAction {
    data object NavigateUp : PomodoroScreenUIAction
    // You can add additional actions as needed.
    // For example:
    // data object StartTimer : PomodoroScreenUIAction
    // data object StopTimer : PomodoroScreenUIAction
    // data class SetTask(val taskName: String) : PomodoroScreenUIAction
}
