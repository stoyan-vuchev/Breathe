package choehaualen.breath.presentation.main.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import choehaualen.breath.core.etc.UiString
import choehaualen.breath.data.preferences.AppPreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsScreenViewModel @Inject constructor(
    private val appPreferences: AppPreferences
) : ViewModel() {

    private val _uiActionChannel = Channel<SettingsScreenUIAction>()
    val uiActionFlow = _uiActionChannel.receiveAsFlow()

    private val _snackBarChannel = Channel<UiString>()
    val snackBarFlow = _snackBarChannel.receiveAsFlow()

    fun onUIAction(uiAction: SettingsScreenUIAction) = when (uiAction) {

        is SettingsScreenUIAction.NavigateUp -> sendUIAction(uiAction)

        else -> showSnackBar(
            msg = UiString.BasicString("Coming soon! :)")
        )

    }

    private fun sendUIAction(uiAction: SettingsScreenUIAction) {
        viewModelScope.launch { _uiActionChannel.send(uiAction) }
    }

    private fun showSnackBar(msg: UiString) {
        viewModelScope.launch { _snackBarChannel.send(msg) }
    }

}