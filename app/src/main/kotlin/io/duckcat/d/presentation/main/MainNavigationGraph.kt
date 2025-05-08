package io.duckcat.d.presentation.main

import android.content.Intent
import android.provider.Settings
import androidx.activity.compose.BackHandler
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import io.duckcat.d.core.etc.UiString.Companion.asString
import io.duckcat.d.core.ui.navigateSingleTop
import io.duckcat.d.core.ui.theme.DreamyNightColors
import io.duckcat.d.core.ui.theme.MelonColors
import io.duckcat.d.core.ui.theme.ProvideBreathColors
import io.duckcat.d.core.ui.theme.SkyBlueColors
import io.duckcat.d.core.ui.theme.ZoneColors
import io.duckcat.d.presentation.fitness.FitnessViewModel
import io.duckcat.d.presentation.main.breathe.BreatheScreen
import io.duckcat.d.presentation.main.breathe.BreatheScreenUIAction
import io.duckcat.d.presentation.main.breathe.BreatheScreenViewModel
import io.duckcat.d.presentation.main.explore.ExploreScreen
import io.duckcat.d.presentation.main.explore.ExploreScreenUIAction
import io.duckcat.d.presentation.main.explore.ExploreScreenViewModel
import io.duckcat.d.presentation.main.fitness.FitnessEditScreen
import io.duckcat.d.presentation.main.fitness.FitnessNormalWeightScreen
import io.duckcat.d.presentation.main.fitness.FitnessObeseScreen
import io.duckcat.d.presentation.main.fitness.FitnessOverweightScreen
import io.duckcat.d.presentation.main.fitness.FitnessSetupScreen
import io.duckcat.d.presentation.main.fitness.FitnessUnderweightScreen
import io.duckcat.d.presentation.main.habit.main.HabitMainScreen
import io.duckcat.d.presentation.main.habit.main.HabitMainScreenUIAction
import io.duckcat.d.presentation.main.habit.main.HabitMainScreenViewModel
import io.duckcat.d.presentation.main.habit.setup.HabitSetupScreen
import io.duckcat.d.presentation.main.habit.setup.HabitSetupScreenUIAction
import io.duckcat.d.presentation.main.habit.setup.HabitSetupScreenViewModel
import io.duckcat.d.presentation.main.home.HomeScreen
import io.duckcat.d.presentation.main.home.HomeScreenUIAction
import io.duckcat.d.presentation.main.home.HomeScreenViewModel
import io.duckcat.d.presentation.main.pomodoro.PomodoroScreen
import io.duckcat.d.presentation.main.pomodoro.PomodoroScreenUIAction
import io.duckcat.d.presentation.main.pomodoro.PomodoroViewModel
import io.duckcat.d.presentation.main.productivity.ProductivityScreenUIAction
import io.duckcat.d.presentation.main.productivity.ProductivityScreenViewModel
import io.duckcat.d.presentation.main.productivity.components.ProductivityScreen
import io.duckcat.d.presentation.main.settings.SettingsScreen
import io.duckcat.d.presentation.main.settings.SettingsScreenUIAction
import io.duckcat.d.presentation.main.settings.SettingsScreenUIAction.NavigateUp
import io.duckcat.d.presentation.main.settings.SettingsScreenViewModel
import io.duckcat.d.presentation.main.settings.about.SettingsAboutScreen
import io.duckcat.d.presentation.main.settings.about.SettingsAboutScreenUIAction
import io.duckcat.d.presentation.main.settings.about.SettingsAboutScreenViewModel
import io.duckcat.d.presentation.main.settings.profile.SettingsProfileScreen
import io.duckcat.d.presentation.main.settings.profile.SettingsProfileScreenUIAction
import io.duckcat.d.presentation.main.settings.profile.SettingsProfileScreenViewModel
import io.duckcat.d.presentation.main.soundscape.SoundscapeFilterScreen
import io.duckcat.d.presentation.main.soundscape.SoundscapeUIAction
import io.duckcat.d.presentation.main.soundscape.SoundscapeViewModel
import io.duckcat.d.presentation.study.StudyPlanerScreen

import kotlinx.coroutines.flow.collectLatest


fun NavGraphBuilder.mainNavigationGraph(
    navController: NavHostController,
    onRecreateActivity: () -> Unit
) {

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

                            is HomeScreenUIAction.NavigateToHabitControl -> navController
                                .navigateSingleTop(route = uiAction.route, inclusive = false)

                            is HomeScreenUIAction.NavigateToProductivity -> navController
                                .navigateSingleTop(route = uiAction.route, inclusive = false)

                            is HomeScreenUIAction.NavigateToPomodoro -> navController
                                .navigateSingleTop(route = uiAction.route, inclusive = false)

                            is HomeScreenUIAction.NavigateToSoundscapeFilter -> navController
                                .navigateSingleTop(route = uiAction.route, inclusive = false)

                            is HomeScreenUIAction.NavigateToStudy -> navController
                                .navigateSingleTop(route = MainNavigationDestinations.StudyPlaner.route)

                            is HomeScreenUIAction.NavigateToExplore -> navController
                                .navigateSingleTop(
                                    route = MainNavigationDestinations.Explore.route,
                                    inclusive = false
                                )

                            is HomeScreenUIAction.NavigateToSettings ->
                                navController.navigateSingleTop(
                                    route = uiAction.route,
                                    inclusive = false
                                )


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
            route = MainNavigationDestinations.Explore.route,
            content = {
                val viewModel = hiltViewModel<ExploreScreenViewModel>()
                val snackBarHostState = remember { SnackbarHostState() }

                LaunchedEffect(viewModel.uiActionFlow) {
                    viewModel.uiActionFlow.collectLatest { uiAction ->
                        when (uiAction) {
                            is ExploreScreenUIAction.NavigateToHome ->
                                navController.navigateSingleTop(route = MainNavigationDestinations.Home.route)

                            is ExploreScreenUIAction.NavigateToSettings ->
                                navController.navigateSingleTop(route = MainNavigationDestinations.Settings.route)

                            is ExploreScreenUIAction.NavigateToSleep ->
                                navController.navigateSingleTop(route = MainNavigationDestinations.Sleep.route)

                            is ExploreScreenUIAction.NavigateToBreathe ->
                                navController.navigateSingleTop(route = MainNavigationDestinations.Breathe.route)


                            is ExploreScreenUIAction.NavigateToHabitControl ->
                                navController.navigateSingleTop(route = MainNavigationDestinations.HabitControlSetup.route)

                            is ExploreScreenUIAction.NavigateToProductivity ->
                                navController.navigateSingleTop(route = MainNavigationDestinations.Productivity.route)

                            is ExploreScreenUIAction.NavigateToPomodoro ->
                                navController.navigateSingleTop(route = MainNavigationDestinations.Pomodoro.route)

                            is ExploreScreenUIAction.NavigateToFitness ->
                                navController.navigateSingleTop(route = MainNavigationDestinations.FitnessSetup.route)

                            is ExploreScreenUIAction.NavigateToSoundscapeFilter -> navController
                                .navigateSingleTop(route = MainNavigationDestinations.SoundscapeFilter.route)

                            is ExploreScreenUIAction.NavigateToStudy -> navController
                                .navigateSingleTop(route = MainNavigationDestinations.StudyPlaner.route)

                            else -> Unit
                        }
                    }
                }

                ExploreScreen(
                    snackBarHostState = snackBarHostState,
                    onUIAction = viewModel::onUIAction
                )
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

            composable(route = MainNavigationDestinations.StudyPlaner.route) {
                StudyPlanerScreen(
                    onEditSubject = { subject ->
                        navController.navigate("studyEdit/${subject.id}")
                    }
                )
            }

        composable(
            route = MainNavigationDestinations.Pomodoro.route,
            content = {
                val viewModel = hiltViewModel<PomodoroViewModel>()


                LaunchedEffect(viewModel.uiActionFlow) {
                    viewModel.uiActionFlow.collect { uiAction ->
                        when (uiAction) {
                            is PomodoroScreenUIAction.NavigateUp -> navController.navigateUp()
                            else -> Unit
                        }
                    }
                }

                PomodoroScreen(
                    onUIAction = viewModel::onUIAction
                )
            }
        )

        composable(
            route = MainNavigationDestinations.SoundscapeFilter.route,
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

                    SoundscapeFilterScreen(
                        viewModel = viewModel,
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
        ) { backStackEntry ->
            ProvideBreathColors(ZoneColors) {
                val viewModel = hiltViewModel<HabitMainScreenViewModel>()
                val screenState by viewModel.screenState.collectAsStateWithLifecycle()

                // ✅ Use the NavController passed into NavGraph
                LaunchedEffect(Unit) {
                    viewModel.uiActionFlow.collect { action ->
                        when (action) {
                            HabitMainScreenUIAction.NavigateUp -> {
                                navController.navigateUp()  // ✅ Now correct navController is used
                            }
                        }
                    }
                }

                BackHandler {
                    viewModel.onUIAction(HabitMainScreenUIAction.NavigateUp)
                }

                HabitMainScreen(
                    screenState = screenState,
                    onUIAction = viewModel::onUIAction
                )
            }
        }





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
                val appPreferences = hiltViewModel<SettingsScreenViewModel>().appPreferences
                val snackbarHostState = remember { SnackbarHostState() }
                val context = LocalContext.current

                var isDeleteDataDialogVisible by rememberSaveable { mutableStateOf(false) }

                LaunchedEffect(viewModel.uiActionFlow) {
                    viewModel.uiActionFlow.collectLatest { uiAction ->
                        when (uiAction) {
                            is NavigateUp -> navController.navigateUp()

                            is SettingsScreenUIAction.Profile -> {
                                navController.navigateSingleTop(
                                    route = MainNavigationDestinations.SettingsProfile.route,
                                    popUpTo = MainNavigationDestinations.Settings.route,
                                    inclusive = false
                                )
                            }

                            is SettingsScreenUIAction.Notifications -> {
                                Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS).apply {
                                    putExtra(Settings.EXTRA_APP_PACKAGE, context.packageName)
                                }.also { context.startActivity(it) }
                            }

                            is SettingsScreenUIAction.ShowDeleteDataDialog -> isDeleteDataDialogVisible =
                                true

                            is SettingsScreenUIAction.DismissDeleteDataDialog -> isDeleteDataDialogVisible =
                                false

                            is SettingsScreenUIAction.ConfirmDeleteData -> onRecreateActivity()

                            is SettingsScreenUIAction.About -> {
                                navController.navigateSingleTop(
                                    route = MainNavigationDestinations.SettingsAbout.route,
                                    popUpTo = MainNavigationDestinations.Settings.route,
                                    inclusive = false
                                )
                            }

                            is SettingsScreenUIAction.NavigateToHome -> {
                                navController.navigateSingleTop(
                                    route = MainNavigationDestinations.Home.route,
                                    popUpTo = MainNavigationDestinations.Settings.route,
                                    inclusive = false
                                )
                            }

                            is SettingsScreenUIAction.NavigateToExplore -> {
                                navController.navigateSingleTop(
                                    route = MainNavigationDestinations.Explore.route,
                                    popUpTo = MainNavigationDestinations.Settings.route,
                                    inclusive = false
                                )
                            }

                            is SettingsScreenUIAction.NavigateToProfile -> {
                                navController.navigateSingleTop(
                                    route = MainNavigationDestinations.Profile.route,
                                    popUpTo = MainNavigationDestinations.Settings.route,
                                    inclusive = false
                                )
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
                    appPreferences = appPreferences,  // ✅ FIXED: Injected AppPreferences
                    snackbarHostState = snackbarHostState,
                    isDeleteDataDialogVisible = isDeleteDataDialogVisible,
                    onUIAction = viewModel::onUIAction
                )
            }
        )

        composable(
            route = MainNavigationDestinations.SettingsProfile.route,
            content = {

                val viewModel = hiltViewModel<SettingsProfileScreenViewModel>()
                val screenState by viewModel.screenState.collectAsStateWithLifecycle()

                val focusRequester = remember { FocusRequester() }
                val keyboardController = LocalSoftwareKeyboardController.current

                LaunchedEffect(viewModel.uiActionFlow) {
                    viewModel.uiActionFlow.collectLatest { uiAction ->
                        when (uiAction) {

                            is SettingsProfileScreenUIAction.NavigateUp -> {
                                navController.navigateUp()
                            }

                            is SettingsProfileScreenUIAction.Edit -> {
                                focusRequester.requestFocus()
                                keyboardController?.show()
                            }

                            else -> Unit

                        }
                    }
                }

                SettingsProfileScreen(
                    screenState = screenState,
                    focusRequester = focusRequester,
                    onUIAction = viewModel::onUIAction
                )

            }
        )

        composable(
            route = MainNavigationDestinations.SettingsAbout.route,
            content = {

                val viewModel = hiltViewModel<SettingsAboutScreenViewModel>()

                LaunchedEffect(viewModel.uiActionFlow) {
                    viewModel.uiActionFlow.collectLatest { uiAction ->
                        when (uiAction) {
                            is SettingsAboutScreenUIAction.NavigateUp -> {
                                navController.navigateUp()
                            }
                        }
                    }
                }

                SettingsAboutScreen(
                    onUIAction = viewModel::onUIAction
                )

            }
        )


        composable(route = MainNavigationDestinations.FitnessEdit.route) {
            val viewModel = hiltViewModel<FitnessViewModel>()
            val screenState by viewModel.screenState.collectAsState()
            FitnessEditScreen(
                screenState = screenState,
                onSubmit = { height, weight ->
                    viewModel.onSubmitFitnessData(height, weight)
                },
                onNavigateBack = {
                    when (screenState.bmiCategory) {
                        "Underweight" -> navController.navigate(MainNavigationDestinations.FitnessUnderweight.route) {
                            popUpTo(MainNavigationDestinations.FitnessEdit.route) {
                                inclusive = true
                            }
                        }

                        "Normal" -> navController.navigate(MainNavigationDestinations.FitnessNormal.route) {
                            popUpTo(MainNavigationDestinations.FitnessEdit.route) {
                                inclusive = true
                            }
                        }

                        "Overweight" -> navController.navigate(MainNavigationDestinations.FitnessOverweight.route) {
                            popUpTo(MainNavigationDestinations.FitnessEdit.route) {
                                inclusive = true
                            }
                        }

                        "Obesity" -> navController.navigate(MainNavigationDestinations.FitnessObese.route) {
                            popUpTo(MainNavigationDestinations.FitnessEdit.route) {
                                inclusive = true
                            }
                        }
                    }
                }
            )
        }



        composable(route = MainNavigationDestinations.FitnessSetup.route) {
            val viewModel = hiltViewModel<FitnessViewModel>()

            val screenState by viewModel.screenState.collectAsState()

            LaunchedEffect(screenState.isSetupCompleted) {
                if (screenState.isSetupCompleted) {
                    when (screenState.bmiCategory) {
                        "Underweight" -> navController.navigate(MainNavigationDestinations.FitnessUnderweight.route) {
                            popUpTo(MainNavigationDestinations.FitnessSetup.route) {
                                inclusive = true
                            }
                        }

                        "Normal" -> navController.navigate(MainNavigationDestinations.FitnessNormal.route) {
                            popUpTo(MainNavigationDestinations.FitnessSetup.route) {
                                inclusive = true
                            }
                        }

                        "Overweight" -> navController.navigate(MainNavigationDestinations.FitnessOverweight.route) {
                            popUpTo(MainNavigationDestinations.FitnessSetup.route) {
                                inclusive = true
                            }
                        }

                        "Obesity" -> navController.navigate(MainNavigationDestinations.FitnessObese.route) {
                            popUpTo(MainNavigationDestinations.FitnessSetup.route) {
                                inclusive = true
                            }
                        }

                        else -> {
                        }
                    }
                }
            }

            if (!screenState.isSetupCompleted) {
                FitnessSetupScreen(
                    screenState = screenState,
                    onSubmit = { height, weight ->
                        viewModel.onSubmitFitnessData(height, weight)
                    },
                    onNavigateResult = {}
                )
            }
        }

        composable(route = MainNavigationDestinations.FitnessUnderweight.route) {
            val viewModel = hiltViewModel<FitnessViewModel>()
            val screenState by viewModel.screenState.collectAsState()
            FitnessUnderweightScreen(
                fitnessState = screenState,
                onEditClick = { navController.navigate(MainNavigationDestinations.FitnessEdit.route) }
            )
        }

        composable(route = MainNavigationDestinations.FitnessNormal.route) {
            val viewModel = hiltViewModel<FitnessViewModel>()
            val screenState by viewModel.screenState.collectAsState()
            FitnessNormalWeightScreen(
                fitnessState = screenState,
                onEditClick = { navController.navigate(MainNavigationDestinations.FitnessEdit.route) }
            )
        }

        composable(route = MainNavigationDestinations.FitnessOverweight.route) {
            val viewModel = hiltViewModel<FitnessViewModel>()
            val screenState by viewModel.screenState.collectAsState()
            FitnessOverweightScreen(
                fitnessState = screenState,
                onEditClick = { navController.navigate(MainNavigationDestinations.FitnessEdit.route) }
            )
        }

        composable(route = MainNavigationDestinations.FitnessObese.route) {
            val viewModel = hiltViewModel<FitnessViewModel>()
            val screenState by viewModel.screenState.collectAsState()
            FitnessObeseScreen(
                fitnessState = screenState,
                onEditClick = { navController.navigate(MainNavigationDestinations.FitnessEdit.route) }
            )
        }

        }
    }



