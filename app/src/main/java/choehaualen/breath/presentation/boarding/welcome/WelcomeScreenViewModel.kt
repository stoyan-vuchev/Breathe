package choehaualen.breath.presentation.boarding.welcome

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class WelcomeScreenViewModel : ViewModel() {

    private val _screenState = MutableStateFlow(WelcomeScreenState())
    val screenState = _screenState.asStateFlow()

    private val _uiActionChannel = Channel<WelcomeScreenUIAction>()
    val uiActionFlow = _uiActionChannel.receiveAsFlow()

    fun onUIAction(uiAction: WelcomeScreenUIAction) = when (uiAction) {
        is WelcomeScreenUIAction.Next -> onNext(uiAction.currentSegment)
        is WelcomeScreenUIAction.Back -> onBack(uiAction.currentSegment)
        is WelcomeScreenUIAction.NavigateToUser -> sendUIAction(uiAction)
    }

    private fun onNext(segment: WelcomeScreenSegment) {
        if (segment !is WelcomeScreenSegment.Privacy) {
            _screenState.update { currentState ->
                currentState.copy(
                    segment = when (segment) {
                        is WelcomeScreenSegment.Welcome -> WelcomeScreenSegment.TrackSleep
                        is WelcomeScreenSegment.TrackSleep -> WelcomeScreenSegment.BreathExercises
                        is WelcomeScreenSegment.BreathExercises -> WelcomeScreenSegment.Soundscape
                        is WelcomeScreenSegment.Soundscape -> WelcomeScreenSegment.Puzzle
                        is WelcomeScreenSegment.Puzzle -> WelcomeScreenSegment.Privacy
                        else -> segment
                    }
                )
            }
        } else onUIAction(WelcomeScreenUIAction.NavigateToUser)
    }

    private fun onBack(segment: WelcomeScreenSegment) {
        _screenState.update { currentState ->
            currentState.copy(
                segment = when (segment) {
                    is WelcomeScreenSegment.Privacy -> WelcomeScreenSegment.Puzzle
                    is WelcomeScreenSegment.Puzzle -> WelcomeScreenSegment.Soundscape
                    is WelcomeScreenSegment.Soundscape -> WelcomeScreenSegment.BreathExercises
                    is WelcomeScreenSegment.BreathExercises -> WelcomeScreenSegment.TrackSleep
                    is WelcomeScreenSegment.TrackSleep -> WelcomeScreenSegment.Welcome
                    else -> throw IllegalStateException("This isn't supposed to happen. Lol!")
                }
            )
        }
    }

    private fun sendUIAction(uiAction: WelcomeScreenUIAction.NavigateToUser) {
        viewModelScope.launch { _uiActionChannel.send(uiAction) }
    }

}