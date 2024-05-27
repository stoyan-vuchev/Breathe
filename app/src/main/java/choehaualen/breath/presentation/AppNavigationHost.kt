package choehaualen.breath.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import choehaualen.breath.R
import choehaualen.breath.core.etc.transformFraction
import choehaualen.breath.core.ui.NavHostTransitions
import choehaualen.breath.core.ui.components.topbar.day_view_topbar.DayViewTopBar
import choehaualen.breath.core.ui.components.topbar.day_view_topbar.DayViewTopBarDefaults
import choehaualen.breath.core.ui.navigateSingleTop
import choehaualen.breath.core.ui.theme.BreathTheme
import choehaualen.breath.presentation.boarding.BoardingNavigationDestination
import choehaualen.breath.presentation.boarding.boardingNavigationGraph
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.HazeStyle
import dev.chrisbanes.haze.haze
import dev.chrisbanes.haze.hazeChild

@Composable
fun AppNavigationHost(
    navController: NavHostController,
    isUserAuthenticated: Boolean?,
    onAuthenticateUser: () -> Unit
) {

    NavHost(
        modifier = Modifier
            .fillMaxSize()
            .background(BreathTheme.colors.background),
        navController = navController,
        startDestination = "start_route",
        enterTransition = NavHostTransitions.enterTransition,
        exitTransition = NavHostTransitions.exitTransition,
        popEnterTransition = NavHostTransitions.popEnterTransition,
        popExitTransition = NavHostTransitions.popExitTransition
    ) {

        composable(
            route = "start_route",
            content = {

                LaunchedEffect(isUserAuthenticated) {
                    isUserAuthenticated?.let { isAuthenticated ->
                        navController.navigateSingleTop(
                            route = if (isAuthenticated) "main_screen_route"
                            else BoardingNavigationDestination.Welcome.route,
                            popUpTo = "start_route"
                        )
                    } ?: onAuthenticateUser()
                }

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(BreathTheme.colors.background)
                        .systemBarsPadding(),
                    contentAlignment = Alignment.Center
                ) {

                    Image(
                        painter = painterResource(id = R.drawable.splash_logo),
                        contentDescription = "Breath Logo"
                    )

                }

            }
        )

        boardingNavigationGraph(
            navController = navController
        )

        composable(
            route = "main_screen_route",
            content = {

                val scrollBehavior = DayViewTopBarDefaults.exitUntilCollapsedScrollBehavior()

                val bgAlpha by remember(scrollBehavior.state.collapsedFraction) {
                    derivedStateOf { scrollBehavior.state.collapsedFraction }
                }

                val gradientAlpha by remember(bgAlpha) {
                    derivedStateOf { 1f - bgAlpha }
                }

                val topBarBgAlpha by remember(gradientAlpha) {
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
                        .background(BreathTheme.colors.background)
                        .background(background)
                        .nestedScroll(scrollBehavior.nestedScrollConnection),
                    containerColor = Color.Unspecified,
                    contentColor = BreathTheme.colors.text,
                    topBar = {

                        // but we need to extract the date info first :)

                        DayViewTopBar(
                            modifier = Modifier.hazeChild(
                                state = hazeState,
                                style = HazeStyle(
                                    tint = BreathTheme.colors.background.copy(topBarBgAlpha),
                                    blurRadius = 20.dp,
                                    noiseFactor = 1f
                                )
                            ),
                            actions = {

                                IconButton(
                                    onClick = { /*TODO*/ }
                                ) {

                                    Icon(
                                        imageVector = Icons.Rounded.Settings,
                                        contentDescription = null
                                    )

                                }

                            },
                            scrollBehavior = scrollBehavior
                        )

                    }
                ) { insetsPadding ->

                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .haze(hazeState),
                        contentPadding = insetsPadding
                    ) {

                        items(
                            count = 100,
                            key = { "item_$it" }
                        ) {

                            Text(
                                modifier = Modifier.padding(16.dp),
                                text = "Item: ${it + 1}"
                            )

                        }

                    }

                }

            }
        )

    }

}