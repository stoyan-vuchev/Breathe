package io.proxima.breathe.presentation.main.productivity.components

import androidx.compose.runtime.Immutable
import io.proxima.breathe.presentation.main.productivity.ProductivityReminderInterval

@Immutable
sealed interface ProductivityScreenReminderUIAction {

    data class SetEnabled(
        val enabled: Boolean
    ) : ProductivityScreenReminderUIAction

    data class SetInterval(
        val interval: ProductivityReminderInterval
    ) : ProductivityScreenReminderUIAction

}