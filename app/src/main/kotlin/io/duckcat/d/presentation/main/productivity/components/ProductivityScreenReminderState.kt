package io.duckcat.d.presentation.main.productivity.components

import androidx.compose.runtime.Stable
import io.duckcat.d.presentation.main.productivity.ProductivityReminderInterval

@Stable
data class ProductivityScreenReminderState(
    val enabled: Boolean = false,
    val interval: ProductivityReminderInterval? = null // if the reminder has a fixed interval, do not show it in the UI
)