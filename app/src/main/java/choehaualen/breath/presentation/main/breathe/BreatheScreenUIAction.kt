package choehaualen.breath.presentation.main.breathe

import androidx.compose.runtime.Immutable
import choehaualen.breath.presentation.main.breathe.segment.BreatheScreenSegmentType

@Immutable
sealed interface BreatheScreenUIAction {

    data object NavigateUp : BreatheScreenUIAction
    data class SetSegment(val segment: BreatheScreenSegmentType) : BreatheScreenUIAction

    data class StartSegment(val segment: BreatheScreenSegmentType) : BreatheScreenUIAction
    data object ClearSegment : BreatheScreenUIAction

}