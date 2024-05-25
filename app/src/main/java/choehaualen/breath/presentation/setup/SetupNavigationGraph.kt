package choehaualen.breath.presentation.setup

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import choehaualen.breath.presentation.setup.welcome.SetupScreen
import choehaualen.breath.presentation.setup.welcome.SetupScreenViewModel
import choehaualen.breath.presentation.setup.welcome.WelcomeScreen

fun NavGraphBuilder.setupNavigationGraph(
    navHostController: NavHostController
) = navigation(
    startDestination = SetupNavigationDestinations.WelcomeScreen.route,
    route = SetupNavigationDestinations.NAV_ROUTE,
    enterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Start) },
    exitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Start) },
    popEnterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.End) },
    popExitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.End) }
) {

    composable(
        route = SetupNavigationDestinations.WelcomeScreen.route,
        content = {

            WelcomeScreen(
                onNavigateToSetup = {
                    navHostController.navigate(
                        route = SetupNavigationDestinations.SetupScreen.route
                    ) {
                        launchSingleTop = true
                    }
                }
            )

        }
    )

    composable(
        route = SetupNavigationDestinations.SetupScreen.route,
        content = {

            val viewModel = viewModel<SetupScreenViewModel>()
            val screenState by viewModel.screenState.collectAsStateWithLifecycle()

            SetupScreen(
                currentSegment = screenState.currentSegment,
                onChangeSegment = viewModel::onSegmentChange
            )

        }
    )

    // But we lose the cohesive animation, its file cus this still looks wel animated & smooth
    // lemme see sth

}