package choehaualen.breath.presentation.main

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import choehaualen.breath.core.etc.UiString.Companion.asString
import choehaualen.breath.core.ui.colors.DreamyNightColors
import choehaualen.breath.core.ui.colors.MelonColors
import choehaualen.breath.core.ui.colors.ProvideBreathColors
import choehaualen.breath.core.ui.colors.SkyBlueColors
import choehaualen.breath.core.ui.colors.SleepColors
import choehaualen.breath.core.ui.colors.ZoneColors
import choehaualen.breath.core.ui.navigateSingleTop
import choehaualen.breath.presentation.main.breathe.BreatheScreen
import choehaualen.breath.presentation.main.breathe.BreatheScreenUIAction
import choehaualen.breath.presentation.main.breathe.BreatheScreenViewModel
import choehaualen.breath.presentation.main.habit.main.HabitMainScreen
import choehaualen.breath.presentation.main.habit.main.HabitMainScreenUIAction
import choehaualen.breath.presentation.main.habit.main.HabitMainScreenViewModel
import choehaualen.breath.presentation.main.habit.setup.HabitSetupScreen
import choehaualen.breath.presentation.main.habit.setup.HabitSetupScreenUIAction
import choehaualen.breath.presentation.main.habit.setup.HabitSetupScreenViewModel
import choehaualen.breath.presentation.main.home.HomeScreen
import choehaualen.breath.presentation.main.home.HomeScreenUIAction
import choehaualen.breath.presentation.main.home.HomeScreenViewModel
import choehaualen.breath.presentation.main.productivity.ProductivityScreenUIAction
import choehaualen.breath.presentation.main.productivity.ProductivityScreenViewModel
import choehaualen.breath.presentation.main.productivity.components.ProductivityScreen
import choehaualen.breath.presentation.main.settings.SettingsScreen
import choehaualen.breath.presentation.main.settings.SettingsScreenUIAction.DismissDeleteDataDialog
import choehaualen.breath.presentation.main.settings.SettingsScreenUIAction.NavigateUp
import choehaualen.breath.presentation.main.settings.SettingsScreenUIAction.ShowDeleteDataDialog
import choehaualen.breath.presentation.main.settings.SettingsScreenViewModel
import choehaualen.breath.presentation.main.sleep.SleepScreen
import choehaualen.breath.presentation.main.sleep.SleepScreenUIAction
import choehaualen.breath.presentation.main.sleep.SleepScreenViewModel
import choehaualen.breath.presentation.main.soundscape.SoundScapeScreen
import choehaualen.breath.presentation.main.soundscape.SoundscapeUIAction
import choehaualen.breath.presentation.main.soundscape.SoundscapeViewModel
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
                val snackBarHostState = remember { SnackbarHostState() }
                val context = LocalContext.current

                LaunchedEffect(viewModel.uiActionFlow) {
                    viewModel.uiActionFlow.collectLatest { uiAction ->
                        when (uiAction) {

                            is HomeScreenUIAction.NavigateToSleep -> navController.navigate(
                                route = uiAction.route
                            ) { launchSingleTop = true }

                            is HomeScreenUIAction.NavigateToBreathe -> navController.navigate(
                                route = uiAction.route
                            ) { launchSingleTop = true }

                            is HomeScreenUIAction.NavigateToSoundscape -> navController.navigate(
                                route = uiAction.route
                            ) { launchSingleTop = true }

                            is HomeScreenUIAction.NavigateToHabitControl -> navController.navigate(
                                route = uiAction.route
                            ) { launchSingleTop = true }

                            is HomeScreenUIAction.NavigateToProductivity -> navController.navigate(
                                route = uiAction.route
                            ) { launchSingleTop = true }

                            is HomeScreenUIAction.NavigateToSettings -> navController.navigate(
                                route = uiAction.route
                            ) { launchSingleTop = true }

                            else -> Unit

                        }
                    }
                }

                LaunchedEffect(viewModel.snackBarFlow) {
                    viewModel.snackBarFlow.collect { msg ->
                        snackBarHostState.showSnackbar(msg.asString(context))
                    }
                }

                HomeScreen(
                    snackBarHostState = snackBarHostState,
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

        composable(
            route = MainNavigationDestinations.Breathe.route,
            content = {

                ProvideBreathColors(MelonColors) {

                    val viewModel = hiltViewModel<BreatheScreenViewModel>()
                    val screenState by viewModel.screenState.collectAsStateWithLifecycle()

                    LaunchedEffect(viewModel.uiActionFlow) {
                        viewModel.uiActionFlow.collectLatest { uiAction ->
                            when (uiAction) {
                                is BreatheScreenUIAction.NavigateUp -> navController.navigateUp()
                                else -> Unit
                            }
                        }
                    }

                    BreatheScreen(
                        screenState = screenState,
                        onUIAction = viewModel::onUIAction
                    )

                }

            }
        )

        composable(
            route = MainNavigationDestinations.Soundscape.route,
            content = {
                ProvideBreathColors(DreamyNightColors) {

                    val viewModel = hiltViewModel<SoundscapeViewModel>()
                    val screenState by viewModel.screenState.collectAsStateWithLifecycle()

                    LaunchedEffect(viewModel.uiActionFlow) {
                        viewModel.uiActionFlow.collectLatest { uiAction ->
                            when (uiAction) {
                                is SoundscapeUIAction.NavigateUp -> navController.navigateUp()
                            }
                        }
                    }

                    SoundScapeScreen(
                        screenState = screenState,
                        onUIAction = viewModel::onUIAction
                    )

                }
            }
        )

        composable(
            route = MainNavigationDestinations.HabitControlSetup.route,
            content = {
                ProvideBreathColors(ZoneColors) {

                    val viewModel = hiltViewModel<HabitSetupScreenViewModel>()
                    val screenState by viewModel.screenState.collectAsStateWithLifecycle()

                    LaunchedEffect(viewModel.uiActionFlow) {
                        viewModel.uiActionFlow.collectLatest { uiAction ->
                            when (uiAction) {

                                is HabitSetupScreenUIAction.NavigateToMain -> {
                                    navController.navigateSingleTop(
                                        route = MainNavigationDestinations.HabitControlMain.route,
                                        popUpTo = MainNavigationDestinations.HabitControlSetup.route,
                                        inclusive = true
                                    )
                                }

                                is HabitSetupScreenUIAction.NavigateUp -> navController.navigateUp()
                                else -> Unit

                            }
                        }
                    }

                    HabitSetupScreen(
                        screenState = screenState,
                        onUIAction = viewModel::onUIAction
                    )

                }
            }
        )

        composable(
            route = MainNavigationDestinations.HabitControlMain.route,
            content = {
                ProvideBreathColors(ZoneColors) {

                    val viewModel = hiltViewModel<HabitMainScreenViewModel>()
                    val screenState by viewModel.screenState.collectAsStateWithLifecycle()

                    LaunchedEffect(viewModel.uiActionFlow) {
                        viewModel.uiActionFlow.collectLatest { uiAction ->
                            when (uiAction) {
                                is HabitMainScreenUIAction.NavigateUp -> navController.navigateUp()
                            }
                        }
                    }

                    HabitMainScreen(
                        screenState = screenState,
                        onUIAction = viewModel::onUIAction
                    )

                }
            }
        )

        composable(
            route = MainNavigationDestinations.Productivity.route,
            content = {
                ProvideBreathColors(SkyBlueColors) {

                    val viewModel = hiltViewModel<ProductivityScreenViewModel>()
                    val screenState by viewModel.state.collectAsStateWithLifecycle()

                    LaunchedEffect(viewModel.uiActionFlow) {
                        viewModel.uiActionFlow.collectLatest { uiAction ->
                            when (uiAction) {

                                is ProductivityScreenUIAction.NavigateUp -> {
                                    navController.navigateUp()
                                }

                                else -> Unit

                            }
                        }
                    }

                    ProductivityScreen(
                        screenState = screenState,
                        onUIAction = viewModel::onUIAction
                    )

                }
            }
        )

        composable(
            route = MainNavigationDestinations.Settings.route,
            content = {

                val viewModel = hiltViewModel<SettingsScreenViewModel>()
                val snackbarHostState = remember { SnackbarHostState() }
                val context = LocalContext.current

                var isDeleteDataDialogVisible by rememberSaveable { mutableStateOf(false) }

                LaunchedEffect(viewModel.uiActionFlow) {
                    viewModel.uiActionFlow.collectLatest { uiAction ->
                        when (uiAction) {
                            is NavigateUp -> navController.navigateUp()
                            is ShowDeleteDataDialog -> isDeleteDataDialogVisible = true
                            is DismissDeleteDataDialog -> isDeleteDataDialogVisible = false
                            else -> Unit
                        }
                    }
                }

                LaunchedEffect(viewModel.snackBarFlow) {
                    viewModel.snackBarFlow.collect { msg ->
                        snackbarHostState.showSnackbar(msg.asString(context))
                    }
                }

                SettingsScreen(
                    snackbarHostState = snackbarHostState,
                    isDeleteDataDialogVisible = isDeleteDataDialogVisible,
                    onUIAction = viewModel::onUIAction
                )

            }
        )

    }

}