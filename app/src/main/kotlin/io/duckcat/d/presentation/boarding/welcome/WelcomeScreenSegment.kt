package io.duckcat.d.presentation.boarding.welcome

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.runtime.Immutable
import io.duckcat.d.R
import io.duckcat.d.core.ui.theme.BreathDefaultColors
import io.duckcat.d.core.ui.theme.Colors
import io.duckcat.d.core.ui.theme.DreamyNightColors
import io.duckcat.d.core.ui.theme.MelonColors

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
        icon = R.drawable.app_glow,
        colors = BreathDefaultColors
    )

//    data object TrackSleep : WelcomeScreenSegment(
//        title = R.string.welcome_screen_track_sleep_segment_title,
//        description = R.string.welcome_screen_track_sleep_segment_description,
//        icon = R.drawable.sleep,
//        colors = SleepColors
//    )

    data object BreathExercises : WelcomeScreenSegment(
        title = R.string.welcome_screen_breath_exercises_segment_title,
        description = R.string.welcome_screen_breath_exercises_segment_description,
        icon = R.drawable.hr_icon_svg,
        colors = MelonColors
    )

    data object Soundscape : WelcomeScreenSegment(
        title = R.string.welcome_screen_soundscape_segment_title,
        description = R.string.welcome_screen_soundscape_segment_description,
        icon = R.drawable.emotion_icon_svg,
        colors = DreamyNightColors
    )

    data object Focus : WelcomeScreenSegment(
        title = R.string.welcome_screen_focus_segment_title,
        description = R.string.welcome_screen_focus_segment_description,
        icon = R.drawable.focus_icon_svg,
        colors = DreamyNightColors
    )

    data object Tasks : WelcomeScreenSegment(
        title = R.string.welcome_screen_task_segment_title,
        description = R.string.welcome_screen_task_segment_description,
        icon = R.drawable.task_icon,
        colors = DreamyNightColors
    )




//    data object Puzzle : WelcomeScreenSegment(
//        title = R.string.welcome_screen_puzzle_segment_title,
//        description = R.string.welcome_screen_puzzle_segment_description,
//        icon = R.drawable.puzzle,
//        colors = MangoColors
//    )

//    data object Privacy : WelcomeScreenSegment(
//        title = R.string.welcome_screen_privacy_segment_title,
//        description = R.string.welcome_screen_privacy_segment_description,
//        icon = R.drawable.privacy,
//        colors = SilverColors
//    )

}