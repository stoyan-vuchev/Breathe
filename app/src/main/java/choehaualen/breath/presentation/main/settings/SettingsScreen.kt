package choehaualen.breath.presentation.main.settings

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.KeyboardArrowLeft
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import choehaualen.breath.core.etc.transformFraction
import choehaualen.breath.core.ui.components.snackbar.SnackBar
import choehaualen.breath.core.ui.components.topbar.TopBarDefaults
import choehaualen.breath.core.ui.components.topbar.basic_topbar.BasicTopBar
import choehaualen.breath.core.ui.theme.BreathTheme

@Composable
fun SettingsScreen(
    snackbarHostState: SnackbarHostState,
    onUIAction: (SettingsScreenUIAction) -> Unit
) {

    val scrollBehavior = TopBarDefaults.exitUntilCollapsedScrollBehavior()

    val bgAlpha by remember(scrollBehavior.state.collapsedFraction) {
        derivedStateOf { scrollBehavior.state.collapsedFraction }
    }

    val topBarBgAlpha by remember(bgAlpha) { // this ensures smooth color transition
        derivedStateOf {
            transformFraction(
                value = bgAlpha,
                startX = .8f,
                endX = 1f,
                startY = 0f,
                endY = 1f
            )
        }
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            // .background(BreathTheme.colors.backgroundBrush())
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        containerColor = BreathTheme.colors.background.copy(bgAlpha),
        contentColor = BreathTheme.colors.text,
        topBar = {

            BasicTopBar(
                titleText = "Settings",
                scrollBehavior = scrollBehavior,
                navigationIcon = {

                    IconButton(onClick = { onUIAction(SettingsScreenUIAction.NavigateUp) }) {

                        Icon(
                            modifier = Modifier.size(32.dp),
                            imageVector = Icons.AutoMirrored.Rounded.KeyboardArrowLeft,
                            contentDescription = "Navigate up to Home"
                        )

                    }

                },
                backgroundColor = BreathTheme.colors.background.copy(topBarBgAlpha)
            )

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

@Preview
@Composable
private fun SettingsScreenPreview() = BreathTheme {
    SettingsScreen(
        snackbarHostState = SnackbarHostState(),
        onUIAction = {}
    )
}