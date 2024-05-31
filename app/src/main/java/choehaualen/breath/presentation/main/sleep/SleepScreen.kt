package choehaualen.breath.presentation.main.sleep

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.automirrored.rounded.KeyboardArrowLeft
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import choehaualen.breath.core.ui.colors.backgroundBrush
import choehaualen.breath.core.ui.components.topbar.TopBarDefaults
import choehaualen.breath.core.ui.components.topbar.basic_topbar.BasicTopBar
import choehaualen.breath.core.ui.theme.BreathTheme

@Composable
fun SleepScreen(
    onUIAction: (SleepScreenUIAction) -> Unit
) {

    val lazyListState = rememberLazyListState()
    val scrollBehavior = TopBarDefaults.exitUntilCollapsedScrollBehavior()

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(BreathTheme.colors.backgroundBrush())
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

                    // Let's make the goal set logic first.
                    // We can store it in the datastore preferences
                    // And use it anywhere. Wait I have to go to the toilet for a moment.
                    // brb.okay, oke I'm back
                    // But first, let me play some motivational tracks.
                    // So I can focus lol yeah. Okeh, let's go

                }
            )

        }
    ) { insetsPadding ->

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            state = lazyListState,
            contentPadding = insetsPadding
        ) {

            // todo

        }

    }

}