package io.proxima.breathe.presentation.main.settings.about

import androidx.compose.runtime.Immutable

@Immutable
sealed interface SettingsAboutScreenUIAction {

    data object NavigateUp : SettingsAboutScreenUIAction

}