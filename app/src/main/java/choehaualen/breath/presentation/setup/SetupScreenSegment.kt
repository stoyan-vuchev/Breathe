package choehaualen.breath.presentation.setup

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
    val description: String
) {

    data object TrackAndImproveSleep : SetupScreenSegment(
        colors = SleepColors,
        title = "Track and Improve Sleep",
        description = "Tracks sleep pattern using on-device artificial intelligence model that leverages the device’s light and motion sensors and gives sleep\n" +
                "results based on it."
    )

    data object BreathExercises : SetupScreenSegment(
        colors = MelonColors,
        title = "Breath exercises",
        description = "Breath exercises  to keep stress away\n" +
                "by using different breathing techniques which even can control how fast your heart beats"
    )

    data object Soundscape : SetupScreenSegment(
        colors = DreamyNightColors,
        title = "Soundscape",
        description = "Curated library with nature \n" +
                "ambient sound which"
    )

    data object Privacy : SetupScreenSegment(
        colors = SilverColors,
        title = "Privacy",
        description = "Breath won’t share any data it processes, \n" +
                "everything is processed on device"
    )

}