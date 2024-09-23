package io.proxima.breathe.presentation.main.habit.checkpoint

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.proxima.breathe.data.preferences.AppPreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class HabitCheckpointScreenViewModel @Inject constructor(
    private val appPreferences: AppPreferences
) : ViewModel() {

    private val _screenState = MutableStateFlow(HabitCheckpointScreenState())
    val screenState = _screenState.asStateFlow()

    init {
        fetchHabitQuote()
    }

    fun onUIAction(uiAction: HabitCheckpointUIAction) = when (uiAction) {

        is HabitCheckpointUIAction.Pass -> _screenState.update {
            it.copy(currentSegment = HabitCheckpointScreenSegment.Pass)
        }

        is HabitCheckpointUIAction.Fail -> _screenState.update {
            it.copy(currentSegment = HabitCheckpointScreenSegment.Fail)
        }

        is HabitCheckpointUIAction.Continue -> {}

        is HabitCheckpointUIAction.Stay -> {}

        is HabitCheckpointUIAction.NavigateUp -> _screenState.update {
            it.copy(currentSegment = HabitCheckpointScreenSegment.Checkpoint)
        }

    }

    private fun fetchHabitQuote() {
        viewModelScope.launch {
            val habitQuote = withContext(Dispatchers.IO) { appPreferences.getHabitQuote() }
            _screenState.update { it.copy(habitQuote = habitQuote ?: "") }
        }
    }

}