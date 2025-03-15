package io.proxima.breathe.presentation.main.home

import android.content.Intent
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerDefaults
import androidx.compose.foundation.pager.PagerState
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import io.proxima.breathe.R
import io.proxima.breathe.core.ui.carouselTransition
import io.proxima.breathe.core.ui.components.HorizontalPagerIndicator
import io.proxima.breathe.core.ui.fadingEdges
import io.proxima.breathe.core.ui.theme.BreathTheme
import io.proxima.breathe.presentation.main.mlassist.ChatActivity

@Composable
fun HomeScreenGridPager(
    modifier: Modifier = Modifier,
    state: PagerState,
    onUIAction: (HomeScreenUIAction) -> Unit,
) = Column(
    modifier = modifier
        .fadingEdges(
            startOffset = 16.dp,
            endOffset = 16.dp,
            minFade = .9f
        )
        .padding(bottom = 16.dp),
    horizontalAlignment = Alignment.CenterHorizontally
) {
    HorizontalPager(
        modifier = Modifier.fillMaxWidth(),
        state = state,
        key = { "page_$it" },
        flingBehavior = PagerDefaults.flingBehavior(
            state = state,
            snapAnimationSpec = spring(
                stiffness = Spring.StiffnessVeryLow,
                dampingRatio = Spring.DampingRatioLowBouncy
            )
        ),
        pageContent = { page ->
            when (page) {
                0 -> Column(
                    modifier = Modifier
                        .atAGlancePageModifier(itemPage = 0, state = state)
                ) {

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        HomeScreenToggle(
                            modifier = Modifier.weight(1f),
                            icon = painterResource(id = R.drawable.nulltoggleoverlay),
                            label = "Stress",
                            background = R.drawable.stressback,
                            onClick = { onUIAction(HomeScreenUIAction.NavigateToBreathe()) }
                        )
                        HomeScreenToggle(
                            modifier = Modifier.weight(1f),
                            icon = painterResource(id = R.drawable.nulltoggleoverlay),
                            label = "Productivity",
                            background = R.drawable.productivity,
                            onClick = { onUIAction(HomeScreenUIAction.NavigateToProductivity()) }
                        )
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        HomeScreenToggle(
                            modifier = Modifier.weight(1f),
                            icon = painterResource(id = R.drawable.nulltoggleoverlay),
                            label = "Soundscape",
                            background = R.drawable.soundscape,
                            onClick = { onUIAction(HomeScreenUIAction.NavigateToSoundscape()) }
                        )
                        HomeScreenToggle(
                            modifier = Modifier.weight(1f),
                            icon = painterResource(id = R.drawable.nulltoggleoverlay),
                            boundlessIcon = true,
                            label = "Habit Control",
                            background = R.drawable.zone,
                            onClick = { onUIAction(HomeScreenUIAction.NavigateToHabitControl()) }
                        )
                        HomeScreenToggle(
                            modifier = Modifier.weight(1f),
                            icon = painterResource(id = R.drawable.nulltoggleoverlay),
                            label = "Focus Assist",
                            background = R.drawable.sleep,
                            onClick = { onUIAction(HomeScreenUIAction.NavigateToPomodoro()) }
                        )
                    }
                }
                /*1 -> Column(
                    modifier = Modifier
                        .atAGlancePageModifier(itemPage = 1, state = state)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Spacer(modifier = Modifier.weight(1f))

                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        HomeScreenToggle(
                            modifier = Modifier.weight(1f),
                            icon = painterResource(id = R.drawable.nulltoggleoverlay),
                            label = "More",
                            background = R.drawable.stressback,
                            onClick = { onUIAction(HomeScreenUIAction.More) }
                        )
                        HomeScreenToggle(
                            modifier = Modifier.weight(1f),
                            icon = painterResource(id = R.drawable.nulltoggleoverlay),
                            label = "AI Assist",
                            background = R.drawable.stressback,
                            onClick = { onUIAction(HomeScreenUIAction.NavigateToMlAssist()) }
                        )
                    }
                }*/
            }
        }
    )
    Spacer(modifier = Modifier.height(0.dp))
    HorizontalPagerIndicator(
        pagerState = state,
        pageCount = state.pageCount,
        activeColor = BreathTheme.colors.text,
        inactiveColor = BreathTheme.colors.text.copy(alpha = 0.1f),
        indicatorWidth = 0.dp,
        indicatorHeight = 0.dp
    )
}

private fun Modifier.atAGlancePageModifier(
    itemPage: Int,
    state: PagerState
) = fillMaxWidth()
    .carouselTransition(
        itemPage = itemPage,
        pagerState = state
    )
    .padding(16.dp)
