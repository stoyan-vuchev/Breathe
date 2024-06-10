package choehaualen.breath.presentation.boarding.user

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import choehaualen.breath.data.preferences.AppPreferences
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
class UserScreenViewModel @Inject constructor(
    private val preferences: AppPreferences
) : ViewModel() {

    private val _screenState = MutableStateFlow(UserScreenState())
    val screenState = _screenState.asStateFlow()

    private val _uiActionChannel = Channel<UserScreenUIAction>()
    val uiActionFlow = _uiActionChannel.receiveAsFlow()

    fun onUIAction(uiAction: UserScreenUIAction) = when (uiAction) {
        is UserScreenUIAction.SetUsernameText -> onSetUsernameText(uiAction.text)
        is UserScreenUIAction.SetBedtime -> setBedTime(uiAction.hour, uiAction.minute)
        is UserScreenUIAction.SetWakeUp -> setWakeUpTime(uiAction.hour, uiAction.minute)
        is UserScreenUIAction.GoToUsernameSegment -> onGoToSegment(UserScreenSegment.Username)
        is UserScreenUIAction.GoToBedtimeSegment -> checkUsernameForFlaws()
        is UserScreenUIAction.GoToWakeUpSegment -> onGoToSegment(UserScreenSegment.UsualWakeUpTime)
        is UserScreenUIAction.GoToGreetSegment -> checkSleepDataForFlaws()
        is UserScreenUIAction.GetStarted -> sendUIAction(UserScreenUIAction.GetStarted)
        else -> Unit // Unit basically returns nothing.
    }

    init {
        viewModelScope.launch {
            val username = withContext(Dispatchers.IO) { preferences.getUser() }
            _screenState.update { currentState ->
                currentState.copy(
                    currentSegment = username?.let { UserScreenSegment.Greet }
                        ?: UserScreenSegment.Username,
                    username = username
                )
            }
        }
    }

    private fun onSetUsernameText(text: String) {
        _screenState.update { currentState ->
            currentState.copy(usernameText = text)
        }
    }

    private fun onGoToSegment(segment: UserScreenSegment) {
        _screenState.update { currentState ->
            currentState.copy(currentSegment = segment)
        }
    }

    private fun setBedTime(
        hour: Int,
        minute: Int
    ) {
        _screenState.update { currentState ->
            currentState.copy(
                bedtimeHour = hour,
                bedtimeMinute = minute
            )
        }
    }

    private fun setWakeUpTime(
        hour: Int,
        minute: Int
    ) {
        _screenState.update { currentState ->
            currentState.copy(
                wakeUpHour = hour,
                wakeUpMinute = minute
            )
        }
    }

    private fun checkUsernameForFlaws() {

        val result = UsernameValidator.validateUsername(screenState.value.usernameText)

        _screenState.update { currentState ->
            currentState.copy(usernameValidationResult = result)
        }

        if (result is UsernameValidationResult.ValidUsername) {
            _screenState.update { currentState ->
                currentState.copy(currentSegment = UserScreenSegment.UsualBedTime)
            }
        }

    }

    private fun checkSleepDataForFlaws() {

        val bedtimeHour = _screenState.value.bedtimeHour
        val bedtimeMinute = _screenState.value.bedtimeMinute
        val wakeUpHour = _screenState.value.wakeUpHour
        val wakeUpMinute = _screenState.value.wakeUpMinute

        val result = SleepValidator.validateSleepDuration(
            bedtimeHour = bedtimeHour,
            bedtimeMinute = bedtimeMinute,
            wakeUpHour = wakeUpHour,
            wakeUpMinute = wakeUpMinute
        )

        _screenState.update { currentState ->
            currentState.copy(sleepValidationResult = result)
        }

        if (result is SleepValidationResult.ValidSleepDuration) {
            saveDataToPreferences(
                username = _screenState.value.usernameText,
                bedtimeHour = bedtimeHour,
                bedtimeMinute = bedtimeMinute,
                wakeUpHour = wakeUpHour,
                wakeUpMinute = wakeUpMinute
            )
        }

    }

    private fun saveDataToPreferences(
        username: String,
        bedtimeHour: Int,
        bedtimeMinute: Int,
        wakeUpHour: Int,
        wakeUpMinute: Int
    ) {

        val bedtime = Pair(bedtimeHour, bedtimeMinute)
        val wakeUpTime = Pair(wakeUpHour, wakeUpMinute)

        viewModelScope.launch {

            withContext(Dispatchers.IO) { preferences.setUser(username) }
            withContext(Dispatchers.IO) { preferences.setUsualBedtime(bedtime) }
            withContext(Dispatchers.IO) { preferences.setUsualWakeUpTime(wakeUpTime) }

            _screenState.update { currentState ->
                currentState.copy(
                    username = username,
                    currentSegment = UserScreenSegment.Greet
                )
            }.also { sendUIAction(UserScreenUIAction.PopBackStack) }

        }

    }

    private fun sendUIAction(uiAction: UserScreenUIAction) {
        viewModelScope.launch { _uiActionChannel.send(uiAction) }
    }

}