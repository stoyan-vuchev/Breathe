package io.proxima.breathe.presentation.main.habit.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.proxima.breathe.core.etc.Result
import io.proxima.breathe.data.preferences.AppPreferences
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
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

    private val _uiActionChannel = Channel<HabitMainScreenUIAction>()
    val uiActionFlow = _uiActionChannel.receiveAsFlow()

    init {
        initialize()
    }

    fun onUIAction(uiAction: HabitMainScreenUIAction) = when (uiAction) {
        is HabitMainScreenUIAction.NavigateUp -> sendUIAction(uiAction)
    }

    private fun initialize() {
        viewModelScope.launch {

            val username = withContext(Dispatchers.IO) { appPreferences.getUser() }
            val habitName = withContext(Dispatchers.IO) { appPreferences.getHabitName() }
            val habitQuote = withContext(Dispatchers.IO) { appPreferences.getHabitQuote() }
            val habitDuration = withContext(Dispatchers.IO) { appPreferences.getHabitDuration() }
            val habitProgress = withContext(Dispatchers.IO) { appPreferences.getHabitProgress() }

            val hasOngoingHabit = username is Result.Success && username.data != null
                    && habitName is Result.Success && habitName.data != null
                    && habitQuote is Result.Success && habitQuote.data != null
                    && habitDuration is Result.Success && habitDuration.data != null

            _screenState.update {
                it.copy(
                    hasOngoingHabit = hasOngoingHabit,
                    username = username.data ?: "",
                    habitName = habitName.data ?: "",
                    habitQuote = habitQuote.data ?: "",
                    habitDuration = habitDuration.data ?: 0,
                    habitProgress = habitProgress.data ?: 0
                )
            }

        }
    }

    private fun sendUIAction(uiAction: HabitMainScreenUIAction) {
        viewModelScope.launch { _uiActionChannel.send(uiAction) }
    }

}