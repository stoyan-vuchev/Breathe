package io.proxima.breathe.presentation.main.productivity

import androidx.compose.runtime.Immutable

@Immutable
sealed interface ProductivityScreenUIAction {
    data object NavigateUp : ProductivityScreenUIAction
    data class SetReminderEnabled(val id: String, val enabled: Boolean) : ProductivityScreenUIAction
}
