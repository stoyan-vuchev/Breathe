package choehaualen.breath.core.ui.components.topbar.day_view_topbar

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import kotlin.math.absoluteValue
import kotlin.math.roundToInt

/**
 *
 * Creates a [DayViewTopBarState] that is remembered across compositions.
 *
 * @param initialHeightOffsetLimit the initial value for [DayViewTopBarState.heightOffsetLimit],
 * which represents the pixel limit that a top app bar is allowed to collapse when the scrollable
 * content is scrolled.
 *
 * @param initialHeightOffset the initial value for [DayViewTopBarState.heightOffset]. The initial
 * offset height offset should be between zero and [initialHeightOffsetLimit].
 *
 */
@Composable
fun rememberDayViewTopBarState(
    initialHeightOffsetLimit: Float = -Float.MAX_VALUE,
    initialHeightOffset: Float = 0f
): DayViewTopBarState = rememberSaveable(saver = DayViewTopBarState.Saver) {
    DayViewTopBarState(
        initialHeightOffsetLimit,
        initialHeightOffset
    )
}

@Stable
class DayViewTopBarState(
    initialHeightOffsetLimit: Float,
    initialHeightOffset: Float
) {

    private var _heightOffset: Float by mutableFloatStateOf(initialHeightOffset)

    /**
     * The top bar's height offset limit in pixels, which represents the limit that a top app
     * bar is allowed to collapse to.
     *
     * Use this limit to coerce the [heightOffset] value when it's updated.
     */
    var heightOffsetLimit: Float by mutableFloatStateOf(initialHeightOffsetLimit)

    /**
     * The top bar's current height offset in pixels. This height offset is applied to the fixed
     * height of the app bar to control the displayed height when content is being scrolled.
     *
     * Updates to the [heightOffset] value are coerced between zero and [heightOffsetLimit].
     */
    var heightOffset: Float
        get() = _heightOffset
        set(newOffset) {
            _heightOffset = newOffset.coerceIn(
                minimumValue = heightOffsetLimit,
                maximumValue = 0f
            )
        }

    /**
     * A value that represents the collapsed height percentage of the top bar.
     *
     * A `0.0` represents a fully expanded bar, and `1.0` represents a fully collapsed bar (computed
     * as [heightOffset] / [heightOffsetLimit]).
     */
    val collapsedFraction: Float by derivedStateOf {
        if (heightOffsetLimit != 0f) heightOffset / heightOffsetLimit else 0f
    }

    /**
     * Returns the very bottom Y offset of the top bar.
     */
    val bottomOffset: Float by derivedStateOf {
        (heightOffsetLimit + heightOffset.absoluteValue)
            .absoluteValue.roundToInt().toFloat()
    }

    companion object {

        /**
         * The default [Saver] implementation for [DayViewTopBarState].
         */
        val Saver: Saver<DayViewTopBarState, *> = listSaver(
            save = { listOf(it.heightOffsetLimit, it.heightOffset) },
            restore = {
                DayViewTopBarState(
                    initialHeightOffsetLimit = it[0],
                    initialHeightOffset = it[1]
                )
            }
        )

    }

}