package io.proxima.breathe.presentation.main.habit.main

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.KeyboardArrowLeft
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import io.proxima.breathe.core.ui.theme.backgroundBrush
import io.proxima.breathe.core.ui.components.topbar.TopBarDefaults
import io.proxima.breathe.core.ui.components.topbar.basic_topbar.BasicTopBar
import io.proxima.breathe.core.ui.theme.BreathTheme
import sv.lib.squircleshape.SquircleShape

@Composable
fun HabitMainScreen(
    screenState: HabitMainScreenState,
    onUIAction: (HabitMainScreenUIAction) -> Unit
) {

    val topBarScrollBehavior = TopBarDefaults.exitUntilCollapsedScrollBehavior()
    val lazyListState = rememberLazyListState()

    AnimatedVisibility(
        modifier = Modifier.fillMaxSize(),
        visible = screenState.hasOngoingHabit == true
    ) {

        Scaffold(
            modifier = Modifier
                .fillMaxSize()
                .background(BreathTheme.colors.backgroundBrush())
                .nestedScroll(topBarScrollBehavior.nestedScrollConnection),
            containerColor = Color.Unspecified,
            contentColor = BreathTheme.colors.text,
            topBar = {

                BasicTopBar(
                    titleText = screenState.habitName,
                    scrollBehavior = topBarScrollBehavior,
                    navigationIcon = {

                        IconButton(
                            onClick = { onUIAction(HabitMainScreenUIAction.NavigateUp) }
                        ) {

                            Icon(
                                modifier = Modifier.size(32.dp),
                                imageVector = Icons.AutoMirrored.Rounded.KeyboardArrowLeft,
                                contentDescription = "Navigate Up.",
                                tint = BreathTheme.colors.text
                            )

                        }

                    }
                )

            }
        ) { innerPadding ->

            Box(modifier = Modifier.fillMaxSize()) {

                Box(
                    modifier = Modifier
                        .padding(
                            PaddingValues(
                                top = innerPadding.calculateTopPadding()
                            )
                        )
                        .fillMaxSize()
                        .clip(SquircleShape(topStart = 32.dp, topEnd = 32.dp))
                        .background(BreathTheme.colors.background)
                )

                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    state = lazyListState,
                    contentPadding = innerPadding
                ) {

                    item(
                        key = "reason_title",
                        content = {

                            Text(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 48.dp),
                                text = "Reason to stay on the ZONE!",
                                style = BreathTheme.typography.titleMedium,
                                textAlign = TextAlign.Center
                            )

                        }
                    )

                    item(
                        key = "quote_text",
                        content = {

                            Text(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(bottom = 32.dp),
                                text = "\"${screenState.habitQuote}\"",
                                style = BreathTheme.typography.titleSmall,
                                textAlign = TextAlign.Center
                            )

                        }
                    )

                    item(
                        key = "username_text",
                        content = {

                            Text(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(bottom = 32.dp, end = 64.dp),
                                text = "- ${screenState.username}",
                                style = BreathTheme.typography.titleSmall,
                                textAlign = TextAlign.End
                            )

                        }
                    )

                    item(
                        key = "divider",
                        content = {

                            Box(
                                modifier = Modifier
                                    .padding(horizontal = 48.dp)
                                    .fillMaxWidth()
                                    .height(1.dp)
                                    .background(
                                        color = BreathTheme.colors.text.copy(.5f),
                                        shape = SquircleShape()
                                    )
                            )

                        }
                    )

                    item(
                        key = "progress_component",
                        content = {

                            Text(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 32.dp, start = 48.dp),
                                text = "Your goal: ${screenState.habitName} " +
                                        "for ${screenState.habitDuration} days",
                                style = BreathTheme.typography.titleSmall,
                                textAlign = TextAlign.Start
                            )

                            Box(
                                modifier = Modifier
                                    .padding(horizontal = 48.dp, vertical = 32.dp)
                                    .fillMaxWidth()
                                    .height(1.dp)
                                    .background(
                                        color = BreathTheme.colors.text.copy(.5f),
                                        shape = SquircleShape()
                                    )
                            )

                            Text(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(start = 48.dp),
                                text = "You've been on a ${screenState.habitProgress} day streak",
                                style = BreathTheme.typography.titleSmall,
                                textAlign = TextAlign.Start
                            )

                            HabitMainProgressComponent(
                                modifier = Modifier
                                    .padding(vertical = 24.dp)
                                    .fillMaxWidth()
                                    .height(48.dp)
                                    .padding(horizontal = 24.dp),
                                goalDuration = screenState.habitDuration,
                                goalProgress = screenState.habitProgress
                            )

                        }
                    )

                }

            }

        }

    }

}