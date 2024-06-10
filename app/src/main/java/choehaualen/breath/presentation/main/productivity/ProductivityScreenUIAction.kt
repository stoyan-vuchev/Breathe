package choehaualen.breath.presentation.main.productivity

import androidx.compose.runtime.Immutable

@Immutable
sealed interface ProductivityScreenUIAction {

    data object NavigateUp : ProductivityScreenUIAction

    data class SetReminderEnabled(
        val id: String,
        val enabled: Boolean
    ) : ProductivityScreenUIAction

    data class SetReminderInterval(
        val id: String,
        val interval: Long
    ) : ProductivityScreenUIAction

}