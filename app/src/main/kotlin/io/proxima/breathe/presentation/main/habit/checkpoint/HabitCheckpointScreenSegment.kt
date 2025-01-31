package io.proxima.breathe.presentation.main.habit.checkpoint

sealed interface HabitCheckpointScreenSegment {
    data object Checkpoint : HabitCheckpointScreenSegment
    data object Pass : HabitCheckpointScreenSegment
    data object Fail : HabitCheckpointScreenSegment
}