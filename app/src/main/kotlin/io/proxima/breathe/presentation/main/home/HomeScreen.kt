package io.proxima.breathe.presentation.main.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import dev.chrisbanes.haze.HazeStyle
import dev.chrisbanes.haze.hazeChild
import io.proxima.breathe.R
import io.proxima.breathe.core.etc.Result
import io.proxima.breathe.core.etc.transformFraction
import io.proxima.breathe.core.ui.components.navbar.NavBar
import io.proxima.breathe.core.ui.components.rememberBreathRipple
import io.proxima.breathe.core.ui.components.snackbar.SnackBar
import io.proxima.breathe.core.ui.components.topbar.TopBarDefaults
import io.proxima.breathe.core.ui.components.topbar.day_view_topbar.DayViewTopBar
import io.proxima.breathe.core.ui.theme.BreathDefaultColors
import io.proxima.breathe.core.ui.theme.BreathTheme
import io.proxima.breathe.data.preferences.AppPreferences
import io.proxima.breathe.domain.model.QuoteModel
import io.proxima.breathe.presentation.main.pomodoro.PomodoroViewModel
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(
    screenState: HomeScreenState,
    snackBarHostState: SnackbarHostState,
    onUIAction: (HomeScreenUIAction) -> Unit
) {
    val pomodoroViewModel: PomodoroViewModel = hiltViewModel()
    val homeViewModel: HomeScreenViewModel = hiltViewModel()
    val appPreferences = homeViewModel.appPreferences

    val isFocusSession by pomodoroViewModel.isFocusSession.collectAsState()
    val timer by pomodoroViewModel.currentTimer.collectAsState()
    val pomodoroCount by pomodoroViewModel.pomodoroCount.collectAsState()

    // Fetch username for HomeScreen use
    var username by remember { mutableStateOf("Guest") }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        coroutineScope.launch {
            val result = appPreferences.getUser()
            if (result is Result.Success) {
                username = result.data ?: "Guest"
            }
        }
    }

    val topBarScrollBehavior = TopBarDefaults.exitUntilCollapsedScrollBehavior()
    val bgAlpha by remember(topBarScrollBehavior.state.collapsedFraction) {
        derivedStateOf { topBarScrollBehavior.state.collapsedFraction }
    }
    val topBarBgAlpha by remember(bgAlpha) {
        derivedStateOf {
            transformFraction(bgAlpha, 0.8f, 1f, 0f, 0.5f)
        }
    }
    val hazeState = remember { dev.chrisbanes.haze.HazeState() }

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.backgroundfakeblur),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        Scaffold(
            modifier = Modifier
                .fillMaxSize()
                .nestedScroll(topBarScrollBehavior.nestedScrollConnection),
            containerColor = Color.Transparent,
            contentColor = BreathDefaultColors.background,
            topBar = {
                DayViewTopBar(
                    modifier = Modifier.hazeChild(
                        state = hazeState,
                        style = HazeStyle(
                            tint = BreathTheme.colors.background.copy(topBarBgAlpha),
                            blurRadius = 20.dp
                        )
                    ),
                    appPreferences = appPreferences,
                    scrollBehavior = topBarScrollBehavior
                )
            },
            bottomBar = {
                Box(
                    modifier = Modifier.hazeChild(
                        state = hazeState,
                        style = HazeStyle(
                            tint = BreathTheme.colors.background.copy(alpha = 0.2f),
                            blurRadius = 20.dp
                        )
                    )
                ) {
                    NavBar(
                        toggle1Text = "Home",
                        toggle2Text = "Explore",
                        toggle3Text = "Profile",
                        toggle1Icon = painterResource(id = R.drawable.home_active),
                        toggle2Icon = painterResource(id = R.drawable.explore_fade),
                        toggle3Icon = painterResource(id = R.drawable.desk_fade),
                        onToggle1Click = {},
                        onToggle2Click = { onUIAction(HomeScreenUIAction.NavigateToExplore) },
                        onToggle3Click = { onUIAction(HomeScreenUIAction.NavigateToSettings()) }
                    )
                }
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
                    .padding(insetsPadding),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {

                // Suggested toggles
                item(key = "suggested_header") {
                    Text(
                        text = "Suggested",
                        modifier = Modifier.padding(start = 36.dp),
                        style = BreathTheme.typography.titleMedium
                    )
                }
                item(key = "suggested_rows") {
                    Column(modifier = Modifier.fillMaxWidth()) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 36.dp),
                            horizontalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .clip(RoundedCornerShape(23.dp))
                                    .background(Color.White.copy(alpha = 0.3f))
                                    .clickable { onUIAction(HomeScreenUIAction.NavigateToPomodoro()) }
                                    .padding(vertical = 16.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    Text(
                                        text = (if (isFocusSession) "Focus Session" else "Break Time") +
                                                String.format(" :  %02d m : %02d s", timer / 60, timer % 60) + ", Count $pomodoroCount",
                                        textAlign = TextAlign.Center
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(10.dp))
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 36.dp),
                            horizontalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .clip(RoundedCornerShape(23.dp))
                                    .background(Color.White.copy(alpha = 0.3f))
                                    .clickable { onUIAction(HomeScreenUIAction.NavigateToFitness()) }
                                    .padding(vertical = 16.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(text = "Remember to keep fitness on track", textAlign = TextAlign.Center)
                            }
                        }
                    }
                }

                // Quick toggles
                item(key = "quick_access_header") {
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = "Quick access",
                        modifier = Modifier.padding(start = 36.dp),
                        style = BreathTheme.typography.titleMedium
                    )
                }
                item(key = "quick_access_rows") {
                    Column(modifier = Modifier.fillMaxWidth()) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 36.dp),
                            horizontalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .clip(RoundedCornerShape(23.dp))
                                    .background(Color.White.copy(alpha = 0.3f))
                                    .clickable { onUIAction(HomeScreenUIAction.NavigateToBreathe()) }
                                    .padding(vertical = 16.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(text = "Mindfulness", textAlign = TextAlign.Center)
                            }
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .clip(RoundedCornerShape(23.dp))
                                    .background(Color.White.copy(alpha = 0.3f))
                                    .clickable { onUIAction(HomeScreenUIAction.NavigateToSoundscapeFilter()) }
                                    .padding(vertical = 16.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(text = "Soundscape", textAlign = TextAlign.Center)
                            }
                        }
                        Spacer(modifier = Modifier.height(10.dp))
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 36.dp),
                            horizontalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .clip(RoundedCornerShape(23.dp))
                                    .background(Color.White.copy(alpha = 0.3f))
                                    .clickable { onUIAction(HomeScreenUIAction.NavigateToPomodoro()) }
                                    .padding(vertical = 16.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(text = "Focus Assist", textAlign = TextAlign.Center)
                            }
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .clip(RoundedCornerShape(23.dp))
                                    .background(Color.White.copy(alpha = 0.3f))
                                    .clickable { onUIAction(HomeScreenUIAction.NavigateToHabitControl()) }
                                    .padding(vertical = 16.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(text = "A - H - C", textAlign = TextAlign.Center)
                            }
                        }
                    }
                }

                // Quotes Section
                quotesItem(quote = screenState.quote, onUIAction = onUIAction)
            }
        }
    }
}

private fun LazyListScope.quotesItem(
    quote: QuoteModel,
    onUIAction: (HomeScreenUIAction) -> Unit
) = item(key = "daily_quote") {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberBreathRipple(BreathTheme.colors.primarySoul),
                onClick = { onUIAction(HomeScreenUIAction.ExpandQuote) }
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HorizontalDivider(
            modifier = Modifier
                .fillMaxWidth()
                .padding(26.dp)
                .clip(RoundedCornerShape(50)),
            color = BreathDefaultColors.background
        )
        Text(
            modifier = Modifier.padding(horizontal = 20.dp),
            text = "“${quote.quote}”",
            style = BreathTheme.typography.labelMedium,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 64.dp),
            text = "- ${quote.author}",
            style = BreathTheme.typography.labelMedium,
            textAlign = TextAlign.End
        )
    }
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
