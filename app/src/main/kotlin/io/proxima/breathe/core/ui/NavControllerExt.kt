package io.proxima.breathe.core.ui

import androidx.navigation.NavHostController

fun NavHostController.navigateSingleTop(
    route: String,
    popUpTo: String = route,
    inclusive: Boolean = true
) = navigate(route) {
    launchSingleTop = true
    popUpTo(popUpTo) { this.inclusive = inclusive }
}