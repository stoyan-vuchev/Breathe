package choehaualen.breath.presentation.setup

import androidx.compose.runtime.Stable

@Stable
data class SetupScreenState(
    val currentSegment: SetupScreenSegment = SetupScreenSegment.TrackAndImproveSleep
)