package io.proxima.breathe.presentation.main.productivity

import androidx.compose.runtime.Stable

@Stable
data class ProductivityScreenState(
    val isWaterIntakeReminderEnabled: Boolean = false
)