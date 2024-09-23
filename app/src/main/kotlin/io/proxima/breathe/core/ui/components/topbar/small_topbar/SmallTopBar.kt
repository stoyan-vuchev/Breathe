package io.proxima.breathe.core.ui.components.topbar.small_topbar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.unit.dp
import io.proxima.breathe.core.ui.components.topbar.TopBarDefaults
import io.proxima.breathe.core.ui.theme.BreathTheme

@Composable
fun SmallTopBar(
    modifier: Modifier = Modifier,
    titleText: String,
    navigationIcon: @Composable (RowScope.() -> Unit)? = null,
    actions: @Composable (RowScope.() -> Unit)? = null,
    backgroundColor: Color = Color.Unspecified,
    contentColor: Color = BreathTheme.colors.text,
    titleTextStyle: TextStyle = BreathTheme.typography.titleLarge,
    windowInsets: WindowInsets = TopBarDefaults.windowInsets(),
) = SmallTopBarLayout(
    modifier = modifier,
    titleTextStyle = titleTextStyle,
    titleText = titleText,
    navigationIcon = navigationIcon,
    actions = actions,
    windowInsets = windowInsets,
    backgroundColor = backgroundColor,
    contentColor = contentColor
)

@Composable
private fun SmallTopBarLayout(
    modifier: Modifier = Modifier,
    titleTextStyle: TextStyle,
    titleText: String,
    navigationIcon: @Composable (RowScope.() -> Unit)?,
    actions: @Composable (RowScope.() -> Unit)?,
    windowInsets: WindowInsets,
    backgroundColor: Color,
    contentColor: Color
) = CompositionLocalProvider(LocalContentColor provides contentColor) {

    val density = LocalDensity.current
    val statusBarHeightPx: Float

    density.run {
        statusBarHeightPx = windowInsets.getTop(this).toFloat()
    }

    val statusBarHeight by remember(statusBarHeightPx) {
        derivedStateOf {
            with(density) {
                statusBarHeightPx.toDp()
            }
        }
    }

    Box(
        modifier = modifier
            .height(TopBarDefaults.smallContainerHeight + statusBarHeight)
            .clipToBounds()
            .background(color = backgroundColor),
        content = {

            val windowInsetsPadding by rememberUpdatedState(
                windowInsets.only(WindowInsetsSides.Horizontal)
            )

            Box(
                modifier = Modifier
                    .windowInsetsPadding(windowInsetsPadding)
                    .padding(top = statusBarHeight)
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {

                ProvideTextStyle(
                    value = titleTextStyle,
                    content = { Text(text = titleText) }
                )

            }

            if (navigationIcon != null) {
                Row(
                    modifier = Modifier
                        .padding(horizontal = 8.dp)
                        .height(TopBarDefaults.smallContainerHeight)
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
                        .height(TopBarDefaults.smallContainerHeight)
                        .align(Alignment.BottomEnd),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically,
                    content = actions
                )
            }

        }
    )

}