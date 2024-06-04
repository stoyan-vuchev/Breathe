package choehaualen.breath.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import choehaualen.breath.R
import choehaualen.breath.core.ui.ScreenTransitions
import choehaualen.breath.core.ui.colors.backgroundBrush
import choehaualen.breath.core.ui.navigateSingleTop
import choehaualen.breath.core.ui.theme.BreathTheme
import choehaualen.breath.presentation.boarding.BoardingNavigationDestination
import choehaualen.breath.presentation.boarding.boardingNavigationGraph
import choehaualen.breath.presentation.main.MainNavigationDestinations
import choehaualen.breath.presentation.main.mainNavigationGraph

@Composable
fun AppNavigationHost(
    navController: NavHostController,
    isUserAuthenticated: Boolean?,
    onAuthenticateUser: () -> Unit
) {

    NavHost(
        modifier = Modifier
            .fillMaxSize()
            .background(BreathTheme.colors.backgroundBrush()),
        navController = navController,
        startDestination = "start_route",
        enterTransition = { ScreenTransitions.enterTransition },
        exitTransition = { ScreenTransitions.exitTransition },
        popEnterTransition = { ScreenTransitions.popEnterTransition },
        popExitTransition = { ScreenTransitions.popExitTransition }
    ) {

        composable(
            route = "start_route",
            content = {

                LaunchedEffect(isUserAuthenticated) {
                    isUserAuthenticated?.let { isAuthenticated ->
                        navController.navigateSingleTop(
                            route = if (isAuthenticated) MainNavigationDestinations.Home.route
                            else BoardingNavigationDestination.Welcome.route,
                            popUpTo = "start_route"
                        )
                    } ?: onAuthenticateUser()
                }

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(BreathTheme.colors.background)
                        .systemBarsPadding(),
                    contentAlignment = Alignment.Center
                ) {

                    Image(
                        painter = painterResource(id = R.drawable.splash_logo),
                        contentDescription = "Breath Logo"
                    )

                }

            }
        )

        // Boarding navigation graph
        boardingNavigationGraph(navController = navController)

        // Main navigation graph
        mainNavigationGraph(navController = navController)

    }

}