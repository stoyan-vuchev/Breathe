package choehaualen.breath.presentation.main.breathe

import androidx.compose.runtime.Stable
import choehaualen.breath.presentation.main.breathe.segment.BreatheScreenSegmentState
import choehaualen.breath.presentation.main.breathe.segment.BreatheScreenSegmentType

@Stable
data class BreatheScreenState(
    val segmentType: BreatheScreenSegmentType = BreatheScreenSegmentType.Main,
    val segmentState: BreatheScreenSegmentState = BreatheScreenSegmentState()
)