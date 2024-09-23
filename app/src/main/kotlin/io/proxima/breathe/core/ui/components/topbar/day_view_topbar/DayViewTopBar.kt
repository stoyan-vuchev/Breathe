package io.proxima.breathe.core.ui.components.topbar.day_view_topbar

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.lerp
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import io.proxima.breathe.core.etc.transformFraction
import io.proxima.breathe.core.ui.components.topbar.TopBarDefaults
import io.proxima.breathe.core.ui.components.topbar.TopBarScrollBehavior
import io.proxima.breathe.core.ui.components.topbar.settleAppBar
import io.proxima.breathe.core.ui.theme.BreathTheme
import io.proxima.breathe.core.utils.TimestampUtils
import kotlinx.coroutines.CoroutineScope

/**
 *
 * A custom resizable top bar UI component that displays day related information.
 *
 * @param modifier Apply further customization.
 * @param actions The actions content e.g. an icon button with an options icon.
 * @param scrollBehavior The scroll behavior of the top bar.
 * @param backgroundColor The background color of the top bar.
 * @param contentColor The content color applied to the [actions].
 * @param largeTitleTextStyle The text style of the title in expanded state.
 * @param smallTitleTextStyle The text style of the title in a collapsed state.
 * @param windowInsets The insets for drawing the top bar content safely and away from the system bars.
 *
 */
@Composable
fun DayViewTopBar(
    modifier: Modifier = Modifier,
    actions: @Composable (RowScope.() -> Unit)? = null,
    scrollBehavior: TopBarScrollBehavior? = null,
    backgroundColor: Color = Color.Unspecified,
    contentColor: Color = BreathTheme.colors.text,
    largeTitleTextStyle: TextStyle = BreathTheme.typography.displayLarge,
    smallTitleTextStyle: TextStyle = BreathTheme.typography.headlineLarge,
    windowInsets: WindowInsets = TopBarDefaults.windowInsets()
) = DayViewTopBarLayout(
    modifier = modifier,
    largeTitleTextStyle = largeTitleTextStyle,
    smallTitleTextStyle = smallTitleTextStyle,
    actions = actions,
    windowInsets = windowInsets,
    backgroundColor = backgroundColor,
    contentColor = contentColor,
    maxHeight = TopBarDefaults.largeContainerHeight(scrollBehavior, 1f / 5f),
    pinnedHeight = TopBarDefaults.smallContainerHeight,
    scrollBehavior = scrollBehavior
)

@Composable
private fun DayViewTopBarLayout(
    modifier: Modifier = Modifier,
    largeTitleTextStyle: TextStyle,
    smallTitleTextStyle: TextStyle,
    actions: @Composable (RowScope.() -> Unit)?,
    windowInsets: WindowInsets,
    backgroundColor: Color,
    contentColor: Color,
    maxHeight: Dp,
    pinnedHeight: Dp,
    scrollBehavior: TopBarScrollBehavior?
) = CompositionLocalProvider(LocalContentColor provides contentColor) {

    val density = LocalDensity.current

    val statusBarHeightPx: Float
    val pinnedHeightPx: Float
    val maxHeightPx: Float

    density.run {

        statusBarHeightPx = windowInsets.getTop(this).toFloat()
        pinnedHeightPx = pinnedHeight.toPx() + statusBarHeightPx
        maxHeightPx = maxHeight.toPx() + statusBarHeightPx

        if (scrollBehavior?.state?.heightOffsetLimit != pinnedHeightPx - maxHeightPx) {
            scrollBehavior?.state?.heightOffsetLimit = pinnedHeightPx - maxHeightPx
        }

    }

    val collapsedFraction by rememberUpdatedState {
        scrollBehavior?.state?.collapsedFraction ?: 0f
    }

    val height by remember(
        density,
        maxHeightPx,
        scrollBehavior?.state?.heightOffset
    ) {
        derivedStateOf {
            with(density) {
                (maxHeightPx + (scrollBehavior?.state?.heightOffset ?: 0f)).toDp()
                    .coerceAtLeast(0.dp)
            }
        }
    }

    val statusBarHeight by remember(statusBarHeightPx) {
        derivedStateOf {
            with(density) {
                statusBarHeightPx.toDp()
            }
        }
    }

    val dayTextStyle by remember(collapsedFraction) {
        derivedStateOf {
            lerp(
                start = largeTitleTextStyle,
                stop = smallTitleTextStyle,
                fraction = collapsedFraction()
            )
        }
    }

    val secondLineEndPadding by remember(collapsedFraction) {
        derivedStateOf {
            transformFraction(
                value = collapsedFraction().coerceIn(0f, 1f),
                startX = 0f,
                endX = 1f,
                startY = 0f,
                endY = 86f
            ).dp
        }
    }

    val expandedSubTitleAlpha by remember(collapsedFraction) {
        derivedStateOf {
            transformFraction(
                value = 1f - collapsedFraction().coerceIn(0f, 1f),
                startX = 0.67f,
                endX = 1f,
                startY = 1f,
                endY = 0f
            )
        }
    }

    val collapsedSubTitleAlpha by remember(collapsedFraction) {
        derivedStateOf {
            transformFraction(
                value = collapsedFraction().coerceIn(0f, 1f),
                startX = .67f,
                endX = .33f,
                startY = 0f,
                endY = 1f
            )
        }
    }

    val firstLineWidth by remember(expandedSubTitleAlpha) {
        derivedStateOf {
            transformFraction(
                value = (expandedSubTitleAlpha * .5f).coerceIn(0f, 1f),
                startY = 48f,
                endY = 16f
            ).dp
        }
    }

    val draggableState = rememberDraggableState { delta ->
        if (scrollBehavior != null && !scrollBehavior.isPinned) {
            scrollBehavior.state.heightOffset += delta
        }
    }

    val onDragStopped = remember<suspend CoroutineScope.(Float) -> Unit>(scrollBehavior) {
        { velocity ->
            if (scrollBehavior != null && !scrollBehavior.isPinned) {
                settleAppBar(
                    state = scrollBehavior.state,
                    velocity = velocity,
                    flingAnimationSpec = scrollBehavior.flingAnimationSpec,
                    snapAnimationSpec = scrollBehavior.snapAnimationSpec
                )
            }
        }
    }

    val timestamp = remember { System.currentTimeMillis() }

    val day by rememberUpdatedState(
        TimestampUtils.extractDayOfTheMonth(timestamp).toString()
    )

    val date by rememberUpdatedState(
        TimestampUtils.extractMonthOfTheYear(timestamp)
    )

    Box(
        modifier = modifier
            .height(height)
            .clipToBounds()
            .background(color = backgroundColor)
            .draggable(
                state = draggableState,
                orientation = Orientation.Vertical,
                onDragStopped = onDragStopped
            ),
        contentAlignment = Alignment.CenterStart,
        content = {

            val windowInsetsPadding by rememberUpdatedState(
                windowInsets.only(WindowInsetsSides.Horizontal)
            )

            Box(
                modifier = Modifier.windowInsetsPadding(windowInsetsPadding),
                contentAlignment = Alignment.CenterStart
            ) {

                Box {

                    Row(
                        modifier = Modifier.padding(top = statusBarHeight),
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        HorizontalDivider(
                            modifier = Modifier
                                .graphicsLayer {
                                    alpha = expandedSubTitleAlpha
                                }
                                .padding(end = 8.dp)
                                .clip(RoundedCornerShape(50))
                                .width(firstLineWidth),
                            color = contentColor
                        )

                        Box(
                            content = {

                                ProvideTextStyle(
                                    value = dayTextStyle,
                                    content = { Text(text = day) }
                                )

                            }
                        )

                        Box(
                            modifier = Modifier
                                .padding(start = 8.dp, end = secondLineEndPadding)
                                .fillMaxWidth(),
                            contentAlignment = Alignment.CenterStart
                        ) {

                            Box(
                                modifier = Modifier
                                    .graphicsLayer {
                                        alpha = collapsedSubTitleAlpha
                                    }
                                    .padding(start = 8.dp)
                            ) {

                                ProvideTextStyle(
                                    value = smallTitleTextStyle,
                                    content = { Text(text = date) }
                                )

                            }

                            HorizontalDivider(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .graphicsLayer {
                                        alpha = expandedSubTitleAlpha
                                    }
                                    .padding(end = secondLineEndPadding)
                                    .clip(RoundedCornerShape(50)),
                                color = contentColor
                            )

                        }

                    }

                    Box(
                        modifier = Modifier
                            .graphicsLayer {
                                alpha = expandedSubTitleAlpha
                                translationY = 32.dp.toPx()
                            }
                            .padding(start = 40.dp)
                            .align(Alignment.BottomStart)
                    ) {

                        ProvideTextStyle(
                            value = smallTitleTextStyle,
                            content = { Text(text = date) }
                        )

                    }

                }

            }

            if (actions != null) {
                Row(
                    modifier = Modifier
                        .padding(end = 16.dp)
                        .height(pinnedHeight)
                        .align(Alignment.BottomEnd),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically,
                    content = actions
                )
            }

        }
    )

}