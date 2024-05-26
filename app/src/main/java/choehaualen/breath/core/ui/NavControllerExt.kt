package choehaualen.breath.core.ui

import androidx.navigation.NavHostController

fun NavHostController.navigateSingleTop(
    route: String
) = navigate(
    route = route,
    builder = {

        popBackStack(
            route = "start_route",
            inclusive = true
        )

        launchSingleTop = true

    }
)