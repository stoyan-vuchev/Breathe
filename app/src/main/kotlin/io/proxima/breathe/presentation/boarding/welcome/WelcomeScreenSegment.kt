package io.proxima.breathe.presentation.boarding.welcome

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.runtime.Immutable
import io.proxima.breathe.R
import io.proxima.breathe.core.ui.theme.BreathDefaultColors
import io.proxima.breathe.core.ui.theme.Colors
import io.proxima.breathe.core.ui.theme.DreamyNightColors
import io.proxima.breathe.core.ui.theme.MangoColors
import io.proxima.breathe.core.ui.theme.MelonColors
import io.proxima.breathe.core.ui.theme.SilverColors
import io.proxima.breathe.core.ui.theme.SleepColors

@Immutable
sealed class WelcomeScreenSegment(
    @StringRes val title: Int,
    @StringRes val description: Int,
    @DrawableRes val icon: Int,
    val colors: Colors
) {

    data object Welcome : WelcomeScreenSegment(
        title = R.string.welcome_screen_welcome_segment_title,
        description = R.string.welcome_screen_welcome_segment_description,
        icon = R.drawable.splash_logo,
        colors = BreathDefaultColors
    )

    data object TrackSleep : WelcomeScreenSegment(
        title = R.string.welcome_screen_track_sleep_segment_title,
        description = R.string.welcome_screen_track_sleep_segment_description,
        icon = R.drawable.sleep,
        colors = SleepColors
    )

    data object BreathExercises : WelcomeScreenSegment(
        title = R.string.welcome_screen_breath_exercises_segment_title,
        description = R.string.welcome_screen_breath_exercises_segment_description,
        icon = R.drawable.leaf,
        colors = MelonColors
    )

    data object Soundscape : WelcomeScreenSegment(
        title = R.string.welcome_screen_soundscape_segment_title,
        description = R.string.welcome_screen_soundscape_segment_description,
        icon = R.drawable.sound_wave,
        colors = DreamyNightColors
    )

    data object Puzzle : WelcomeScreenSegment(
        title = R.string.welcome_screen_puzzle_segment_title,
        description = R.string.welcome_screen_puzzle_segment_description,
        icon = R.drawable.puzzle,
        colors = MangoColors
    )

    data object Privacy : WelcomeScreenSegment(
        title = R.string.welcome_screen_privacy_segment_title,
        description = R.string.welcome_screen_privacy_segment_description,
        icon = R.drawable.privacy,
        colors = SilverColors
    )

}