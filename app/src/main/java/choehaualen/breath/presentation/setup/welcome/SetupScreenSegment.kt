package choehaualen.breath.presentation.setup.welcome

import androidx.annotation.DrawableRes
import choehaualen.breath.R
import choehaualen.breath.core.ui.colors.Colors
import choehaualen.breath.core.ui.colors.DreamyNightColors
import choehaualen.breath.core.ui.colors.MelonColors
import choehaualen.breath.core.ui.colors.SilverColors
import choehaualen.breath.core.ui.colors.SleepColors

/**
 *
 *   A collection of segments for the Splash Screen.
 *
 *   Each segment contains it's unique properties like colors, icons, etc.
 *
 */
sealed class SetupScreenSegment(
    val colors: Colors,
    val title: String,
    @DrawableRes val icon: Int,
    val description: String
) {

    data object TrackAndImproveSleep : SetupScreenSegment(
        colors = SleepColors,
        title = "Track and Improve Sleep",
        icon = R.drawable.sleep,
        description = "Tracks sleep pattern using on-device artificial intelligence model that leverages the device’s light and motion sensors and gives sleep results based on it."
    )

    data object BreathExercises : SetupScreenSegment(
        colors = MelonColors,
        title = "Breath exercises",
        icon = R.drawable.leaf,
        description = "Breath exercises  to keep stress away by using different breathing techniques which even can control how fast your heart beats"
    )

    data object Soundscape : SetupScreenSegment(
        colors = DreamyNightColors,
        title = "Soundscape",
        icon = R.drawable.sound_wave,
        description = "Curated library with nature \n" +
                "ambient sound which"
    )

    data object Privacy : SetupScreenSegment(
        colors = SilverColors,
        title = "Privacy",
        icon = R.drawable.privacy,
        description = "Breath won’t share any data it processes, \n" +
                "everything is processed on device"
    )

}