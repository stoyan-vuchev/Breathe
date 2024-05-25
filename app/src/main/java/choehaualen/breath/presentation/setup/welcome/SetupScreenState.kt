package choehaualen.breath.presentation.setup.welcome

import androidx.compose.runtime.Stable

@Stable
data class SetupScreenState(
    val currentSegment: SetupScreenSegment = SetupScreenSegment.TrackAndImproveSleep
)