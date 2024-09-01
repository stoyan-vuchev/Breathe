package choehaualen.breath.presentation.main.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import choehaualen.breath.core.etc.UiString
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor() : ViewModel() {

    private val _uiActionChannel = Channel<HomeScreenUIAction>()
    val uiActionFlow = _uiActionChannel.receiveAsFlow()

    private val _snackBarChannel = Channel<UiString>()
    val snackBarFlow = _snackBarChannel.receiveAsFlow()

    fun onUIAction(uiAction: HomeScreenUIAction) = when (uiAction) {

        is HomeScreenUIAction.NavigateToSleep -> sendUIAction(uiAction)
        is HomeScreenUIAction.NavigateToBreathe -> sendUIAction(uiAction)
        is HomeScreenUIAction.NavigateToSoundscape -> sendUIAction(uiAction)
        is HomeScreenUIAction.NavigateToProductivity -> sendUIAction(uiAction)

        is HomeScreenUIAction.ExpandQuote -> showSnackbar(
            msg = UiString.BasicString("Quotes are coming soon! :)")
        )

        is HomeScreenUIAction.Zone -> showSnackbar(
            msg = UiString.BasicString("More cool stuff is coming soon! <3")
        )

        is HomeScreenUIAction.NavigateToSettings -> sendUIAction(uiAction)

    }

    private fun sendUIAction(uiAction: HomeScreenUIAction) {
        viewModelScope.launch { _uiActionChannel.send(uiAction) }
    }

    private fun showSnackbar(msg: UiString) {
        viewModelScope.launch { _snackBarChannel.send(msg) }
    }

}