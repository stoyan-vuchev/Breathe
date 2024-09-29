package io.proxima.breathe.presentation.main.settings.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.proxima.breathe.data.preferences.AppPreferences
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SettingsProfileScreenViewModel @Inject constructor(
    private val appPreferences: AppPreferences
) : ViewModel() {

    private val _uiActionChannel = Channel<SettingsProfileScreenUIAction>()
    val uiActionFlow = _uiActionChannel.receiveAsFlow()

    private val _screenState = MutableStateFlow(SettingsProfileScreenState())
    val screenState = _screenState.asStateFlow()

    init {
        initialize()
    }

    private fun initialize() {
        viewModelScope.launch {
            val username = withContext(Dispatchers.IO) { appPreferences.getUser().data ?: "" }
            _screenState.update {
                it.copy(
                    username = username,
                    usernameTextFieldText = username
                )
            }
        }
    }

    fun onUIAction(uiAction: SettingsProfileScreenUIAction) {
        when (uiAction) {
            is SettingsProfileScreenUIAction.NavigateUp -> sendUIAction(uiAction)
            is SettingsProfileScreenUIAction.Edit -> editUsername()
            is SettingsProfileScreenUIAction.Save -> saveUsername()
            is SettingsProfileScreenUIAction.SetUsernameTextFieldText -> setText(uiAction.newText)
        }
    }

    private fun setText(newText: String) {
        _screenState.update { it.copy(usernameTextFieldText = newText) }
    }

    private fun editUsername() {
        viewModelScope.launch {
            _screenState.update { it.copy(isInEditMode = true) }
            delay(360L)
            sendUIAction(SettingsProfileScreenUIAction.Edit)
        }
    }

    private fun saveUsername() {
        viewModelScope.launch {
            val username = _screenState.value.usernameTextFieldText
            if (username != _screenState.value.username && username.isNotBlank()) {
                withContext(Dispatchers.IO) { appPreferences.setUser(username) }
                val newUsername = withContext(Dispatchers.IO) { appPreferences.getUser() }
                _screenState.update { it.copy(username = newUsername.data ?: username) }
            } else {
                _screenState.update {
                    it.copy(usernameTextFieldText = _screenState.value.username)
                }
            }
            _screenState.update { it.copy(isInEditMode = false) }
        }
    }

    private fun sendUIAction(uiAction: SettingsProfileScreenUIAction) {
        viewModelScope.launch { _uiActionChannel.send(uiAction) }
    }

}