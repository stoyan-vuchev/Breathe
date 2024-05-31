package choehaualen.breath.presentation.main

sealed class MainNavigationDestinations(
    val route: String
) {

    data object Home : MainNavigationDestinations("main_home_screen")
    data object Sleep : MainNavigationDestinations("main_sleep_screen")

    companion object {
        const val NAV_ROUTE = "main_navigation"
    }

}