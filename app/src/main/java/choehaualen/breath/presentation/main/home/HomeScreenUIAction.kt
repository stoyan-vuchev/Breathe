package choehaualen.breath.presentation.main.home

import androidx.compose.runtime.Immutable
import choehaualen.breath.presentation.main.MainNavigationDestinations

@Immutable
sealed interface HomeScreenUIAction {

    data class NavigateToSleep(
        val route: String = MainNavigationDestinations.Sleep.route
    ) : HomeScreenUIAction

}