package choehaualen.breath.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import choehaualen.breath.data.preferences.AppPreferences
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivityViewModel(
    private val preferences: AppPreferences
) : ViewModel() {

    private val _isUserAuthenticated = MutableStateFlow<Boolean?>(null)
    val isUserAuthenticated = _isUserAuthenticated.asStateFlow()

    fun onAuthenticateUser() {
        viewModelScope.launch {
            val username = withContext(Dispatchers.IO) { preferences.getUser() }
            _isUserAuthenticated.update { username != null }
        }
    }

}