package io.proxima.breathe.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import io.proxima.breathe.core.ui.ScreenTransitions
import io.proxima.breathe.core.ui.navigateSingleTop
import io.proxima.breathe.core.ui.theme.BreathTheme
import io.proxima.breathe.core.ui.theme.backgroundBrush
import io.proxima.breathe.presentation.boarding.BoardingNavigationDestination
import io.proxima.breathe.presentation.boarding.SplashElement
import io.proxima.breathe.presentation.boarding.boardingNavigationGraph
import io.proxima.breathe.presentation.main.MainNavigationDestinations
import io.proxima.breathe.presentation.main.mainNavigationGraph
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.seconds

@Composable
fun AppNavigationHost(
    navController: NavHostController,
    isUserAuthenticated: Boolean?,
    onAuthenticateUser: () -> Unit,
    onRecreateActivity: () -> Unit
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
                        delay(3.seconds)
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
                        .background(Color.Black)
                        .systemBarsPadding(),
                    contentAlignment = Alignment.Center
                ) {

                    SplashElement(
                        modifier = Modifier.size(300.dp)
                    )

                }

            }
        )

        // Boarding navigation graph
        boardingNavigationGraph(navController = navController)

        // Main navigation graph
        mainNavigationGraph(
            navController = navController,
            onRecreateActivity = onRecreateActivity
        )

    }

}