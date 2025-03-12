package io.proxima.breathe.presentation.main.home

//import android.content.Intent
//import androidx.compose.animation.core.Spring
//import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
//import androidx.compose.foundation.pager.HorizontalPager
//import androidx.compose.foundation.pager.PagerDefaults
//import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
//import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
//import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.shape.RoundedCornerShape
//import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.haze
import dev.chrisbanes.haze.HazeStyle
import dev.chrisbanes.haze.hazeChild
import io.proxima.breathe.core.etc.transformFraction
//import io.proxima.breathe.core.ui.carouselTransition
//import io.proxima.breathe.core.ui.components.HorizontalPagerIndicator
//import io.proxima.breathe.core.ui.fadingEdges
import io.proxima.breathe.core.ui.components.rememberBreathRipple
import io.proxima.breathe.core.ui.components.snackbar.SnackBar
import io.proxima.breathe.core.ui.components.topbar.TopBarDefaults
import io.proxima.breathe.core.ui.components.topbar.day_view_topbar.DayViewTopBar
import io.proxima.breathe.core.ui.theme.BreathDefaultColors
import io.proxima.breathe.core.ui.theme.BreathTheme
import io.proxima.breathe.domain.model.QuoteModel
import sv.lib.squircleshape.SquircleShape
import io.proxima.breathe.R

// ---------------------- NavBar Composable with blur ----------------------
@Composable
fun NavBar(
    modifier: Modifier = Modifier,
    toggle1Text: String,
    toggle2Text: String,
    toggle3Text: String,
    toggle1Icon: Painter? = null,
    toggle2Icon: Painter? = null,
    toggle3Icon: Painter? = null,
    onToggle1Click: () -> Unit,
    onToggle2Click: () -> Unit,
    onToggle3Click: () -> Unit
) {



    val hazeState = remember { dev.chrisbanes.haze.HazeState() }
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(Color.White.copy(alpha = 0.2f))
            .hazeChild(
                state = hazeState,
                style = HazeStyle(
                    blurRadius = 60.dp,
                    tint = Color.White.copy(alpha = 0.2f)

                )
            )
            .padding(vertical = 24.dp)
    )
    {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            NavBarItem(icon = toggle1Icon, text = toggle1Text, onClick = onToggle1Click)
            NavBarItem(icon = toggle2Icon, text = toggle2Text, onClick = onToggle2Click)
            NavBarItem(icon = toggle3Icon, text = toggle3Text, onClick = onToggle3Click)
        }
    }
}

@Composable
fun NavBarItem(
    icon: Painter? = null,
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.clickable { onClick() },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        if (icon != null) {
            Image(
                painter = icon,
                contentDescription = null,
                modifier = Modifier.size(24.dp)
            )
        }
        Text(text = text)
    }
}

// ---------------------- HomeScreen Composable ----------------------
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
    val topBarBgAlpha by remember(bgAlpha) {
        derivedStateOf {
            transformFraction(
                value = bgAlpha,
                startX = 0.8f,
                endX = 1f,
                startY = 0f,
                endY = 0.5f
            )
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
                    scrollBehavior = topBarScrollBehavior,
                    actions = {
                        IconButton(onClick = { onUIAction(HomeScreenUIAction.NavigateToSettings()) }) {
                            Icon(
                                imageVector = Icons.Rounded.Settings,
                                contentDescription = "Settings"
                            )
                        }
                    }
                )
            },
            bottomBar = {
                // Fixed NavBar at the very bottom.
                NavBar(
                    toggle1Text = "Home",
                    toggle2Text = "Explore",
                    toggle3Text = "Profile",
                    toggle1Icon = painterResource(id = R.drawable.happy_face),
                    toggle2Icon = painterResource(id = R.drawable.happy_face),
                    toggle3Icon = painterResource(id = R.drawable.happy_face),
                    onToggle1Click = {  },
                    onToggle2Click = {  },
                    onToggle3Click = {  }
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
                // Order: first toggles (HorizontalPager), then quotes.
                togglesItem(onUIAction = onUIAction)
                quotesItem(
                    quote = screenState.quote,
                    onUIAction = onUIAction
                )
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
        Spacer(modifier = Modifier.height(0.dp))
        HorizontalDivider(
            modifier = Modifier
                .fillMaxWidth()
                .padding(36.dp)
                .clip(RoundedCornerShape(50)),
            color = BreathDefaultColors.background
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            modifier = Modifier.padding(horizontal = 20.dp),
            text = "“${quote.quote}”",
            style = BreathTheme.typography.labelMedium,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 64.dp),
            text = "- ${quote.author}",
            style = BreathTheme.typography.labelMedium,
            textAlign = TextAlign.End
        )
        Spacer(modifier = Modifier.height(8.dp))
    }
}

private fun LazyListScope.togglesItem(
    onUIAction: (HomeScreenUIAction) -> Unit
) = item(key = "toggles") {
    HomeScreenGridPager(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp)
            .clip(SquircleShape(40.dp))
            .background(BreathDefaultColors.background.copy(alpha = 0.2f)),
        state = rememberPagerState { 2 },
        onUIAction = onUIAction
    )
}

//@Composable
//fun HomeScreenGridPager(
//    modifier: Modifier = Modifier,
//    state: PagerState,
//    onUIAction: (HomeScreenUIAction) -> Unit,
//) = Column(
//    modifier = modifier
//        .fadingEdges(
//            startOffset = 16.dp,
//            endOffset = 16.dp,
//            minFade = 0.9f
//        )
//        .padding(bottom = 16.dp),
//    horizontalAlignment = Alignment.CenterHorizontally
//) {
//    HorizontalPager(
//        modifier = Modifier.fillMaxWidth(),
//        state = state,
//        key = { "page_$it" },
//        flingBehavior = PagerDefaults.flingBehavior(
//            state = state,
//            snapAnimationSpec = spring(
//                stiffness = Spring.StiffnessVeryLow,
//                dampingRatio = Spring.DampingRatioLowBouncy
//            )
//        ),
//        pageContent = { page ->
//            when (page) {
//                0 -> Column(
//                    modifier = Modifier.atAGlancePageModifier(itemPage = 0, state = state)
//                ) {
//                    Row(
//                        modifier = Modifier.fillMaxWidth(),
//                        horizontalArrangement = Arrangement.spacedBy(16.dp)
//                    ) {
//                        HomeScreenToggle(
//                            modifier = Modifier.weight(1f),
//                            icon = painterResource(id = R.drawable.nulltoggleoverlay),
//                            label = "Stress",
//                            background = R.drawable.stressback,
//                            onClick = { onUIAction(HomeScreenUIAction.NavigateToBreathe()) }
//                        )
//                        HomeScreenToggle(
//                            modifier = Modifier.weight(1f),
//                            icon = painterResource(id = R.drawable.nulltoggleoverlay),
//                            label = "Productivity",
//                            background = R.drawable.productivity,
//                            onClick = { onUIAction(HomeScreenUIAction.NavigateToProductivity()) }
//                        )
//                    }
//                    Spacer(modifier = Modifier.height(16.dp))
//                    Row(
//                        modifier = Modifier.fillMaxWidth(),
//                        horizontalArrangement = Arrangement.spacedBy(16.dp)
//                    ) {
//                        HomeScreenToggle(
//                            modifier = Modifier.weight(1f),
//                            icon = painterResource(id = R.drawable.nulltoggleoverlay),
//                            label = "Soundscape",
//                            background = R.drawable.soundscape,
//                            onClick = { onUIAction(HomeScreenUIAction.NavigateToSoundscape()) }
//                        )
//                        HomeScreenToggle(
//                            modifier = Modifier.weight(1f),
//                            icon = painterResource(id = R.drawable.nulltoggleoverlay),
//                            boundlessIcon = true,
//                            label = "Habit Control",
//                            background = R.drawable.zone,
//                            onClick = { onUIAction(HomeScreenUIAction.NavigateToHabitControl()) }
//                        )
//                    }
//                }
//            }
//        }
//    )
//    Spacer(modifier = Modifier.height(0.dp))
//    HorizontalPagerIndicator(
//        pagerState = state,
//        pageCount = state.pageCount,
//        activeColor = BreathTheme.colors.text,
//        inactiveColor = BreathTheme.colors.text.copy(alpha = 0.1f),
//        indicatorWidth = 0.dp,
//        indicatorHeight = 0.dp
//    )
//}
//
//private fun Modifier.atAGlancePageModifier(
//    itemPage: Int,
//    state: PagerState
//) = fillMaxWidth()
//    .carouselTransition(
//        itemPage = itemPage,
//        pagerState = state
//    )
//    .padding(16.dp)

@Composable
fun HomeScreenToggle(
    modifier: Modifier = Modifier,
    icon: Painter,
    label: String,
    boundlessIcon: Boolean = false,
    boundlessIconSize: DpSize = DpSize.Unspecified,
    background: Int, // image resource id for background
    onClick: () -> Unit
) {
    Column(
        modifier = modifier.clickable(
            interactionSource = remember { MutableInteractionSource() },
            indication = rememberBreathRipple(BreathTheme.colors.background),
            onClick = onClick
        ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Box(
            modifier = Modifier
                .clip(SquircleShape(24.dp))
                .aspectRatio(1f)
                .fillMaxWidth()
        ) {
            // Background image
            Image(
                painter = painterResource(id = background),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.matchParentSize()
            )
            // Foreground icon
            Image(
                painter = icon,
                contentDescription = label,
                modifier = Modifier
                    .align(Alignment.Center)
                    .then(
                        if (!boundlessIcon) Modifier.width(56.dp)
                        else Modifier
                            .defaultMinSize(minWidth = 74.dp)
                            .size(boundlessIconSize)
                    )
            )
        }
        Text(
            text = label,
            style = BreathTheme.typography.labelMedium,
            textAlign = TextAlign.Center,
            color = BreathDefaultColors.background,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
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
