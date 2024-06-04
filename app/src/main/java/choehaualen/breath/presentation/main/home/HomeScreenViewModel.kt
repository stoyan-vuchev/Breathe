package choehaualen.breath.presentation.main.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor() : ViewModel() {

    private val _uiActionChannel = Channel<HomeScreenUIAction>()
    val uiActionFlow = _uiActionChannel.receiveAsFlow()

    fun onUIAction(uiAction: HomeScreenUIAction) = when (uiAction) {
        is HomeScreenUIAction.NavigateToSleep -> sendUIAction(uiAction)
        is HomeScreenUIAction.NavigateToBreathe -> sendUIAction(uiAction)
    }

    private fun sendUIAction(uiAction: HomeScreenUIAction) {
        viewModelScope.launch { _uiActionChannel.send(uiAction) }
    }

}