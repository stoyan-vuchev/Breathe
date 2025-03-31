package io.proxima.breathe.presentation.main.explore

import androidx.compose.runtime.Immutable
import io.proxima.breathe.presentation.main.MainNavigationDestinations

@Immutable
sealed interface ExploreScreenUIAction {
    data object NavigateToHome : ExploreScreenUIAction
    data object NavigateToProfile : ExploreScreenUIAction
    data object NavigateToSettings : ExploreScreenUIAction


    data class NavigateToSleep(
        val route: String = MainNavigationDestinations.Sleep.route
    ) : ExploreScreenUIAction

    data class NavigateToBreathe(
        val route: String = MainNavigationDestinations.Breathe.route
    ) : ExploreScreenUIAction

//    data class NavigateToSoundscape(
//        val route: String = MainNavigationDestinations.Soundscape.route
//    ) : ExploreScreenUIAction

    data class NavigateToHabitControl(
        val route: String = MainNavigationDestinations.HabitControlSetup.route
    ) : ExploreScreenUIAction

    data class NavigateToProductivity(
        val route: String = MainNavigationDestinations.Productivity.route
    ) : ExploreScreenUIAction



    data class NavigateToPomodoro(
        val route: String = MainNavigationDestinations.Pomodoro.route
    ) : ExploreScreenUIAction

    data class NavigateToFitness(
        val route: String = MainNavigationDestinations.FitnessSetup.route
    ) : ExploreScreenUIAction

    data class NavigateToSoundscapeFilter(
        val route: String = MainNavigationDestinations.SoundscapeFilter.route
    ) : ExploreScreenUIAction

    data class NavigateToStudy(
        val route: String = MainNavigationDestinations.StudyMain.route
    ) : ExploreScreenUIAction
}

