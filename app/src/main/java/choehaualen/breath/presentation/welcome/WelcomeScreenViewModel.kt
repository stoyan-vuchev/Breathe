package choehaualen.breath.presentation.welcome

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class WelcomeScreenViewModel : ViewModel() {

    private val _screenState = MutableStateFlow(WelcomeScreenState())
    val screenState = _screenState.asStateFlow()

    fun onUIAction(uiAction: WelcomeScreenUIAction) = when (uiAction) {
        is WelcomeScreenUIAction.Next -> onNext(uiAction.currentSegment)
        is WelcomeScreenUIAction.Back -> onBack(uiAction.currentSegment)
    }

    private fun onNext(segment: WelcomeScreenSegment) {
        _screenState.update { currentState ->
            currentState.copy(
                segment = when (segment) {
                    is WelcomeScreenSegment.Welcome -> WelcomeScreenSegment.TrackSleep
                    is WelcomeScreenSegment.TrackSleep -> WelcomeScreenSegment.BreathExercises
                    is WelcomeScreenSegment.BreathExercises -> WelcomeScreenSegment.Soundscape
                    is WelcomeScreenSegment.Soundscape -> WelcomeScreenSegment.Privacy
                    is WelcomeScreenSegment.Privacy -> WelcomeScreenSegment.TrackSleep
                }
            )
        }
    }

    private fun onBack(segment: WelcomeScreenSegment) {
        _screenState.update { currentState ->
            currentState.copy(
                segment = when (segment) {
                    is WelcomeScreenSegment.Privacy -> WelcomeScreenSegment.Soundscape
                    is WelcomeScreenSegment.Soundscape -> WelcomeScreenSegment.BreathExercises
                    is WelcomeScreenSegment.BreathExercises -> WelcomeScreenSegment.TrackSleep
                    is WelcomeScreenSegment.TrackSleep -> WelcomeScreenSegment.Welcome
                    else -> throw IllegalStateException("This isn't supposed to happen. Lol!")
                }
            )
        }
    }

}