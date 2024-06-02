package choehaualen.breath.presentation.main.sleep

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import choehaualen.breath.core.etc.transformFraction
import choehaualen.breath.core.ui.components.button.UniqueButton
import choehaualen.breath.core.ui.components.topbar.TopBarDefaults
import choehaualen.breath.core.ui.components.topbar.basic_topbar.BasicTopBar
import choehaualen.breath.core.ui.theme.BreathTheme
import choehaualen.breath.presentation.main.sleep.sleep_goal.SleepScreenSetSleepGoalUIComponent

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

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(background)
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        containerColor = Color.Unspecified,
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

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                state = lazyListState,
                contentPadding = insetsPadding
            ) {

                item(key = "set_sleep_goal_ui_component") {

                    SleepScreenSetSleepGoalUIComponent(
                        state = screenState.sleepGoalUIComponentState,
                        onUIAction = {
                            onUIAction(SleepScreenUIAction.SleepGoalUIAction(it))
                        }
                    )

                }

            }

            Column(
                modifier = Modifier
                    .systemBarsPadding()
                    .padding(bottom = 64.dp)
                    .align(Alignment.BottomCenter)
            ) {

                UniqueButton(
                    onClick = { /*TODO*/ },
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