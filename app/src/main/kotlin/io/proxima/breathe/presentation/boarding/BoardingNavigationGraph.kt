package io.proxima.breathe.presentation.boarding

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import io.proxima.breathe.core.ui.navigateSingleTop
import io.proxima.breathe.presentation.boarding.user.UserScreen
import io.proxima.breathe.presentation.boarding.user.UserScreenSegment
import io.proxima.breathe.presentation.boarding.user.UserScreenUIAction
import io.proxima.breathe.presentation.boarding.user.UserScreenViewModel
import io.proxima.breathe.presentation.boarding.welcome.WelcomeScreen
import io.proxima.breathe.presentation.boarding.welcome.WelcomeScreenUIAction
import io.proxima.breathe.presentation.boarding.welcome.WelcomeScreenViewModel
import io.proxima.breathe.presentation.main.MainNavigationDestinations
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest

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

                    val viewModel = hiltViewModel<WelcomeScreenViewModel>()
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

                    val viewModel = hiltViewModel<UserScreenViewModel>()
                    val screenState by viewModel.screenState.collectAsStateWithLifecycle()
                    val focusRequester = remember { FocusRequester() }
                    val keyboardController = LocalSoftwareKeyboardController.current

                    BackHandler(
                        enabled = screenState.currentSegment !is UserScreenSegment.Username
                                && screenState.username == null,
                        onBack = {

                            viewModel.onUIAction(
                                when (screenState.currentSegment) {
                                    is UserScreenSegment.UsualWakeUpTime -> UserScreenUIAction.GoToBedtimeSegment
                                    else -> UserScreenUIAction.GoToUsernameSegment
                                }
                            )

                        }
                    )

                    LaunchedEffect(screenState.currentSegment) {
                        if (screenState.currentSegment is UserScreenSegment.Username) {
                            delay(360L)
                            focusRequester.requestFocus()
                            keyboardController?.show()
                        }
                    }

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
                                        route = MainNavigationDestinations.Home.route,
                                        popUpTo = BoardingNavigationDestination.User.route
                                    )
                                }

                            }
                        }
                    }

                    UserScreen(
                        screenState = screenState,
                        focusRequester = focusRequester,
                        onUIAction = viewModel::onUIAction
                    )

                }
            )

        }
    )
}