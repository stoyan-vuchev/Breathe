package choehaualen.breath.core.ui

import androidx.navigation.NavHostController

fun NavHostController.navigateSingleTop(
    route: String,
    popUpTo: String
) = navigate(route) {
    launchSingleTop = true
    popUpTo(popUpTo) { inclusive = true }
}