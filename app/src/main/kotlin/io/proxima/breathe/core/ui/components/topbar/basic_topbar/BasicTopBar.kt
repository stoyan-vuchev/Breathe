package io.proxima.breathe.core.ui.components.topbar.basic_topbar

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsPadding
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
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.lerp
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import io.proxima.breathe.core.etc.transformFraction
import io.proxima.breathe.core.ui.components.topbar.TopBarDefaults
import io.proxima.breathe.core.ui.components.topbar.TopBarScrollBehavior
import io.proxima.breathe.core.ui.components.topbar.settleAppBar
import io.proxima.breathe.core.ui.theme.BreathTheme
import kotlinx.coroutines.CoroutineScope

@Composable
fun BasicTopBar(
    modifier: Modifier = Modifier,
    navigationIcon: @Composable (RowScope.() -> Unit)? = null,
    actions: @Composable (RowScope.() -> Unit)? = null,
    titleText: String,
    scrollBehavior: TopBarScrollBehavior? = null,
    backgroundColor: Color = Color.Unspecified,
    contentColor: Color = BreathTheme.colors.text,
    largeTitleTextStyle: TextStyle = BreathTheme.typography.headlineLarge,
    smallTitleTextStyle: TextStyle = BreathTheme.typography.titleLarge,
    windowInsets: WindowInsets = TopBarDefaults.windowInsets(),
    spanFactor: Float = 1f / 4f,
    animateContent: Boolean = false
) = BasicTopBarLayout(
    modifier = modifier,
    largeTitleTextStyle = largeTitleTextStyle,
    smallTitleTextStyle = smallTitleTextStyle,
    titleText = titleText,
    navigationIcon = navigationIcon,
    actions = actions,
    windowInsets = windowInsets,
    backgroundColor = backgroundColor,
    contentColor = contentColor,
    maxHeight = TopBarDefaults.largeContainerHeight(scrollBehavior, spanFactor),
    pinnedHeight = TopBarDefaults.smallContainerHeight,
    scrollBehavior = scrollBehavior,
    animateContent = animateContent
)

@Composable
private fun BasicTopBarLayout(
    modifier: Modifier = Modifier,
    largeTitleTextStyle: TextStyle,
    smallTitleTextStyle: TextStyle,
    titleText: String,
    navigationIcon: @Composable (RowScope.() -> Unit)?,
    actions: @Composable (RowScope.() -> Unit)?,
    windowInsets: WindowInsets,
    backgroundColor: Color,
    contentColor: Color,
    maxHeight: Dp,
    pinnedHeight: Dp,
    scrollBehavior: TopBarScrollBehavior?,
    animateContent: Boolean
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

    val titleTextStyle by remember(collapsedFraction) {
        derivedStateOf {
            lerp(
                start = largeTitleTextStyle,
                stop = smallTitleTextStyle,
                fraction = collapsedFraction()
            )
        }
    }

    val titlePadding by remember(collapsedFraction, statusBarHeight) {
        derivedStateOf {
            PaddingValues(
                top = transformFraction(
                    value = 1f - collapsedFraction(),
                    startX = 0f,
                    endX = 1f,
                    startY = 0f,
                    endY = statusBarHeight.value
                ).dp,
                bottom = transformFraction(
                    value = 1f - collapsedFraction(),
                    startX = 0f,
                    endX = 1f,
                    startY = 0f,
                    endY = TopBarDefaults.smallContainerHeight.value
                ).dp
            )
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
                modifier = Modifier
                    .windowInsetsPadding(windowInsetsPadding)
                    .padding(top = statusBarHeight)
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {

                ProvideTextStyle(
                    value = titleTextStyle,
                    content = {

                        Text(
                            modifier = Modifier
                                .padding(titlePadding)
                                .then(
                                    if (animateContent) Modifier
                                        .animateContentSize(
                                            animationSpec = spring(),
                                            alignment = Alignment.Center
                                        )
                                    else Modifier
                                ),
                            text = titleText,
                            textAlign = TextAlign.Center
                        )

                    }
                )

            }

            if (navigationIcon != null) {
                Row(
                    modifier = Modifier
                        .padding(horizontal = 8.dp)
                        .height(pinnedHeight)
                        .align(Alignment.BottomStart),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically,
                    content = navigationIcon
                )
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