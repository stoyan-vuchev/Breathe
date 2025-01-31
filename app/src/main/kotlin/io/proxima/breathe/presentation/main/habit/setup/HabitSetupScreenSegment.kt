package io.proxima.breathe.presentation.main.habit.setup

/**
 * An interface for the main part of the Habit Control feature.
 */
sealed interface HabitSetupScreenSegment {

    /**
     * An object resembling the habit name part of the user flow.
     */
    data object HabitName : HabitSetupScreenSegment

    /**
     * An object resembling the quote part of the user flow.
     */
    data object HabitQuote : HabitSetupScreenSegment

    /**
     * An object resembling the habit duration part of the user flow.
     */
    data object HabitDuration : HabitSetupScreenSegment

    /**
     * An object resembling the habit confirmation part of the user flow.
     */
    data object HabitConfirmation : HabitSetupScreenSegment

}