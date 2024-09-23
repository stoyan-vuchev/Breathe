package io.proxima.breathe.presentation.main.breathe.segment

import androidx.annotation.StringRes
import androidx.compose.runtime.Immutable
import io.proxima.breathe.R

@Immutable
sealed class BreatheScreenSegmentType(
    @StringRes val title: Int
) {

    data object Main : BreatheScreenSegmentType(
        title = R.string.breathe_segment_main_title
    )

    data object DeepBreathing : BreatheScreenSegmentType(
        title = R.string.breathe_segment_deep_breathing_mode_title
    )

    data object AlertMode : BreatheScreenSegmentType(
        title = R.string.breathe_segment_alert_mode_title
    )

    data object CalmMode : BreatheScreenSegmentType(
        title = R.string.breathe_segment_calm_mode_title
    )

}