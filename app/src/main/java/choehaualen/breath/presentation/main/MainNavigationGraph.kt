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

                // So, what's next?, you sure u have time and have a mood to continue?
                // So - so, 50/50 how abt u take some break? Yes, I think my b*t will merge
                // with the chair :DDDDDDol yes take break see ya :)
                // Oke, dm me on Discord , see ya!~   , or u send me msg when u are free
                // Oke, I'll do some exercises outside, and cut the grass. So half an hour will be
                // quite refreshing. Great! Oke, see ya soon!, :)

            }
        )

    }

}