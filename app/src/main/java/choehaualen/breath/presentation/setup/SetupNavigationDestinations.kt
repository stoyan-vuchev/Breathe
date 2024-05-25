package choehaualen.breath.presentation.setup

sealed class SetupNavigationDestinations(
    val route: String
) {

    data object WelcomeScreen : SetupNavigationDestinations(
        route = "welcome_screen"
    )

    data object SetupScreen : SetupNavigationDestinations(
        route = "setup_screen"
    )

    companion object {
        const val NAV_ROUTE = "setup_nav_route"
    }

}