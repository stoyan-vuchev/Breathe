package io.proxima.breathe.presentation.main.habit.setup

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
class HabitSetupScreenViewModel @Inject constructor(
    private val appPreferences: AppPreferences
) : ViewModel() {

    private val _screenState = MutableStateFlow(HabitSetupScreenState())
    val screenState = _screenState.asStateFlow()

    private val _uiActionChannel = Channel<HabitSetupScreenUIAction>()
    val uiActionFlow = _uiActionChannel.receiveAsFlow()

    init {
        initialize()
    }

    fun onUIAction(uiAction: HabitSetupScreenUIAction) = when (uiAction) {

        is HabitSetupScreenUIAction.NavigateToMain -> sendUIAction(uiAction)

        is HabitSetupScreenUIAction.SetSegment -> {
            _screenState.update { it.copy(currentSegment = uiAction.segment) }
        }

        is HabitSetupScreenUIAction.NavigateUp -> if (
            _screenState.value.currentSegment != HabitSetupScreenSegment.HabitName
        ) _screenState.update {
            it.copy(
                currentSegment = when (it.currentSegment) {
                    is HabitSetupScreenSegment.HabitQuote -> HabitSetupScreenSegment.HabitName
                    is HabitSetupScreenSegment.HabitDuration -> HabitSetupScreenSegment.HabitQuote
                    is HabitSetupScreenSegment.HabitConfirmation -> HabitSetupScreenSegment.HabitDuration
                    else -> it.currentSegment
                }
            )
        } else sendUIAction(uiAction)

        is HabitSetupScreenUIAction.SetFieldValue -> onSetTextFieldValue(
            currentSegment = uiAction.currentSegment,
            newValue = uiAction.newValue
        )

        is HabitSetupScreenUIAction.Next -> onNext()

    }

    private fun onSetTextFieldValue(
        currentSegment: HabitSetupScreenSegment,
        newValue: String
    ) = _screenState.update {
        when (currentSegment) {
            is HabitSetupScreenSegment.HabitName -> it.copy(name = newValue)
            is HabitSetupScreenSegment.HabitQuote -> it.copy(quote = newValue)
            is HabitSetupScreenSegment.HabitDuration -> it.copy(goalDuration = newValue)
            else -> it
        }
    }

    private fun onNext() {

        when (_screenState.value.currentSegment) {

            is HabitSetupScreenSegment.HabitName -> {

                if (_screenState.value.name.isBlank()) return

                onUIAction(
                    HabitSetupScreenUIAction.SetSegment(
                        HabitSetupScreenSegment.HabitQuote
                    )
                )

            }

            is HabitSetupScreenSegment.HabitQuote -> {

                if (_screenState.value.quote.isBlank()) return

                onUIAction(
                    HabitSetupScreenUIAction.SetSegment(
                        HabitSetupScreenSegment.HabitDuration
                    )
                )

            }

            is HabitSetupScreenSegment.HabitDuration -> {

                if (_screenState.value.goalDuration.isBlank()) return

                onUIAction(
                    HabitSetupScreenUIAction.SetSegment(
                        HabitSetupScreenSegment.HabitConfirmation
                    )
                )

            }

            is HabitSetupScreenSegment.HabitConfirmation -> saveHabit()
            else -> Unit

        }

    }

    private fun saveHabit() {
        viewModelScope.launch {

            withContext(Dispatchers.IO) {
                appPreferences.setHabitName(_screenState.value.name)
                appPreferences.setHabitQuote(_screenState.value.quote)
                appPreferences.setHabitDuration(_screenState.value.goalDuration.toInt())
            }

            onUIAction(HabitSetupScreenUIAction.NavigateToMain)

        }
    }

    private fun sendUIAction(uiAction: HabitSetupScreenUIAction) {
        viewModelScope.launch { _uiActionChannel.send(uiAction) }
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

            if (hasOngoingHabit) {

                onUIAction(HabitSetupScreenUIAction.NavigateToMain)

            } else {

                _screenState.update {
                    it.copy(
                        username = username ?: "",
                        name = habitName ?: "",
                        quote = habitQuote ?: "",
                        goalDuration = "${habitDuration ?: ""}",
                        currentProgress = habitProgress ?: 0,
                        currentSegment = HabitSetupScreenSegment.HabitName
                    )
                }

            }

        }
    }

}