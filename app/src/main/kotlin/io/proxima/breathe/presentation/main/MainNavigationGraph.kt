package io.proxima.breathe.presentation.main

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
import androidx.navigation.compose.rememberNavController
import io.proxima.breathe.core.etc.UiString.Companion.asString
import io.proxima.breathe.core.ui.navigateSingleTop
import io.proxima.breathe.core.ui.theme.DreamyNightColors
import io.proxima.breathe.core.ui.theme.MelonColors
import io.proxima.breathe.core.ui.theme.ProvideBreathColors
import io.proxima.breathe.core.ui.theme.SkyBlueColors
import io.proxima.breathe.core.ui.theme.SleepColors
import io.proxima.breathe.core.ui.theme.ZoneColors
import io.proxima.breathe.presentation.fitness.FitnessNormalScreen
import io.proxima.breathe.presentation.fitness.FitnessObeseScreen
import io.proxima.breathe.presentation.fitness.FitnessOverweightScreen
import io.proxima.breathe.presentation.fitness.FitnessSetupScreen
import io.proxima.breathe.presentation.fitness.FitnessUnderweightScreen
import io.proxima.breathe.presentation.fitness.FitnessViewModel
import io.proxima.breathe.presentation.main.breathe.BreatheScreen
import io.proxima.breathe.presentation.main.breathe.BreatheScreenUIAction
import io.proxima.breathe.presentation.main.breathe.BreatheScreenViewModel
import io.proxima.breathe.presentation.main.explore.ExploreScreen
import io.proxima.breathe.presentation.main.explore.ExploreScreenUIAction
import io.proxima.breathe.presentation.main.explore.ExploreScreenViewModel
import io.proxima.breathe.presentation.main.habit.checkpoint.HabitCheckpointScreen
import io.proxima.breathe.presentation.main.habit.checkpoint.HabitCheckpointScreenSegment
import io.proxima.breathe.presentation.main.habit.checkpoint.HabitCheckpointScreenViewModel
import io.proxima.breathe.presentation.main.habit.checkpoint.HabitCheckpointUIAction
import io.proxima.breathe.presentation.main.habit.main.HabitMainScreen
import io.proxima.breathe.presentation.main.habit.main.HabitMainScreenUIAction
import io.proxima.breathe.presentation.main.habit.main.HabitMainScreenViewModel
import io.proxima.breathe.presentation.main.habit.setup.HabitSetupScreen
import io.proxima.breathe.presentation.main.habit.setup.HabitSetupScreenUIAction
import io.proxima.breathe.presentation.main.habit.setup.HabitSetupScreenViewModel
import io.proxima.breathe.presentation.main.home.HomeScreen
import io.proxima.breathe.presentation.main.home.HomeScreenUIAction
import io.proxima.breathe.presentation.main.home.HomeScreenViewModel
import io.proxima.breathe.presentation.main.pomodoro.PomodoroScreen
import io.proxima.breathe.presentation.main.pomodoro.PomodoroScreenUIAction
import io.proxima.breathe.presentation.main.pomodoro.PomodoroViewModel
import io.proxima.breathe.presentation.main.productivity.ProductivityScreenUIAction
import io.proxima.breathe.presentation.main.productivity.ProductivityScreenViewModel
import io.proxima.breathe.presentation.main.productivity.components.ProductivityScreen
import io.proxima.breathe.presentation.main.settings.SettingsScreen
import io.proxima.breathe.presentation.main.settings.SettingsScreenUIAction
import io.proxima.breathe.presentation.main.settings.SettingsScreenUIAction.DismissDeleteDataDialog
import io.proxima.breathe.presentation.main.settings.SettingsScreenUIAction.NavigateUp
import io.proxima.breathe.presentation.main.settings.SettingsScreenUIAction.ShowDeleteDataDialog
import io.proxima.breathe.presentation.main.settings.SettingsScreenViewModel
import io.proxima.breathe.presentation.main.settings.about.SettingsAboutScreen
import io.proxima.breathe.presentation.main.settings.about.SettingsAboutScreenUIAction
import io.proxima.breathe.presentation.main.settings.about.SettingsAboutScreenViewModel
import io.proxima.breathe.presentation.main.settings.profile.SettingsProfileScreen
import io.proxima.breathe.presentation.main.settings.profile.SettingsProfileScreenUIAction
import io.proxima.breathe.presentation.main.settings.profile.SettingsProfileScreenViewModel
import io.proxima.breathe.presentation.main.soundscape.SoundScapeScreen
import io.proxima.breathe.presentation.main.soundscape.SoundscapeFilterScreen
import io.proxima.breathe.presentation.main.soundscape.SoundscapeUIAction
import io.proxima.breathe.presentation.main.soundscape.SoundscapeViewModel
import io.proxima.breathe.presentation.study.StudyPlanerMainScreen
import io.proxima.breathe.presentation.study.StudyPlanerSetupScreen
import io.proxima.breathe.presentation.study.StudyPlanerViewModel
import kotlinx.coroutines.flow.collectLatest
//


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

//                            is HomeScreenUIAction.NavigateToSoundscape -> navController
//                                .navigateSingleTop(route = uiAction.route, inclusive = false)

                            is HomeScreenUIAction.NavigateToHabitControl -> navController
                                .navigateSingleTop(route = uiAction.route, inclusive = false)

                            is HomeScreenUIAction.NavigateToProductivity -> navController
                                .navigateSingleTop(route = uiAction.route, inclusive = false)

//                            is HomeScreenUIAction.NavigateToSettings -> navController
//                                .navigateSingleTop(route = uiAction.route, inclusive = false)

                            is HomeScreenUIAction.NavigateToMlAssist -> navController
                                .navigateSingleTop(route = uiAction.route, inclusive = false)

                            is HomeScreenUIAction.NavigateToPomodoro -> navController
                                .navigateSingleTop(route = uiAction.route, inclusive = false)

                            is HomeScreenUIAction.NavigateToSoundscapeFilter -> navController
                                .navigateSingleTop(route = uiAction.route, inclusive = false)

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

//        composable(
//            route = MainNavigationDestinations.Sleep.route,
//            content = {
//
//                ProvideBreathColors(SleepColors) {
//
//                    val viewModel = hiltViewModel<SleepScreenViewModel>()
//                    val screenState by viewModel.screenState.collectAsStateWithLifecycle()
//
//                    LaunchedEffect(viewModel.uiActionFlow) {
//                        viewModel.uiActionFlow.collectLatest { uiAction ->
//                            when (uiAction) {
//                                is SleepScreenUIAction.NavigateUp -> navController.navigateUp()
//                                else -> Unit
//                            }
//                        }
//                    }
//
//                    SleepScreen(
//                        screenState = screenState,
//                        onUIAction = viewModel::onUIAction
//                    )
//
//                }
//
//            }
//        )


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

//                            is ExploreScreenUIAction.NavigateToSoundscape ->
//                                navController.navigateSingleTop(route = MainNavigationDestinations.Soundscape.route)

                            is ExploreScreenUIAction.NavigateToHabitControl ->
                                navController.navigateSingleTop(route = MainNavigationDestinations.HabitControlSetup.route)

                            is ExploreScreenUIAction.NavigateToProductivity ->
                                navController.navigateSingleTop(route = MainNavigationDestinations.Productivity.route)

                            is ExploreScreenUIAction.NavigateToMlAssist ->
                                navController.navigateSingleTop(route = MainNavigationDestinations.MlAssist.route)

                            is ExploreScreenUIAction.NavigateToPomodoro ->
                                navController.navigateSingleTop(route = MainNavigationDestinations.Pomodoro.route)

                            is ExploreScreenUIAction.NavigateToFitness ->
                                navController.navigateSingleTop(route = MainNavigationDestinations.FitnessSetup.route)

                            is ExploreScreenUIAction.NavigateToSoundscapeFilter -> navController
                                .navigateSingleTop(route = MainNavigationDestinations.SoundscapeFilter.route)

                            is ExploreScreenUIAction.NavigateToStudy -> navController
                                .navigateSingleTop(route = MainNavigationDestinations.StudySetup.route)

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
        composable(route = MainNavigationDestinations.StudyMain.route) {
            val viewModel = hiltViewModel<StudyPlanerViewModel>()
            val subjects by viewModel.subjectsFlow.collectAsStateWithLifecycle()
            StudyPlanerMainScreen(
                subjects = subjects,
                currentFocusSubject = viewModel.currentFocusSubject,
                onEditSubject = { /* navigate to an edit screen if needed */ }
            )
        }

        composable(route = MainNavigationDestinations.StudySetup.route) {
            val viewModel = hiltViewModel<StudyPlanerViewModel>()
            StudyPlanerSetupScreen(
                onAddSubject = { name, priority, examDate ->
                    viewModel.addSubject(name, priority, examDate)
                },
                onDone = {
                    navController.navigate(MainNavigationDestinations.StudyMain.route) {
                        popUpTo(MainNavigationDestinations.StudySetup.route) { inclusive = true }
                    }
                }
            )
        }
        composable(
            route = MainNavigationDestinations.Pomodoro.route,
            content = {
                val viewModel = hiltViewModel<PomodoroViewModel>()

                // Listen for UI actions (e.g., NavigateUp) and handle them.
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

//        composable(
//            route = MainNavigationDestinations.Home.route,
//            content = {
//                val viewModel = hiltViewModel<HomeScreenViewModel>()
//                val screenState by viewModel.screenState.collectAsState()
//
//                LaunchedEffect(viewModel.uiActionFlow) {
//                    viewModel.uiActionFlow.collectLatest { uiAction ->
//                        when (uiAction) {
//                            is HomeScreenUIAction.NavigateToSettings ->
//                                navController.navigateSingleTop(route = uiAction.route, inclusive = false)
//                            is HomeScreenUIAction.NavigateToExplore ->
//                                navController.navigateSingleTop(route = MainNavigationDestinations.Explore.route, inclusive = false)
//                            else -> Unit
//                        }
//                    }
//                }
//
//                HomeScreen(
//                    screenState = screenState,
//                    snackBarHostState = remember { SnackbarHostState() },
//                    onUIAction = viewModel::onUIAction
//                )
//            }
//        )


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

                    // Here, we call the filter screen instead of the original screen.
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

        /* composable(
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
        )*/

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

                            is SettingsScreenUIAction.ShowDeleteDataDialog -> isDeleteDataDialogVisible = true
                            is SettingsScreenUIAction.DismissDeleteDataDialog -> isDeleteDataDialogVisible = false
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

        // Fitness Module Route
        composable(route = MainNavigationDestinations.FitnessSetup.route) {
            val viewModel = hiltViewModel<FitnessViewModel>()
            // Use collectAsState to observe the fitness state inside a composable
            val screenState by viewModel.screenState.collectAsState()
            // Launch an effect that navigates when setup is complete
            LaunchedEffect(screenState.isSetupCompleted) {
                if (screenState.isSetupCompleted) {
                    when (screenState.bmiCategory) {
                        "Underweight" -> navController.navigate(MainNavigationDestinations.FitnessUnderweight.route) {
                            popUpTo(MainNavigationDestinations.FitnessSetup.route) { inclusive = true }
                        }
                        "Normal" -> navController.navigate(MainNavigationDestinations.FitnessNormal.route) {
                            popUpTo(MainNavigationDestinations.FitnessSetup.route) { inclusive = true }
                        }
                        "Overweight" -> navController.navigate(MainNavigationDestinations.FitnessOverweight.route) {
                            popUpTo(MainNavigationDestinations.FitnessSetup.route) { inclusive = true }
                        }
                        "Obesity" -> navController.navigate(MainNavigationDestinations.FitnessObese.route) {
                            popUpTo(MainNavigationDestinations.FitnessSetup.route) { inclusive = true }
                        }
                        else -> { /* Fallback, if needed */ }
                    }
                }
            }
            // Show the FitnessSetupScreen only if setup is not completed
            if (!screenState.isSetupCompleted) {
                FitnessSetupScreen(
                    screenState = screenState,
                    onSubmit = { height, weight ->
                        viewModel.onSubmitFitnessData(height, weight)
                    },
                    onNavigateResult = {} // Optional: provide if needed
                )
            }
        }

        composable(route = MainNavigationDestinations.FitnessUnderweight.route) {
            val viewModel = hiltViewModel<FitnessViewModel>()
            val screenState by viewModel.screenState.collectAsState()
            FitnessUnderweightScreen(bmi = screenState.bmi)
        }
        composable(route = MainNavigationDestinations.FitnessNormal.route) {
            val viewModel = hiltViewModel<FitnessViewModel>()
            val screenState by viewModel.screenState.collectAsState()
            FitnessNormalScreen(bmi = screenState.bmi)
        }
        composable(route = MainNavigationDestinations.FitnessOverweight.route) {
            val viewModel = hiltViewModel<FitnessViewModel>()
            val screenState by viewModel.screenState.collectAsState()
            FitnessOverweightScreen(bmi = screenState.bmi)
        }
        composable(route = MainNavigationDestinations.FitnessObese.route) {
            val viewModel = hiltViewModel<FitnessViewModel>()
            val screenState by viewModel.screenState.collectAsState()
            FitnessObeseScreen(bmi = screenState.bmi)
        }

        // ... Other composable routes (Settings, etc.) ...
    }
}


