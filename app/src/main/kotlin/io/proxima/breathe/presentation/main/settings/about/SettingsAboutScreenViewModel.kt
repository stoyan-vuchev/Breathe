package io.proxima.breathe.presentation.main.settings.about

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsAboutScreenViewModel @Inject constructor() : ViewModel() {

    private val _uiActionChannel = Channel<SettingsAboutScreenUIAction>()
    val uiActionFlow = _uiActionChannel.receiveAsFlow()

    fun onUIAction(uiAction: SettingsAboutScreenUIAction) {
        when (uiAction) {
            is SettingsAboutScreenUIAction.NavigateUp -> sendUIAction(uiAction)
        }
    }

    private fun sendUIAction(uiAction: SettingsAboutScreenUIAction) {
        viewModelScope.launch { _uiActionChannel.send(uiAction) }
    }

}