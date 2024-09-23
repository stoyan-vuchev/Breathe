package io.proxima.breathe.presentation.main.breathe.segment

import androidx.annotation.StringRes
import androidx.compose.runtime.Stable
import io.proxima.breathe.R

@Stable
sealed class BreathePhase(
    open val fraction: Float,
    @StringRes val label: Int
) {

    data class Inhale(override val fraction: Float = 1f) : BreathePhase(
        fraction = fraction,
        label = R.string.breathe_phase_inhale_label
    )

    data class ForceInhale(override val fraction: Float = 1f) : BreathePhase(
        fraction = fraction,
        label = R.string.breathe_phase_force_inhale_label
    )

    data class Exhale(override val fraction: Float = 1f) : BreathePhase(
        fraction = fraction,
        label = R.string.breathe_phase_exhale_label
    )

}