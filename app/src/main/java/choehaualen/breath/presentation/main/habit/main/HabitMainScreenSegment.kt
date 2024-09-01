package choehaualen.breath.presentation.main.habit.main

/**
 * An interface for the main part of the Habit Control feature.
 */
sealed interface HabitMainScreenSegment {

    /**
     * An object resembling the habit name part of the user flow.
     */
    data object HabitName : HabitMainScreenSegment

    /**
     * An object resembling the quote part of the user flow.
     */
    data object HabitQuote : HabitMainScreenSegment

    /**
     * An object resembling the habit duration part of the user flow.
     */
    data object HabitDuration : HabitMainScreenSegment

    /**
     * An object resembling the habit confirmation part of the user flow.
     */
    data object HabitConfirmation : HabitMainScreenSegment

    /**
     * An object resembling the main habit dashboard part of the user flow.
     */
    data object HabitMain : HabitMainScreenSegment

}