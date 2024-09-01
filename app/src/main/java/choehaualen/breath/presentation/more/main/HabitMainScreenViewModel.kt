package choehaualen.breath.presentation.more.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import choehaualen.breath.data.preferences.AppPreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class HabitMainScreenViewModel @Inject constructor(
    private val appPreferences: AppPreferences
) : ViewModel() {

    private val _screenState = MutableStateFlow(HabitMainScreenState())
    val screenState = _screenState.asStateFlow()

    init {
        initialize()
    }

    private fun initialize() {
        viewModelScope.launch {

            val username = withContext(Dispatchers.IO) { appPreferences.getUser() }
            val habitName = withContext(Dispatchers.IO) { appPreferences.getHabitName() }
            val habitQuote = withContext(Dispatchers.IO) { appPreferences.getHabitQuote() }
            val habitDuration = withContext(Dispatchers.IO) { appPreferences.getHabitDuration() }
            val habitProgress = withContext(Dispatchers.IO) { appPreferences.getHabitProgress() }

            _screenState.update {
                it.copy(
                    username = username ?: "",
                    name = habitName ?: "",
                    quote = habitQuote ?: "",
                    goalDuration = habitDuration ?: 0,
                    currentProgress = habitProgress ?: 0
                )
            }

        }
    }

}