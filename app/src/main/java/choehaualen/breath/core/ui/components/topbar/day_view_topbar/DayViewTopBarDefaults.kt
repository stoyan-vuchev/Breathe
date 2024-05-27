package choehaualen.breath.core.ui.components.topbar.day_view_topbar

import android.content.res.Configuration.ORIENTATION_PORTRAIT
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.DecayAnimationSpec
import androidx.compose.animation.core.spring
import androidx.compose.animation.rememberSplineBasedDecay
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.systemBars
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * Useful utilities for a top bar UI components.
 */
object DayViewTopBarDefaults {

    /**
     * Default insets to be used and consumed by the top bars.
     */
    @Composable
    fun windowInsets(): WindowInsets {
        val insets by rememberUpdatedState(
            WindowInsets.systemBars.only(
                sides = WindowInsetsSides.Horizontal + WindowInsetsSides.Top
            )
        )
        return insets
    }

    /**
     *
     * Returns a [DayViewTopBarScrollBehavior] that adjusts its properties to affect the colors and
     * height of the top app bar.
     *
     * A top app bar that is set up with this [DayViewTopBarScrollBehavior] will immediately collapse
     * when the nested content is pulled up, and will expand back the collapsed area when the
     * content is  pulled all the way down.
     *
     * @param state the state object to be used to control or observe the top app bar's scroll
     * state. See [rememberDayViewTopBarState] for a state that is remembered across compositions.
     *
     * @param canScroll a callback used to determine whether scroll events are to be
     * handled by this [ExitUntilCollapsedScrollBehavior].
     *
     * @param snapAnimationSpec an optional [AnimationSpec] that defines how the top app bar snaps
     * to either fully collapsed or fully extended state when a fling or a drag scrolled it into an
     * intermediate position.
     *
     * @param flingAnimationSpec an optional [DecayAnimationSpec] that defined how to fling the top
     * app bar when the user flings the app bar itself, or the content below it.
     *
     */
    @Composable
    fun exitUntilCollapsedScrollBehavior(
        state: DayViewTopBarState = rememberDayViewTopBarState(),
        canScroll: () -> Boolean = { true },
        snapAnimationSpec: AnimationSpec<Float>? = spring(),
        flingAnimationSpec: DecayAnimationSpec<Float>? = rememberSplineBasedDecay()
    ): DayViewTopBarScrollBehavior = ExitUntilCollapsedScrollBehavior(
        state = state,
        snapAnimationSpec = snapAnimationSpec,
        flingAnimationSpec = flingAnimationSpec,
        canScroll = canScroll
    )

    /**
     * The smallest possible height of the top bar.
     */
    val smallContainerHeight: Dp get() = 72.dp

    /**
     *
     * Returns the height of an largest possible height of the top bar.
     *
     * @param scrollBehavior The scroll behavior of the top bar.
     * @param factor The multiplication factor to determine the top bar height from the window height.
     *
     */
    @Composable
    internal fun largeContainerHeight(
        scrollBehavior: DayViewTopBarScrollBehavior? = null,
        // 1/4 of the window height by default.
        factor: Float = 1f / 4f
    ): Dp {

        val minHeight = 175.dp
        val configuration = LocalConfiguration.current
        var maxHeight by remember { mutableStateOf<Dp?>(null) }

        LaunchedEffect(configuration) {
            if (scrollBehavior != null) {
                settleAppBar(
                    state = scrollBehavior.state,
                    velocity = 10f,
                    flingAnimationSpec = scrollBehavior.flingAnimationSpec,
                    snapAnimationSpec = scrollBehavior.snapAnimationSpec
                )
            }
        }

        // If the screen orientation is set to landscape, return a minimum
        // top bar container height of 175.dp, otherwise calculate the maximum
        // possible top bar container height using by dividing the window height
        // by the specified factor and return the result with a minimum of 175.dp.

        return if (configuration.orientation != ORIENTATION_PORTRAIT) minHeight
        else maxHeight ?: LocalDensity.current.run {

            // Calculate the maximum height.
            maxHeight = ((configuration.screenHeightDp.dp +
                    WindowInsets.systemBars.getBottom(this).toDp()) *
                    factor.coerceIn(.25f, .5f)).coerceAtLeast(minHeight)

            // Return the calculated maximum height.
            maxHeight!!

        }

    }

}