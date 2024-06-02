package choehaualen.breath.presentation.main.sleep

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import choehaualen.breath.data.manager.SleepManager
import choehaualen.breath.data.preferences.AppPreferences
import choehaualen.breath.presentation.main.sleep.sleep_goal.SleepScreenSetSleepGoalUIComponentUIAction
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

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