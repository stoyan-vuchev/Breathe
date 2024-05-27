package choehaualen.breath.presentation.main

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import choehaualen.breath.presentation.main.home.HomeScreen

fun NavGraphBuilder.mainNavigationGraph(navController: NavHostController) {

    navigation(
        startDestination = MainNavigationDestinations.Home.route,
        route = MainNavigationDestinations.NAV_ROUTE
    ) {

        composable(
            route = MainNavigationDestinations.Home.route,
            content = {

                HomeScreen()

            }
        )

    }

}