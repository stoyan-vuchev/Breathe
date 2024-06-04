package choehaualen.breath.presentation.main.settings

import androidx.compose.runtime.Immutable

@Immutable
sealed interface SettingsScreenUIAction {

    data object NavigateUp : SettingsScreenUIAction

    data object Profile : SettingsScreenUIAction

    data object ShowDeleteDataDialog : SettingsScreenUIAction
    data object DismissDeleteDataDialog : SettingsScreenUIAction
    data object ConfirmDeleteData : SettingsScreenUIAction

    data object Notifications : SettingsScreenUIAction
    data object Themes : SettingsScreenUIAction
    data object About : SettingsScreenUIAction

}