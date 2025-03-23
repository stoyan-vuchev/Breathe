package io.proxima.breathe.core.ui.components.topbar.day_view_topbar

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import io.proxima.breathe.core.etc.Result
import io.proxima.breathe.core.etc.transformFraction
import io.proxima.breathe.core.ui.components.topbar.TopBarDefaults
import io.proxima.breathe.core.ui.components.topbar.TopBarScrollBehavior
import io.proxima.breathe.core.ui.components.topbar.settleAppBar
import io.proxima.breathe.core.ui.theme.BreathDefaultColors
import io.proxima.breathe.core.ui.theme.BreathTheme
import io.proxima.breathe.core.utils.TimestampUtils
import io.proxima.breathe.data.preferences.AppPreferences
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun DayViewTopBar(
    modifier: Modifier = Modifier,
    appPreferences: AppPreferences,  // ✅ AppPreferences injected
    actions: @Composable (RowScope.() -> Unit)? = null,
    scrollBehavior: TopBarScrollBehavior? = null,
    backgroundColor: Color = Color.Unspecified,
    contentColor: Color = BreathDefaultColors.background,
    largeTitleTextStyle: TextStyle = BreathTheme.typography.displayLarge,
    smallTitleTextStyle: TextStyle = BreathTheme.typography.headlineLarge,
    windowInsets: WindowInsets = TopBarDefaults.windowInsets()
) {
    var username by remember { mutableStateOf("") }
    val coroutineScope = rememberCoroutineScope()

    // ✅ Load user name from preferences
    LaunchedEffect(Unit) {
        coroutineScope.launch {
            when (val result = appPreferences.getUser()) {
                is Result.Success -> username = result.data ?: "Guest"
                is Result.Error -> username = "Guest"
            }
        }
    }

    DayViewTopBarLayout(
        modifier = modifier,
        largeTitleTextStyle = largeTitleTextStyle,
        smallTitleTextStyle = smallTitleTextStyle,
        actions = actions,
        windowInsets = windowInsets,
        backgroundColor = backgroundColor,
        contentColor = contentColor,
        maxHeight = TopBarDefaults.largeContainerHeight(scrollBehavior, 1f / 5f),
        pinnedHeight = TopBarDefaults.smallContainerHeight,
        scrollBehavior = scrollBehavior,
        username = username
    )
}

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
    scrollBehavior: TopBarScrollBehavior?,
    username: String
) = CompositionLocalProvider(LocalContentColor provides contentColor) {

    val density = LocalDensity.current
    val statusBarHeightPx = windowInsets.getTop(density).toFloat()
    val pinnedHeightPx = with(density) { pinnedHeight.toPx() } + statusBarHeightPx
    val maxHeightPx = with(density) { maxHeight.toPx() } + statusBarHeightPx

    scrollBehavior?.state?.heightOffsetLimit = pinnedHeightPx - maxHeightPx

    val collapsedFraction by rememberUpdatedState { scrollBehavior?.state?.collapsedFraction ?: 0f }

    val height by remember {
        derivedStateOf {
            with(density) { (maxHeightPx + (scrollBehavior?.state?.heightOffset ?: 0f)).toDp() }
                .coerceAtLeast(0.dp)
        }
    }

    val statusBarHeight = with(density) { statusBarHeightPx.toDp() }

    val secondLineEndPadding by remember(collapsedFraction) {
        derivedStateOf {
            transformFraction(collapsedFraction().coerceIn(0f, 1f), 0f, 1f, 0f, 86f).dp
        }
    }

    val expandedSubTitleAlpha by remember(collapsedFraction) {
        derivedStateOf {
            transformFraction(1f - collapsedFraction().coerceIn(0f, 1f), 0.67f, 1f, 1f, 0f)
        }
    }

    val collapsedSubTitleAlpha by remember(collapsedFraction) {
        derivedStateOf {
            transformFraction(collapsedFraction().coerceIn(0f, 1f), .67f, .33f, 0f, 1f)
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
                settleAppBar(scrollBehavior.state, velocity, scrollBehavior.flingAnimationSpec, scrollBehavior.snapAnimationSpec)
            }
        }
    }

    val timestamp = remember { System.currentTimeMillis() }
    val date by rememberUpdatedState(TimestampUtils.extractMonthOfTheYear(timestamp))

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
        contentAlignment = Alignment.CenterStart
    ) {

        Box(
            modifier = Modifier.windowInsetsPadding(windowInsets.only(WindowInsetsSides.Horizontal)),
            contentAlignment = Alignment.CenterStart
        ) {

            Row(
                modifier = Modifier.padding(top = statusBarHeight),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier.padding(start = 40.dp),
                    text = if (username.isNotEmpty()) "Hi, $username" else "Hi"
                )

                Box(
                    modifier = Modifier
                        .padding(start = 8.dp, end = secondLineEndPadding)
                        .fillMaxWidth(),
                    contentAlignment = Alignment.CenterStart
                ) {
                    Box(
                        modifier = Modifier
                            .graphicsLayer { alpha = collapsedSubTitleAlpha }
                            .padding(start = 8.dp)
                    ) {
                        ProvideTextStyle(value = smallTitleTextStyle) {
                            Text(text = date)
                        }
                    }
                }
            }

            Box(
                modifier = Modifier
                    .graphicsLayer {
                        alpha = expandedSubTitleAlpha
                        translationY = with(density) { 32.dp.toPx() } // ✅ FIXED toPx
                    }
                    .padding(start = 40.dp)
                    .align(Alignment.BottomStart)
            ) {
                ProvideTextStyle(value = smallTitleTextStyle) {
                    Text(text = "Good Morning")
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
}
