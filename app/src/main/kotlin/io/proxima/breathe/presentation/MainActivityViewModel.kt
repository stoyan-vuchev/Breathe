package io.proxima.breathe.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.proxima.breathe.core.etc.Result
import io.proxima.breathe.data.preferences.AppPreferences
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val preferences: AppPreferences
) : ViewModel() {

    private val _isUserAuthenticated = MutableStateFlow<Boolean?>(null)
    val isUserAuthenticated = _isUserAuthenticated.asStateFlow()

    fun onAuthenticateUser() {
        viewModelScope.launch {
            val result = withContext(Dispatchers.IO) { preferences.getUser() }
            _isUserAuthenticated.update {
                when (result) {
                    is Result.Success -> result.data?.isNotEmpty() ?: false
                    is Result.Error -> false
                }
            }
        }
    }

}