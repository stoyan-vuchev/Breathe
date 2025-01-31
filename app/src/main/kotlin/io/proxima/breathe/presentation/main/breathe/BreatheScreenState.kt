package io.proxima.breathe.presentation.main.breathe

import androidx.compose.runtime.Stable
import io.proxima.breathe.presentation.main.breathe.segment.BreatheScreenSegmentState
import io.proxima.breathe.presentation.main.breathe.segment.BreatheScreenSegmentType

@Stable
data class BreatheScreenState(
    val segmentType: BreatheScreenSegmentType = BreatheScreenSegmentType.Main,
    val segmentState: BreatheScreenSegmentState = BreatheScreenSegmentState()
)