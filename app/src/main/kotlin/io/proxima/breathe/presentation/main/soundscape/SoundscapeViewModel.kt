package io.proxima.breathe.presentation.main.soundscape

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SoundscapeViewModel @Inject constructor() : ViewModel() {

    private val _screenState = MutableStateFlow(SoundscapeScreenState())
    val screenState = _screenState.asStateFlow()

    private val _uiActionChannel = Channel<SoundscapeUIAction>()
    val uiActionFlow = _uiActionChannel.receiveAsFlow()

    fun onUIAction(uiAction: SoundscapeUIAction) = when (uiAction) {
        is SoundscapeUIAction.NavigateUp -> sendUIAction(uiAction)
    }

    private fun sendUIAction(uiAction: SoundscapeUIAction) {
        viewModelScope.launch { _uiActionChannel.send(uiAction) }
    }

}