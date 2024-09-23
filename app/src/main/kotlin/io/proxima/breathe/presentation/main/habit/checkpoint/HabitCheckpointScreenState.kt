package io.proxima.breathe.presentation.main.habit.checkpoint

import androidx.compose.runtime.Stable

@Stable
data class HabitCheckpointScreenState(
    val currentSegment: HabitCheckpointScreenSegment = HabitCheckpointScreenSegment.Checkpoint,
    val habitQuote: String = ""
)