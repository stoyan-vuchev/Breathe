package io.proxima.breathe.presentation.main.sleep

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.KeyboardArrowLeft
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import io.proxima.breathe.core.etc.transformFraction
import io.proxima.breathe.core.ui.components.button.UniqueButton
import io.proxima.breathe.core.ui.components.topbar.TopBarDefaults
import io.proxima.breathe.core.ui.components.topbar.basic_topbar.BasicTopBar
import io.proxima.breathe.core.ui.theme.BreathTheme
import io.proxima.breathe.presentation.main.sleep.set_sleep_goal.SleepScreenSetSleepGoalUIComponent
import io.proxima.breathe.presentation.main.sleep.sleep_goal_graph.SleepGoalGraph
import sv.lib.squircleshape.SquircleShape

@Composable
fun SleepScreen(
    screenState: SleepScreenState,
    onUIAction: (SleepScreenUIAction) -> Unit
) {

    val lazyListState = rememberLazyListState()
    val scrollBehavior = TopBarDefaults.exitUntilCollapsedScrollBehavior()

    val bgAlpha by remember(scrollBehavior.state.collapsedFraction) {
        derivedStateOf { scrollBehavior.state.collapsedFraction }
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
                endY = 0f // .5f
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

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(background)
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        containerColor = BreathTheme.colors.background.copy(bgAlpha),
        contentColor = BreathTheme.colors.text,
        topBar = {

            BasicTopBar(
                titleText = "Sleep",
                scrollBehavior = scrollBehavior,
                navigationIcon = {

                    IconButton(onClick = { onUIAction(SleepScreenUIAction.NavigateUp) }) {

                        Icon(
                            modifier = Modifier.size(32.dp),
                            imageVector = Icons.AutoMirrored.Rounded.KeyboardArrowLeft,
                            contentDescription = "Navigate up to Home"
                        )

                    }

                },
                backgroundColor = BreathTheme.colors.background.copy(topBarBgAlpha)
            )

        }
    ) { insetsPadding ->

        Box(modifier = Modifier.fillMaxSize()) {

            if (screenState.sleepGoalDuration != null) {
                Box(
                    modifier = Modifier
                        .padding(
                            PaddingValues(
                                top = insetsPadding.calculateTopPadding()
                            )
                        )
                        .fillMaxSize()
                        .clip(SquircleShape(topStart = 32.dp, topEnd = 32.dp))
                        .background(BreathTheme.colors.background)
                )
            }

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                state = lazyListState,
                contentPadding = insetsPadding
            ) {

                if (screenState.sleepGoalDuration == null) {

                    item(key = "set_sleep_goal_ui_component") {

                        SleepScreenSetSleepGoalUIComponent(
                            state = screenState.sleepGoalUIComponentState,
                            onUIAction = {
                                onUIAction(SleepScreenUIAction.SleepGoalUIAction(it))
                            }
                        )

                    }

                } else {

                    item(key = "sleep_goal_graph_ui_component") {

                        SleepGoalGraph(
                            modifier = Modifier
                                .padding(48.dp)
                                .fillMaxWidth()
                                .padding(top = 16.dp),
                            sleepGoalDuration = screenState.sleepGoalDuration,
                            currentSleepDuration = screenState.currentSleepDuration
                        )

                        Spacer(modifier = Modifier.height(24.dp))

                        Text(
                            modifier = Modifier
                                .padding(horizontal = 32.dp)
                                .fillMaxWidth()
                                .clip(SquircleShape(24.dp))
                                .background(BreathTheme.colors.card)
                                .padding(horizontal = 24.dp, vertical = 12.dp),
                            text = "Lack of sleep can have a negative impact " +
                                    "on the ability to think clearly, form memories, " +
                                    "learn well, and function optimally during the day. " +
                                    "Breathe recommends you to sleep at least 7-8h.",
                            style = BreathTheme.typography.bodySmall
                        )

                    }

                }

            }

            if (screenState.sleepGoalDuration == null) {

                Column(
                    modifier = Modifier
                        .systemBarsPadding()
                        .padding(bottom = 64.dp)
                        .align(Alignment.BottomCenter)
                ) {

                    UniqueButton(
                        onClick = { onUIAction(SleepScreenUIAction.Next) },
                        content = { Text(text = "Next") },
                        paddingValues = PaddingValues(
                            horizontal = 48.dp,
                            vertical = 12.dp
                        )
                    )

                }

            }

        }

    }

}