package choehaualen.breath.presentation.main.habit.setup

sealed interface HabitSetupScreenUIAction {

    data class SetSegment(
        val segment: HabitSetupScreenSegment
    ) : HabitSetupScreenUIAction

    data object NavigateUp : HabitSetupScreenUIAction

    data class SetFieldValue(
        val currentSegment: HabitSetupScreenSegment,
        val newValue: String
    ) : HabitSetupScreenUIAction

    data object Next : HabitSetupScreenUIAction
    data object NavigateToMain : HabitSetupScreenUIAction

}