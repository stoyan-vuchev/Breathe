package io.proxima.breathe.presentation.main.habit.checkpoint

sealed interface HabitCheckpointUIAction {
    data object Pass : HabitCheckpointUIAction
    data object Fail : HabitCheckpointUIAction
    data object Continue : HabitCheckpointUIAction
    data object Stay : HabitCheckpointUIAction
    data object NavigateUp : HabitCheckpointUIAction
}