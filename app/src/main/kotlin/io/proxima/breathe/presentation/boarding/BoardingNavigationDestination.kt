package io.proxima.breathe.presentation.boarding

import androidx.compose.runtime.Immutable

@Immutable
sealed class BoardingNavigationDestination(
    val route: String
) {

    data object Welcome : BoardingNavigationDestination("boarding_welcome")
    data object User : BoardingNavigationDestination("boarding_user")

    companion object {
        const val NAV_ROUTE = "boarding_navigation"
    }

}