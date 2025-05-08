package io.duckcat.d.presentation.main.breathe

import androidx.compose.runtime.Stable
import io.duckcat.d.presentation.main.breathe.segment.BreatheScreenSegmentState
import io.duckcat.d.presentation.main.breathe.segment.BreatheScreenSegmentType

@Stable
data class BreatheScreenState(
    val segmentType: BreatheScreenSegmentType = BreatheScreenSegmentType.Main,
    val segmentState: BreatheScreenSegmentState = BreatheScreenSegmentState()
)