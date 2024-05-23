package choehaualen.breath.presentation.setup

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class SetupScreenViewModel : ViewModel() {

    private val _screenState = MutableStateFlow(SetupScreenState())
    val screenState = _screenState.asStateFlow()

    fun onSegmentChange(
        segment: SetupScreenSegment,
        isDirectionNext: Boolean
    ) {

        _screenState.update { currentState ->
            currentState.copy(
                currentSegment = if (isDirectionNext) {

                    when (segment) {

                        is SetupScreenSegment.TrackAndImproveSleep -> SetupScreenSegment.BreathExercises
                        is SetupScreenSegment.BreathExercises -> SetupScreenSegment.Soundscape
                        is SetupScreenSegment.Soundscape -> SetupScreenSegment.Privacy
                        else -> SetupScreenSegment.TrackAndImproveSleep

                    }

                } else {

                    when (segment) {

                        is SetupScreenSegment.BreathExercises -> SetupScreenSegment.TrackAndImproveSleep
                        is SetupScreenSegment.Soundscape -> SetupScreenSegment.BreathExercises
                        is SetupScreenSegment.Privacy -> SetupScreenSegment.Soundscape
                        else -> SetupScreenSegment.TrackAndImproveSleep

                    }

                }
            )
        }

    }

}