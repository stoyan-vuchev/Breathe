package choehaualen.breath.presentation.main.breathe.segment

import androidx.compose.runtime.Stable

@Stable
data class BreatheScreenSegmentState(
    val isRunning: Boolean = false,
    val countDown: Int = 5,
    val modeDurationInSeconds: Int = 0,
    val phase: BreathePhase = BreathePhase.Inhale(0f),
)