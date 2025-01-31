package io.proxima.breathe.presentation.main.habit.main

import androidx.compose.runtime.Stable

@Stable
data class HabitMainScreenState(
    val hasOngoingHabit: Boolean? = null,
    val username: String = "",
    val habitName: String = "",
    val habitQuote: String = "",
    val habitDuration: Int = 0,
    val habitProgress: Int = 0
)