package io.proxima.breathe.presentation.main.sleep

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.proxima.breathe.core.etc.Result
import io.proxima.breathe.core.etc.Result.Companion.DefaultError
import io.proxima.breathe.core.etc.UiString
import io.proxima.breathe.data.manager.SleepManager
import io.proxima.breathe.data.preferences.AppPreferences
import io.proxima.breathe.presentation.main.sleep.set_sleep_goal.SleepScreenSetSleepGoalUIComponentUIAction
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
import kotlin.time.Duration.Companion.hours

// What should we do now? bro i just remembered, very long ago the sleep segment data
//showed on breathe, I remember that , so the issue is not on our end xd.yes anyways
//lets do that productivity reminder thing, similar to water the other ones also push notifications.
// let's fix the reminders showing within the awake time.
// yeah we have the sleep and wake time :)(usual) yep

@HiltViewModel
class SleepScreenViewModel @Inject constructor(
    private val appPreferences: AppPreferences,
    private val sleepManager: SleepManager
) : ViewModel() {

    private val _screenState = MutableStateFlow(SleepScreenState())
    val screenState = _screenState.asStateFlow()

    private val _uiActionChannel = Channel<SleepScreenUIAction>()
    val uiActionFlow = _uiActionChannel.receiveAsFlow()

    fun onUIAction(uiAction: SleepScreenUIAction) = when (uiAction) {
        is SleepScreenUIAction.NavigateUp -> sendUIAction(uiAction)
        is SleepScreenUIAction.SleepGoalUIAction -> onSleepGoalUIAction(uiAction)
        is SleepScreenUIAction.Next -> setSleepGoal()
    }

    init {
        getSleepData()
        viewModelScope.launch {
            val sleepGoal = withContext(Dispatchers.IO) { appPreferences.getSleepGoal() }
            _screenState.update { currentState ->
                currentState.copy(
                    sleepGoalDuration = sleepGoal,
                    sleepGoalUIComponentState = currentState.sleepGoalUIComponentState.copy(
                        isSupportAlertShown = sleepGoal == null
                    )
                )
            }
        }
    }

    private fun setSleepGoal() {

        val hours = _screenState.value.sleepGoalUIComponentState.sleepTimeHours
        val minutes = _screenState.value.sleepGoalUIComponentState.sleepTimeMinutes

        when (val result = SleepGoalValidator.validate(hours, minutes)) {

            is SleepGoalValidatorResult.ShortDuration -> showError(result.error)
            is SleepGoalValidatorResult.LongDuration -> showError(result.error)

            is SleepGoalValidatorResult.Valid -> {
                val sleepGoal = convertHoursAndMinutesStringToMillis(hours, minutes)
                saveSleepGoal(sleepGoal)
            }

        }

    }

    private fun getSleepData() {
        viewModelScope.launch {

            when (val result = sleepManager.readSleepData()) {

                is Result.Success -> {

                    // We don't have any sleep data here.
                    // how abt test it on a device which recorded it?

                    var totalSleepDuration = 6.hours.inWholeMilliseconds
                    result.data
                        ?.mapNotNull { it.totalSleepDuration }
                        ?.forEach { totalSleepDuration += it }

                    _screenState.update { currentState ->
                        currentState.copy(
                            currentSleepDuration = totalSleepDuration
                        )
                    }

                }

                is Result.Error -> showError(result.error ?: DefaultError)

            }

        }
    }

    private fun showError(error: UiString) {
        _screenState.update { currentState ->
            currentState.copy(
                sleepGoalUIComponentState = currentState.sleepGoalUIComponentState.copy(
                    error = error
                )
            )
        }
    }

    private fun saveSleepGoal(sleepGoal: Long) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) { appPreferences.setSleepGoal(sleepGoal) }
            _screenState.update { currentState ->
                currentState.copy(sleepGoalDuration = sleepGoal)
            }
        }
    }

    private fun onSleepGoalUIAction(
        uiAction: SleepScreenUIAction.SleepGoalUIAction
    ) = when (uiAction.value) {

        is SleepScreenSetSleepGoalUIComponentUIAction.SetSleepHours -> {
            _screenState.update { currentState ->
                currentState.copy(
                    sleepGoalUIComponentState = currentState.sleepGoalUIComponentState.copy(
                        sleepTimeHours = uiAction.value.sleepTimeHours
                    )
                )
            }
        }

        is SleepScreenSetSleepGoalUIComponentUIAction.SetSleepMinutes -> {
            _screenState.update { currentState ->
                currentState.copy(
                    sleepGoalUIComponentState = currentState.sleepGoalUIComponentState.copy(
                        sleepTimeMinutes = uiAction.value.sleepMinutes
                    )
                )
            }
        }

    }

    private fun sendUIAction(uiAction: SleepScreenUIAction) {
        viewModelScope.launch { _uiActionChannel.send(uiAction) }
    }

}