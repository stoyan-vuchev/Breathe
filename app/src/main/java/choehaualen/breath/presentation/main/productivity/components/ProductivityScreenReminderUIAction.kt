package choehaualen.breath.presentation.main.productivity.components

import androidx.compose.runtime.Immutable
import choehaualen.breath.presentation.main.productivity.ProductivityReminderInterval

@Immutable
sealed interface ProductivityScreenReminderUIAction {

    data class SetEnabled(
        val enabled: Boolean
    ) : ProductivityScreenReminderUIAction

    data class SetInterval(
        val interval: ProductivityReminderInterval
    ) : ProductivityScreenReminderUIAction

}