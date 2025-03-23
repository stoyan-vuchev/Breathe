package io.proxima.breathe.presentation.main.settings

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.KeyboardArrowLeft
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.Color
import io.proxima.breathe.R
import io.proxima.breathe.core.etc.transformFraction
import io.proxima.breathe.core.ui.components.navbar.NavBar
import io.proxima.breathe.core.ui.components.snackbar.SnackBar
import io.proxima.breathe.core.ui.components.topbar.TopBarDefaults
import io.proxima.breathe.core.ui.components.topbar.basic_topbar.BasicTopBar
import io.proxima.breathe.core.ui.theme.BreathTheme
import io.proxima.breathe.core.ui.theme.BreathDefaultColors
import io.proxima.breathe.presentation.main.settings.SettingsScreen
import dev.chrisbanes.haze.hazeChild
import dev.chrisbanes.haze.HazeStyle



@Composable
fun SettingsScreen(
    snackbarHostState: SnackbarHostState,
    isDeleteDataDialogVisible: Boolean,
    onUIAction: (SettingsScreenUIAction) -> Unit
) {
    val scrollBehavior = TopBarDefaults.exitUntilCollapsedScrollBehavior()

    val bgAlpha by remember(scrollBehavior.state.collapsedFraction) {
        derivedStateOf { scrollBehavior.state.collapsedFraction }
    }

    val topBarBgAlpha by remember(bgAlpha) {
        derivedStateOf {
            transformFraction(
                value = bgAlpha,
                startX = 0.8f,
                endX = 1f,
                startY = 0f,
                endY = 1f
            )
        }
    }
    val hazeState = remember { dev.chrisbanes.haze.HazeState() }
    Box(modifier = Modifier.fillMaxSize()) {
        // Background Image
        Image(
            painter = painterResource(id = R.drawable.backgroundfakeblur),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
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
                        toggle2Icon = painterResource(id = R.drawable.explore_fade),
                        toggle3Icon = painterResource(id = R.drawable.desk_active),
                        onToggle1Click = { onUIAction(SettingsScreenUIAction.NavigateToHome) },
                        onToggle2Click = { onUIAction(SettingsScreenUIAction.NavigateToExplore) },
                        onToggle3Click = {  }
                    )
                }
            },
            snackbarHost = {
                SnackbarHost(
                    hostState = snackbarHostState,
                    snackbar = { SnackBar(it) }
                )
            }
        ) { insetsPadding ->
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = insetsPadding
            ) {
                settingsScreenMainCategory(onUIAction = onUIAction)
                settingsScreenOtherCategory(onUIAction = onUIAction)
            }
        }
    }

    SettingsScreenDeleteDataDialog(
        isVisible = isDeleteDataDialogVisible,
        onUIAction = onUIAction
    )
}

//@Preview(showBackground = true)
//@Composable
//private fun SettingsScreenPreview() = BreathTheme {
//    SettingsScreen(
//        snackbarHostState = SnackbarHostState(),
//        isDeleteDataDialogVisible = false,
//        onUIAction = {}
//    )
//}
