package choehaualen.breath.presentation.main.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import choehaualen.breath.R
import choehaualen.breath.core.etc.transformFraction
import choehaualen.breath.core.ui.colors.DreamyNightColors
import choehaualen.breath.core.ui.colors.MelonColors
import choehaualen.breath.core.ui.colors.SleepColors
import choehaualen.breath.core.ui.components.rememberBreathRipple
import choehaualen.breath.core.ui.components.snackbar.SnackBar
import choehaualen.breath.core.ui.components.topbar.TopBarDefaults
import choehaualen.breath.core.ui.components.topbar.day_view_topbar.DayViewTopBar
import choehaualen.breath.core.ui.theme.BreathTheme
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.HazeStyle
import dev.chrisbanes.haze.haze
import dev.chrisbanes.haze.hazeChild
import sv.lib.squircleshape.SquircleShape

@Composable
fun HomeScreen(
    snackBarHostState: SnackbarHostState,
    onUIAction: (HomeScreenUIAction) -> Unit
) {

    val topBarScrollBehavior = TopBarDefaults.exitUntilCollapsedScrollBehavior()

    val bgAlpha by remember(topBarScrollBehavior.state.collapsedFraction) {
        derivedStateOf { topBarScrollBehavior.state.collapsedFraction }
    }

    val gradientAlpha by remember(bgAlpha) {
        derivedStateOf { 1f - bgAlpha }
    }

    val topBarBgAlpha by remember(gradientAlpha) { // this ensures smooth color transition
        derivedStateOf {
            transformFraction(
                value = 1f - gradientAlpha,
                startX = .8f,
                endX = 1f,
                startY = 0f,
                endY = .5f
            )
        }
    }

    val background by rememberUpdatedState(
        Brush.verticalGradient(
            colors = listOf(
                BreathTheme.colors.backgroundGradientStart.copy(gradientAlpha),
                BreathTheme.colors.backgroundGradientEnd.copy(gradientAlpha)
            )
        )
    )

    val hazeState = remember { HazeState() }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(background)
            .nestedScroll(topBarScrollBehavior.nestedScrollConnection),
        containerColor = BreathTheme.colors.background.copy(bgAlpha),
        contentColor = BreathTheme.colors.text,
        topBar = {

            DayViewTopBar(
                modifier = Modifier.hazeChild(
                    state = hazeState,
                    style = HazeStyle(
                        tint = BreathTheme.colors.background.copy(topBarBgAlpha),
                        blurRadius = 20.dp
                    )
                ),
                scrollBehavior = topBarScrollBehavior,
                actions = {

                    IconButton(
                        onClick = { onUIAction(HomeScreenUIAction.Settings) },
                        content = {

                            Icon(
                                imageVector = Icons.Rounded.Settings,
                                contentDescription = "Settings"
                            )

                        }
                    )

                }
            )

        },
        snackbarHost = {

            SnackbarHost(
                hostState = snackBarHostState,
                snackbar = { SnackBar(it) }
            )

        }
    ) { insetsPadding ->

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .haze(hazeState),
            contentPadding = insetsPadding
        ) {

            quotesItem(onUIAction = onUIAction)

            togglesItem(onUIAction = onUIAction)

        }

    }

}

private fun LazyListScope.quotesItem(
    quote: String = "You will face many defeats in life, but never let yourself be defeated.",
    author: String = "Maya Angelov",
    onUIAction: (HomeScreenUIAction) -> Unit
) = item(key = "daily_quote") {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberBreathRipple(color = BreathTheme.colors.primarySoul),
                onClick = { onUIAction(HomeScreenUIAction.ExpandQuote) }
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = "Daily Quotes ~",
            style = BreathTheme.typography.headlineSmall
        )

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            modifier = Modifier.padding(horizontal = 32.dp),
            text = "“$quote”",
            style = BreathTheme.typography.labelMedium,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 64.dp),
            text = "- $author",
            style = BreathTheme.typography.labelMedium,
            textAlign = TextAlign.End
        )

        Spacer(modifier = Modifier.height(8.dp))

    }

}

private fun LazyListScope.togglesItem(
    onUIAction: (HomeScreenUIAction) -> Unit
) = item(key = "toggles") {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clip(SquircleShape(40.dp))
            .background(BreathTheme.colors.card)
            .padding(16.dp)
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            HomeScreenToggle(
                modifier = Modifier.weight(1f),
                icon = painterResource(id = R.drawable.sleep),
                label = "Sleep",
                background = Brush.verticalGradient(
                    colors = listOf(
                        SleepColors.primarySoul,
                        SleepColors.secondarySoul
                    )
                ),
                onClick = { onUIAction(HomeScreenUIAction.NavigateToSleep()) }
            )

            HomeScreenToggle(
                modifier = Modifier.weight(1f),
                icon = painterResource(id = R.drawable.leaf),
                label = "Breathe",
                background = Brush.verticalGradient(
                    colors = listOf(
                        MelonColors.primarySoul,
                        MelonColors.secondarySoul
                    )
                ),
                onClick = { onUIAction(HomeScreenUIAction.NavigateToBreathe()) }
            )

        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            HomeScreenToggle(
                modifier = Modifier.weight(1f),
                icon = painterResource(id = R.drawable.sound_wave),
                label = "Soundscape",
                background = Brush.verticalGradient(
                    colors = listOf(
                        DreamyNightColors.primarySoul,
                        DreamyNightColors.secondarySoul
                    )
                ),
                onClick = { onUIAction(HomeScreenUIAction.NavigateToSoundscape()) }
            )

            HomeScreenToggle(
                modifier = Modifier.weight(1f),
                icon = painterResource(id = R.drawable.more_hor),
                label = "More",
                background = Brush.verticalGradient(
                    colors = listOf(
                        Color.DarkGray,
                        Color.DarkGray
                    )
                ),
                onClick = { onUIAction(HomeScreenUIAction.ShowMore) }
            )

        }

    }

}

@Preview
@Composable
private fun HomeScreenPreview() = BreathTheme {
    HomeScreen(
        snackBarHostState = SnackbarHostState(),
        onUIAction = {}
    )
}