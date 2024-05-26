package choehaualen.breath.presentation.welcome

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.runtime.Immutable
import choehaualen.breath.R
import choehaualen.breath.core.ui.colors.BreathDefaultColors
import choehaualen.breath.core.ui.colors.Colors
import choehaualen.breath.core.ui.colors.DreamyNightColors
import choehaualen.breath.core.ui.colors.MelonColors
import choehaualen.breath.core.ui.colors.SilverColors
import choehaualen.breath.core.ui.colors.SleepColors

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

    data object Privacy : WelcomeScreenSegment(
        title = R.string.welcome_screen_privacy_segment_title,
        description = R.string.welcome_screen_privacy_segment_description,
        icon = R.drawable.privacy,
        colors = SilverColors
    )

}