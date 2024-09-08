package choehaualen.breath.presentation.main.habit.setup

import androidx.compose.runtime.Stable

@Stable
data class HabitSetupScreenState(
    val currentSegment: HabitSetupScreenSegment? = null,
    val username: String = "",
    val name: String = "",
    val quote: String = "",
    val goalDuration: String = "",
    val currentProgress: Int = 0
)