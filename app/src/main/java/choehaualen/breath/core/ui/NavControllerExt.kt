package choehaualen.breath.core.ui

import androidx.navigation.NavHostController

fun NavHostController.navigateSingleTop(
    route: String,
    popUpTo: String,
    inclusive: Boolean = true
) = navigate(route) {
    launchSingleTop = true
    popUpTo(popUpTo) { this.inclusive = inclusive }
}