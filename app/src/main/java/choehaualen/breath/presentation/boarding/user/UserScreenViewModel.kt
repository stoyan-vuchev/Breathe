package choehaualen.breath.presentation.boarding.user

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import choehaualen.breath.data.preferences.AppPreferences
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UserScreenViewModel(
    private val preferences: AppPreferences
) : ViewModel() {

    private val _screenState = MutableStateFlow(UserScreenState())
    val screenState = _screenState.asStateFlow()

    private val _isUserSetChannel = Channel<Boolean?>()
    val isUserSetFlow = _isUserSetChannel.receiveAsFlow()

    fun onUIAction(uiAction: UserScreenUIAction) = when (uiAction) {
        is UserScreenUIAction.Next -> saveUsernameOrNavigate()
        is UserScreenUIAction.SetNameText -> onSetUsernameText(uiAction.text)
        else -> Unit // Unit basically returns nothing.
    }

    init {
        viewModelScope.launch {
            val username = withContext(Dispatchers.IO) { preferences.getUser() }
            _screenState.update { it.copy(username = username) }
        }
    }

    private fun onSetUsernameText(text: String) {
        _screenState.update { currentState ->
            currentState.copy(nameText = text)
        }
    }

    private fun saveUsernameOrNavigate() {
        if (screenState.value.username == null) checkUsernameForFlaws()
        else viewModelScope.launch { _isUserSetChannel.send(true) }
    }

    private fun checkUsernameForFlaws() {

        val result = UsernameValidator.validateUsername(screenState.value.nameText)

        _screenState.update { currentState ->
            currentState.copy(usernameValidationResult = result)
        }

        if (result is UsernameValidationResult.ValidUsername) {
            saveUsernameToPreferences()
        }

    }

    private fun saveUsernameToPreferences() {
        viewModelScope.launch {
            val username = screenState.value.nameText
            withContext(Dispatchers.IO) { preferences.setUser(username) }
                .also { viewModelScope.launch { _isUserSetChannel.send(true) } }
        }
    }

}