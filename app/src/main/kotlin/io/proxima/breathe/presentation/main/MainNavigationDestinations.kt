package io.proxima.breathe.presentation.main

import androidx.compose.runtime.Immutable

@Immutable
sealed class MainNavigationDestinations(
    val route: String
) {

    data object Home : MainNavigationDestinations("main_home_screen")
    data object Sleep : MainNavigationDestinations("main_sleep_screen")
    data object Breathe : MainNavigationDestinations("main_breathe_screen")
    data object Soundscape : MainNavigationDestinations("main_soundscape_screen")
    data object Productivity : MainNavigationDestinations("main_productivity_screen")

    data object HabitControlSetup : MainNavigationDestinations("main_habit_control_setup_screen")
    data object HabitControlMain : MainNavigationDestinations("main_habit_control_main_screen")
    data object HabitControlCheckpoint : MainNavigationDestinations("main_habit_control_checkpoint_screen")

    data object Settings : MainNavigationDestinations("main_settings_screen")
    data object SettingsProfile : MainNavigationDestinations("main_settings_profile_screen")
    data object SettingsAbout : MainNavigationDestinations("main_settings_about_screen")

    companion object {
        const val NAV_ROUTE = "main_navigation"
    }

}