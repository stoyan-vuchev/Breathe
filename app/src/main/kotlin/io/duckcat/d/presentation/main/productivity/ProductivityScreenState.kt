package io.duckcat.d.presentation.main.productivity

import androidx.compose.runtime.Stable

@Stable
data class ProductivityScreenState(
    val isWaterIntakeReminderEnabled: Boolean = false,
    val isReadBookReminderEnabled: Boolean = false,
    val isBasicWorkoutReminderEnabled: Boolean = false,
    val isTouchGrassReminderEnabled: Boolean = false
)
