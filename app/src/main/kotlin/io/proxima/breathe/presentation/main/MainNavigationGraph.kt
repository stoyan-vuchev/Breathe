package io.proxima.breathe.presentation.main

import android.content.Intent
import android.provider.Settings
import androidx.activity.compose.BackHandler
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
import io.proxima.breathe.core.etc.UiString.Companion.asString
import io.proxima.breathe.core.ui.theme.DreamyNightColors
import io.proxima.breathe.core.ui.theme.MelonColors
import io.proxima.breathe.core.ui.theme.ProvideBreathColors
import io.proxima.breathe.core.ui.theme.SkyBlueColors
import io.proxima.breathe.core.ui.theme.SleepColors
import io.proxima.breathe.core.ui.theme.ZoneColors
import io.proxima.breathe.core.ui.navigateSingleTop
import io.proxima.breathe.presentation.main.breathe.BreatheScreen
import io.proxima.breathe.presentation.main.breathe.BreatheScreenUIAction
import io.proxima.breathe.presentation.main.breathe.BreatheScreenViewModel
import io.proxima.breathe.presentation.main.habit.checkpoint.HabitCheckpointScreen
import io.proxima.breathe.presentation.main.habit.checkpoint.HabitCheckpointScreenSegment
import io.proxima.breathe.presentation.main.habit.checkpoint.HabitCheckpointScreenViewModel
import io.proxima.breathe.presentation.main.habit.checkpoint.HabitCheckpointUIAction
import io.proxima.breathe.presentation.main.habit.setup.HabitSetupScreen
import io.proxima.breathe.presentation.main.habit.setup.HabitSetupScreenUIAction
import io.proxima.breathe.presentation.main.habit.setup.HabitSetupScreenViewModel
import io.proxima.breathe.presentation.main.home.HomeScreen
import io.proxima.breathe.presentation.main.home.HomeScreenUIAction
import io.proxima.breathe.presentation.main.home.HomeScreenViewModel
import io.proxima.breathe.presentation.main.productivity.ProductivityScreenUIAction
import io.proxima.breathe.presentation.main.productivity.ProductivityScreenViewModel
import io.proxima.breathe.presentation.main.productivity.components.ProductivityScreen
import io.proxima.breathe.presentation.main.settings.SettingsScreen
import io.proxima.breathe.presentation.main.settings.SettingsScreenUIAction
import io.proxima.breathe.presentation.main.settings.SettingsScreenUIAction.DismissDeleteDataDialog
import io.proxima.breathe.presentation.main.settings.SettingsScreenUIAction.NavigateUp
import io.proxima.breathe.presentation.main.settings.SettingsScreenUIAction.ShowDeleteDataDialog
import io.proxima.breathe.presentation.main.settings.SettingsScreenViewModel
import io.proxima.breathe.presentation.main.sleep.SleepScreen
import io.proxima.breathe.presentation.main.sleep.SleepScreenUIAction
import io.proxima.breathe.presentation.main.sleep.SleepScreenViewModel
import io.proxima.breathe.presentation.main.soundscape.SoundScapeScreen
import io.proxima.breathe.presentation.main.soundscape.SoundscapeUIAction
import io.proxima.breathe.presentation.main.soundscape.SoundscapeViewModel
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
                val screenState by viewModel.screenState.collectAsStateWithLifecycle()

                val snackBarHostState = remember { SnackbarHostState() }
                val context = LocalContext.current

                LaunchedEffect(viewModel.uiActionFlow) {
                    viewModel.uiActionFlow.collectLatest { uiAction ->
                        when (uiAction) {

                            is HomeScreenUIAction.NavigateToSleep -> navController
                                .navigateSingleTop(route = uiAction.route, inclusive = false)

                            is HomeScreenUIAction.NavigateToBreathe -> navController
                                .navigateSingleTop(route = uiAction.route, inclusive = false)

                            is HomeScreenUIAction.NavigateToSoundscape -> navController
                                .navigateSingleTop(route = uiAction.route, inclusive = false)

                            is HomeScreenUIAction.NavigateToHabitControl -> navController
                                .navigateSingleTop(route = uiAction.route, inclusive = false)

                            is HomeScreenUIAction.NavigateToProductivity -> navController
                                .navigateSingleTop(route = uiAction.route, inclusive = false)

                            is HomeScreenUIAction.NavigateToSettings -> navController
                                .navigateSingleTop(route = uiAction.route, inclusive = false)

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
                    screenState = screenState,
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
                                else -> Unit
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

//                    val viewModel = hiltViewModel<HabitMainScreenViewModel>()
//                    val screenState by viewModel.screenState.collectAsStateWithLifecycle()
//
//                    LaunchedEffect(viewModel.uiActionFlow) {
//                        viewModel.uiActionFlow.collectLatest { uiAction ->
//                            when (uiAction) {
//                                is HabitMainScreenUIAction.NavigateUp -> navController.navigateUp()
//                            }
//                        }
//                    }
//
//                    HabitMainScreen(
//                        screenState = screenState,
//                        onUIAction = viewModel::onUIAction
//                    )

                    val viewModel = hiltViewModel<HabitCheckpointScreenViewModel>()
                    val screenState by viewModel.screenState.collectAsStateWithLifecycle()

                    BackHandler(
                        enabled = screenState.currentSegment
                                !is HabitCheckpointScreenSegment.Checkpoint,
                        onBack = { viewModel.onUIAction(HabitCheckpointUIAction.NavigateUp) }
                    )

                    HabitCheckpointScreen(
                        screenState = screenState,
                        onUIAction = viewModel::onUIAction
                    )

                }
            }
        )

        composable(
            route = MainNavigationDestinations.HabitControlCheckpoint.route,
            content = {
                ProvideBreathColors(ZoneColors) {

                    val viewModel = hiltViewModel<HabitCheckpointScreenViewModel>()
                    val screenState by viewModel.screenState.collectAsStateWithLifecycle()

                    BackHandler(
                        enabled = screenState.currentSegment
                                !is HabitCheckpointScreenSegment.Checkpoint,
                        onBack = { viewModel.onUIAction(HabitCheckpointUIAction.NavigateUp) }
                    )

                    HabitCheckpointScreen(
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

                            is SettingsScreenUIAction.Notifications -> {
                                Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS)
                                    .apply {
                                        putExtra(
                                            Settings.EXTRA_APP_PACKAGE,
                                            context.packageName
                                        )
                                    }.also { context.startActivity(it) }
                            }

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