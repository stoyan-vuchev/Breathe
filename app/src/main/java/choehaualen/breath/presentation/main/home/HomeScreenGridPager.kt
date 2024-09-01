package choehaualen.breath.presentation.main.home

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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import choehaualen.breath.R
import choehaualen.breath.core.ui.carouselTransition
import choehaualen.breath.core.ui.colors.DreamyNightColors
import choehaualen.breath.core.ui.colors.MangoColors
import choehaualen.breath.core.ui.colors.MelonColors
import choehaualen.breath.core.ui.colors.SkyBlueColors
import choehaualen.breath.core.ui.colors.SleepColors
import choehaualen.breath.core.ui.colors.ZoneColors
import choehaualen.breath.core.ui.components.HorizontalPagerIndicator
import choehaualen.breath.core.ui.fadingEdges
import choehaualen.breath.core.ui.theme.BreathTheme

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
                            icon = painterResource(id = R.drawable.zone),
                            boundlessIcon = true,
                            label = "Habit Control",
                            background = Brush.verticalGradient(
                                colors = listOf(
                                    ZoneColors.primarySoul,
                                    ZoneColors.secondarySoul
                                )
                            ),
                            onClick = { onUIAction(HomeScreenUIAction.NavigateToHabitControl()) }
                        )

                    }

                }


                1 -> Column(
                    modifier = Modifier
                        .atAGlancePageModifier(itemPage = 1, state = state)
                ) {

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {

                        HomeScreenToggle(
                            modifier = Modifier.weight(1f),
                            icon = painterResource(id = R.drawable.productivity),
                            label = "Productivity",
                            background = Brush.verticalGradient(
                                colors = listOf(
                                    SkyBlueColors.primarySoul,
                                    SkyBlueColors.secondarySoul
                                )
                            ),
                            onClick = { onUIAction(HomeScreenUIAction.NavigateToProductivity()) }
                        )

                        HomeScreenToggle(
                            modifier = Modifier.weight(1f),
                            icon = painterResource(id = R.drawable.puzzle),
                            label = "Puzzle",
                            background = Brush.verticalGradient(
                                colors = listOf(
                                    MangoColors.primarySoul,
                                    MangoColors.secondarySoul
                                )
                            ),
                            onClick = { /* onUIAction(HomeScreenUIAction.NavigateToPuzzle()) */ }
                        )

                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {

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
                            onClick = { onUIAction(HomeScreenUIAction.More) }
                        )

                        Spacer(modifier = Modifier.weight(1f))

                        // Fixed !!!

                    }

                }

            }

        }
    )

    Spacer(modifier = Modifier.height(8.dp))

    HorizontalPagerIndicator(
        pagerState = state,
        pageCount = state.pageCount,
        activeColor = BreathTheme.colors.text,
        inactiveColor = BreathTheme.colors.text.copy(alpha = 0.33f),
        indicatorWidth = 8.dp,
        indicatorHeight = 8.dp
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