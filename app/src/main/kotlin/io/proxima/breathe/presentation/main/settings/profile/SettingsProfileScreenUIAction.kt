package io.proxima.breathe.presentation.main.settings.profile

import androidx.compose.runtime.Immutable

@Immutable
sealed interface SettingsProfileScreenUIAction {

    data object NavigateUp : SettingsProfileScreenUIAction
    data object Edit : SettingsProfileScreenUIAction
    data object Save : SettingsProfileScreenUIAction

    data class SetUsernameTextFieldText(
        val newText: String
    ) : SettingsProfileScreenUIAction

}