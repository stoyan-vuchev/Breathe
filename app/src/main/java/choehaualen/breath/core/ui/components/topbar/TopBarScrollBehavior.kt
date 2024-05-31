package choehaualen.breath.core.ui.components.topbar

import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.AnimationState
import androidx.compose.animation.core.DecayAnimationSpec
import androidx.compose.animation.core.animateDecay
import androidx.compose.animation.core.animateTo
import androidx.compose.runtime.Stable
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.unit.Velocity
import kotlin.math.abs

/**
 *
 * A [TopBarScrollBehavior] that adjusts its properties to affect the colors and height of a top bar.
 *
 * A top bar that is set up with this [TopBarScrollBehavior] will immediately collapse when
 * the nested content is pulled up, and will expand back the collapsed area when the content is
 * pulled all the way down.
 *
 * @param state a [TopBarState].
 * @param snapAnimationSpec an optional [AnimationSpec] that defines how the top bar snaps to
 * either fully collapsed or fully extended state when a fling or a drag scrolled it into an
 * intermediate position.
 *
 * @param flingAnimationSpec an optional [DecayAnimationSpec] that defined how to fling the top
 * bar when the user flings the top bar itself, or the content below it.
 *
 * @param canScroll a callback used to determine whether scroll events are to be
 * handled by this [ExitUntilCollapsedScrollBehavior].
 *
 */
@Stable
internal class ExitUntilCollapsedScrollBehavior(
    override val state: TopBarState,
    override val snapAnimationSpec: AnimationSpec<Float>?,
    override val flingAnimationSpec: DecayAnimationSpec<Float>?,
    val canScroll: () -> Boolean = { true }
) : TopBarScrollBehavior {

    override val isPinned: Boolean = false

    override var nestedScrollConnection = object : NestedScrollConnection {

        override fun onPreScroll(
            available: Offset,
            source: NestedScrollSource
        ): Offset {

            // Don't intercept if scrolling down.
            if (!canScroll() || available.y > 0f) return Offset.Zero

            val prevHeightOffset = state.heightOffset

            state.heightOffset += available.y

            return if (prevHeightOffset != state.heightOffset) {
                // We're in the middle of top app bar collapse or expand.
                // Consume only the scroll on the Y axis.
                available.copy(x = 0f)
            } else {
                Offset.Zero
            }

        }

        override fun onPostScroll(
            consumed: Offset,
            available: Offset,
            source: NestedScrollSource
        ): Offset {

            if (!canScroll()) return Offset.Zero

            if (available.y < 0f || consumed.y < 0f) {
                // When scrolling up, just update the state's height offset.
                val oldHeightOffset = state.heightOffset
                state.heightOffset += consumed.y
                return Offset(0f, state.heightOffset - oldHeightOffset)
            }

            if (available.y > 0f) {
                // Adjust the height offset in case the consumed delta Y is less than what was
                // recorded as available delta Y in the pre-scroll.
                val oldHeightOffset = state.heightOffset
                state.heightOffset += available.y
                return Offset(0f, state.heightOffset - oldHeightOffset)
            }

            return Offset.Zero

        }

        override suspend fun onPostFling(consumed: Velocity, available: Velocity): Velocity {
            val superConsumed = super.onPostFling(consumed, available)
            return superConsumed + settleAppBar(
                state = state,
                velocity = available.y,
                flingAnimationSpec = flingAnimationSpec,
                snapAnimationSpec = snapAnimationSpec
            )
        }

    }

}

/**
 * Settles the top bar by flinging, in case the given velocity is greater than zero, and snapping
 * after the fling settles.
 */
@Stable
internal suspend fun settleAppBar(
    state: TopBarState,
    velocity: Float,
    flingAnimationSpec: DecayAnimationSpec<Float>?,
    snapAnimationSpec: AnimationSpec<Float>?
): Velocity {

    // Check if the top bar is completely collapsed/expanded. If so, no need to settle the top bar,
    // and just return Zero Velocity.
    // Note that we don't check for 0f due to float precision with the collapsedFraction
    // calculation.
    if (state.collapsedFraction < 0.01f || state.collapsedFraction == 1f) {
        return Velocity.Zero
    }

    var remainingVelocity = velocity

    // In case there is an initial velocity that was left after a previous user fling, animate to
    // continue the motion to expand or collapse the top bar.
    if (flingAnimationSpec != null && abs(velocity) > 1f) {
        var lastValue = 0f
        AnimationState(
            initialValue = 0f,
            initialVelocity = velocity,
        ).animateDecay(flingAnimationSpec) {

            val delta = value - lastValue
            val initialHeightOffset = state.heightOffset

            state.heightOffset = initialHeightOffset + delta
            val consumed = abs(initialHeightOffset - state.heightOffset)

            lastValue = value
            remainingVelocity = this.velocity

            // avoid rounding errors and stop if anything is unconsumed
            if (abs(delta - consumed) > 0.5f) this.cancelAnimation()

        }
    }

    // Snap if animation specs were provided.
    if (snapAnimationSpec != null) {
        if (state.heightOffset < 0 &&
            state.heightOffset > state.heightOffsetLimit
        ) {
            AnimationState(initialValue = state.heightOffset).animateTo(
                targetValue = if (state.collapsedFraction < 0.5f) 0f
                else state.heightOffsetLimit,
                animationSpec = snapAnimationSpec
            ) { state.heightOffset = value }
        }
    }

    return Velocity(0f, remainingVelocity)

}

@Stable
interface TopBarScrollBehavior {

    /**
     * A [TopBarState] that is attached to this behavior and is read and updated when scrolling
     * happens.
     */
    val state: TopBarState

    /**
     * Indicates whether the top bar is pinned.
     *
     * A pinned top bar will stay fixed in place when content is scrolled and will not react to any
     * drag gestures.
     */
    val isPinned: Boolean

    /**
     * An optional [AnimationSpec] that defines how the top bar snaps to either fully collapsed
     * or fully extended state when a fling or a drag scrolled it into an intermediate position.
     */
    val snapAnimationSpec: AnimationSpec<Float>?

    /**
     * An optional [DecayAnimationSpec] that defined how to fling the top bar when the user
     * flings the top bar itself, or the content below it.
     */
    val flingAnimationSpec: DecayAnimationSpec<Float>?

    /**
     * A [NestedScrollConnection] that should be attached to a Modifier.nestedScroll in order to
     * keep track of the scroll events.
     */
    val nestedScrollConnection: NestedScrollConnection

}