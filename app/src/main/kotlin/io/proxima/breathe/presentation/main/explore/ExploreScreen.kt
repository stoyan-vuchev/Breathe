package io.proxima.breathe.presentation.main.explore

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.chrisbanes.haze.HazeStyle
import dev.chrisbanes.haze.hazeChild
import dev.chrisbanes.haze.haze
import io.proxima.breathe.R
import io.proxima.breathe.core.ui.components.navbar.NavBar
import io.proxima.breathe.core.ui.components.snackbar.SnackBar
import io.proxima.breathe.core.ui.theme.BreathDefaultColors
import io.proxima.breathe.core.ui.theme.BreathTheme

@Composable
fun ExploreScreen(
    snackBarHostState: SnackbarHostState,
    onUIAction: (ExploreScreenUIAction) -> Unit
) {
    val hazeState = remember { dev.chrisbanes.haze.HazeState() }

    // ✅ Fetch tiles from getExploreTiles()
    val sampleTiles = getExploreTiles(onUIAction)

    Box(modifier = Modifier.fillMaxSize()) {
        // Background image fills the entire screen
        Image(
            painter = painterResource(id = R.drawable.backgroundfakeblur),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        // Scaffold overlays the background
        Scaffold(
            modifier = Modifier
                .fillMaxSize()
                .navigationBarsPadding(),
            containerColor = Color.Transparent,
            contentColor = BreathDefaultColors.background,
            bottomBar = {
                // Wrap NavBar with a haze effect for a blurred background
                Box(
                    modifier = Modifier
                        .hazeChild(
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
                        toggle1Icon = painterResource(id = R.drawable.home_fade),
                        toggle2Icon = painterResource(id = R.drawable.explore_active),
                        toggle3Icon = painterResource(id = R.drawable.desk_fade),
                        onToggle1Click = { onUIAction(ExploreScreenUIAction.NavigateToHome) },
                        onToggle2Click = { },
                        onToggle3Click = { onUIAction(ExploreScreenUIAction.NavigateToSettings) }
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
            // Apply hazeSource to the main content to enable blurring behind hazeChild
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .haze(state = hazeState) // ✅ Ensure that blur applies properly
            ) {
                ExploreScreenWithTiles(
                    tiles = sampleTiles,
                    modifier = Modifier.padding(insetsPadding),
                    onUIAction = onUIAction
                )
            }
        }
    }
}



@Preview(showBackground = true)
@Composable
fun ExploreScreenPreview() {
    ExploreScreen(
        snackBarHostState = remember { SnackbarHostState() },
        onUIAction = {}
    )
}
