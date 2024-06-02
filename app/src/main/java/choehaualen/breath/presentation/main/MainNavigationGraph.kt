package choehaualen.breath.presentation.main

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import choehaualen.breath.core.ui.colors.ProvideBreathColors
import choehaualen.breath.core.ui.colors.SleepColors
import choehaualen.breath.presentation.main.home.HomeScreen
import choehaualen.breath.presentation.main.home.HomeScreenUIAction
import choehaualen.breath.presentation.main.home.HomeScreenViewModel
import choehaualen.breath.presentation.main.sleep.SleepScreen
import choehaualen.breath.presentation.main.sleep.SleepScreenUIAction
import choehaualen.breath.presentation.main.sleep.SleepScreenViewModel
import kotlinx.coroutines.flow.collectLatest

fun NavGraphBuilder.mainNavigationGraph(navController: NavHostController) {

    navigation(
        startDestination = MainNavigationDestinations.Home.route,
        route = MainNavigationDestinations.NAV_ROUTE
    ) {

        composable(
            route = MainNavigationDestinations.Home.route,
            content = {

                val viewModel = hiltViewModel<HomeScreenViewModel>()

                LaunchedEffect(viewModel.uiActionFlow) {
                    viewModel.uiActionFlow.collectLatest { uiAction ->
                        when (uiAction) {
                            is HomeScreenUIAction.NavigateToSleep -> navController.navigate(
                                route = uiAction.route
                            ) { launchSingleTop = true }
                        }
                    }
                }

                HomeScreen(
                    onUIAction = viewModel::onUIAction
                )

            }
        )

        composable(
            route = MainNavigationDestinations.Sleep.route,
            content = {

                ProvideBreathColors(SleepColors) {

                    val viewModel = hiltViewModel<SleepScreenViewModel>()
                    val screenState by viewModel.screenState.collectAsStateWithLifecycle()

                    LaunchedEffect(viewModel.uiActionFlow) {
                        viewModel.uiActionFlow.collectLatest { uiAction ->
                            when (uiAction) {
                                is SleepScreenUIAction.NavigateUp -> navController.navigateUp()
                                else -> Unit
                            }
                        }
                    }

                    SleepScreen(
                        screenState = screenState,
                        onUIAction = viewModel::onUIAction
                    )

                }

            }
        )

    }

}