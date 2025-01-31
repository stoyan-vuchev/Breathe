package io.proxima.breathe.presentation.main.home

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.pager.rememberPagerState
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.HazeStyle
import dev.chrisbanes.haze.haze
import dev.chrisbanes.haze.hazeChild
import io.proxima.breathe.core.etc.transformFraction
import io.proxima.breathe.core.ui.components.rememberBreathRipple
import io.proxima.breathe.core.ui.components.snackbar.SnackBar
import io.proxima.breathe.core.ui.components.topbar.TopBarDefaults
import io.proxima.breathe.core.ui.components.topbar.day_view_topbar.DayViewTopBar
import io.proxima.breathe.core.ui.theme.BreathTheme
import io.proxima.breathe.domain.model.QuoteModel
import sv.lib.squircleshape.SquircleShape

@Composable
fun HomeScreen(
    screenState: HomeScreenState,
    snackBarHostState: SnackbarHostState,
    onUIAction: (HomeScreenUIAction) -> Unit
) {

    val topBarScrollBehavior = TopBarDefaults.exitUntilCollapsedScrollBehavior()

    val bgAlpha by remember(topBarScrollBehavior.state.collapsedFraction) {
        derivedStateOf { topBarScrollBehavior.state.collapsedFraction }
    }

    val topBarBgAlpha by remember(bgAlpha) { // this ensures smooth color transition
        derivedStateOf {
            transformFraction(
                value = bgAlpha,
                startX = .8f,
                endX = 1f,
                startY = 0f,
                endY = .5f
            )
        }
    }

    val hazeState = remember { HazeState() }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
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
                        onClick = { onUIAction(HomeScreenUIAction.NavigateToSettings()) },
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

        },
    ) { insetsPadding ->

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .haze(hazeState),
            contentPadding = insetsPadding
        ) {

            quotesItem(
                quote = screenState.quote,
                onUIAction = onUIAction
            )

            togglesItem(onUIAction = onUIAction)

        }

    }

}

@OptIn(ExperimentalSharedTransitionApi::class)
private fun LazyListScope.quotesItem(
    quote: QuoteModel,
    onUIAction: (HomeScreenUIAction) -> Unit
) = item(key = "daily_quote") {

    SharedTransitionLayout(
        modifier = Modifier
            .fillMaxSize()
    ) {

        AnimatedContent(
            modifier = Modifier.fillMaxWidth(),
            targetState = quote != QuoteModel.Empty,
            label = "",
        ) { isQuoteVisible ->

            if (isQuoteVisible) {

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

                    with(this@SharedTransitionLayout) {
                        Text(
                            modifier = Modifier
                                .padding(horizontal = 32.dp)
                                .sharedElement(
                                    rememberSharedContentState(key = "quote"),
                                    animatedVisibilityScope = this@AnimatedContent
                                ),
                            text = "“${quote.quote}”",
                            style = BreathTheme.typography.labelMedium,
                            textAlign = TextAlign.Center
                        )
                    }

                    Spacer(modifier = Modifier.height(32.dp))

                    with(this@SharedTransitionLayout) {

                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .sharedElement(
                                    rememberSharedContentState(key = "author"),
                                    animatedVisibilityScope = this@AnimatedContent
                                )
                                .padding(horizontal = 64.dp),
                            text = "- ${quote.author}",
                            style = BreathTheme.typography.labelMedium,
                            textAlign = TextAlign.End
                        )

                    }

                    Spacer(modifier = Modifier.height(8.dp))

                }

            }

        }

    }

}

private fun LazyListScope.togglesItem(
    onUIAction: (HomeScreenUIAction) -> Unit
) = item(key = "toggles") {

    HomeScreenGridPager(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clip(SquircleShape(40.dp))
            .background(BreathTheme.colors.card),
        state = rememberPagerState { 2 },
        onUIAction = onUIAction
    )

}

@Preview(showBackground = true)
@Composable
private fun HomeScreenPreview() = BreathTheme {
    HomeScreen(
        screenState = HomeScreenState(),
        snackBarHostState = SnackbarHostState(),
        onUIAction = {}
    )
}