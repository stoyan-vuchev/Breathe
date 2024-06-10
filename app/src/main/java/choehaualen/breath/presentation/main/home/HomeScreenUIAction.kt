package choehaualen.breath.presentation.main.home

import androidx.compose.runtime.Immutable
import choehaualen.breath.presentation.main.MainNavigationDestinations

@Immutable
sealed interface HomeScreenUIAction {

    data class NavigateToSleep(
        val route: String = MainNavigationDestinations.Sleep.route
    ) : HomeScreenUIAction

    data class NavigateToBreathe(
        val route: String = MainNavigationDestinations.Breathe.route
    ) : HomeScreenUIAction

    data class NavigateToSoundscape(
        val route: String = MainNavigationDestinations.Soundscape.route
    ) : HomeScreenUIAction

    data class NavigateToProductivity(
        val route: String = MainNavigationDestinations.Productivity.route
    ) : HomeScreenUIAction

    data object ShowMore : HomeScreenUIAction
    data object ExpandQuote : HomeScreenUIAction

    data class NavigateToSettings(
        val route: String = MainNavigationDestinations.Settings.route
    ) : HomeScreenUIAction

}