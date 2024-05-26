package choehaualen.breath.presentation.boarding

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import choehaualen.breath.core.ui.navigateSingleTop
import choehaualen.breath.presentation.boarding.user.UserScreen
import choehaualen.breath.presentation.boarding.user.UserScreenUIAction
import choehaualen.breath.presentation.boarding.user.UserScreenViewModel
import choehaualen.breath.presentation.boarding.welcome.WelcomeScreen
import choehaualen.breath.presentation.boarding.welcome.WelcomeScreenUIAction
import choehaualen.breath.presentation.boarding.welcome.WelcomeScreenViewModel
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.compose.koinViewModel

fun NavGraphBuilder.boardingNavigationGraph(
    navController: NavHostController
) {

    navigation(
        startDestination = BoardingNavigationDestination.Welcome.route,
        route = BoardingNavigationDestination.NAV_ROUTE,
        builder = {

            composable(
                route = BoardingNavigationDestination.Welcome.route,
                content = {

                    val viewModel = viewModel<WelcomeScreenViewModel>()
                    val screenState by viewModel.screenState.collectAsStateWithLifecycle()

                    LaunchedEffect(viewModel.uiActionFlow) {
                        viewModel.uiActionFlow.collectLatest { uiAction ->
                            when (uiAction) {

                                is WelcomeScreenUIAction.NavigateToUser -> {
                                    navController.navigate(
                                        route = BoardingNavigationDestination.User.route,
                                        builder = { launchSingleTop = true }
                                    )
                                }

                                else -> Unit

                            }
                        }
                    }

                    WelcomeScreen(
                        screenState = screenState,
                        onUIAction = viewModel::onUIAction
                    )

                }
            )

            composable(
                route = BoardingNavigationDestination.User.route,
                content = {

                    val viewModel = koinViewModel<UserScreenViewModel>()
                    val screenState by viewModel.screenState.collectAsStateWithLifecycle()

                    LaunchedEffect(viewModel.uiActionFlow) {
                        viewModel.uiActionFlow.collectLatest { uiAction ->
                            when (uiAction) {

                                is UserScreenUIAction.PopBackStack -> {
                                    navController.navigateSingleTop(
                                        route = BoardingNavigationDestination.User.route,
                                        popUpTo = BoardingNavigationDestination.Welcome.route
                                    )
                                }

                                is UserScreenUIAction.GetStarted -> {
                                    navController.navigateSingleTop(
                                        route = "main_screen_route",
                                        popUpTo = BoardingNavigationDestination.User.route
                                    )
                                }

                            }
                        }
                    }

                    UserScreen(
                        screenState = screenState,
                        onUIAction = viewModel::onUIAction
                    )

                }
            )

        }
    )
}