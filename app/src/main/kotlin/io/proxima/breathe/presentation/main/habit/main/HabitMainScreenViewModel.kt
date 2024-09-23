package io.proxima.breathe.presentation.main.habit.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.proxima.breathe.data.preferences.AppPreferences
import dagger.hilt.android.lifecycle.HiltViewModel
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

            val hasOngoingHabit = username != null
                    && habitName != null
                    && habitQuote != null
                    && habitDuration != null

            _screenState.update {
                it.copy(
                    hasOngoingHabit = hasOngoingHabit,
                    username = username ?: "",
                    habitName = habitName ?: "",
                    habitQuote = habitQuote ?: "",
                    habitDuration = habitDuration ?: 0,
                    habitProgress = habitProgress ?: 0
                )
            }

        }
    }

    private fun sendUIAction(uiAction: HabitMainScreenUIAction) {
        viewModelScope.launch { _uiActionChannel.send(uiAction) }
    }

}