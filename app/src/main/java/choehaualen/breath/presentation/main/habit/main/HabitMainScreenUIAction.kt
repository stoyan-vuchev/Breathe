package choehaualen.breath.presentation.main.habit.main

sealed interface HabitMainScreenUIAction {

    data class SetSegment(
        val segment: HabitMainScreenSegment
    ) : HabitMainScreenUIAction

    data object NavigateUp : HabitMainScreenUIAction

    data class SetHabitName(
        val newName: String
    ) : HabitMainScreenUIAction

}