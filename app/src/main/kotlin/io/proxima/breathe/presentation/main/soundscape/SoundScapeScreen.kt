package io.proxima.breathe.presentation.main.soundscape

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.KeyboardArrowLeft
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.proxima.breathe.core.etc.transformFraction
import io.proxima.breathe.core.ui.theme.DreamyNightColors
import io.proxima.breathe.core.ui.theme.ProvideBreathColors
import io.proxima.breathe.core.ui.theme.backgroundBrush
import io.proxima.breathe.core.ui.components.topbar.TopBarDefaults
import io.proxima.breathe.core.ui.components.topbar.basic_topbar.BasicTopBar
import io.proxima.breathe.core.ui.theme.BreathTheme
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.HazeStyle
import dev.chrisbanes.haze.haze
import dev.chrisbanes.haze.hazeChild
import sv.lib.squircleshape.SquircleShape

@Composable
fun SoundScapeScreen(
    screenState: SoundscapeScreenState,
    onUIAction: (SoundscapeUIAction) -> Unit
) {

    val scrollBehavior = TopBarDefaults.exitUntilCollapsedScrollBehavior()
    val lazyGridState = rememberLazyGridState()
    val topBarBgAlpha by remember(scrollBehavior.state.collapsedFraction) { // this ensures smooth color transition
        derivedStateOf {
            transformFraction(
                value = scrollBehavior.state.collapsedFraction,
                startX = .8f,
                endX = 1f,
                startY = 0f,
                endY = .1f
            )
        }
    }

    val hazeState = remember { HazeState() }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(BreathTheme.colors.backgroundBrush())
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        containerColor = Color.Unspecified,
        contentColor = BreathTheme.colors.text,
        topBar = {

            BasicTopBar(
                modifier = Modifier.hazeChild(
                    state = hazeState,
                    style = HazeStyle(
                        tint = BreathTheme.colors.background.copy(topBarBgAlpha),
                        blurRadius = 20.dp
                    )
                ),
                titleText = "Soundscape",
                scrollBehavior = scrollBehavior,
                navigationIcon = {

                    IconButton(
                        onClick = { onUIAction(SoundscapeUIAction.NavigateUp) }
                    ) {

                        Icon(
                            modifier = Modifier.size(32.dp),
                            imageVector = Icons.AutoMirrored.Rounded.KeyboardArrowLeft,
                            contentDescription = "Navigate back to Home."
                        )

                    }

                },
                backgroundColor = BreathTheme.colors.background.copy(topBarBgAlpha)
            )

        }
    ) { insetsPadding ->

        LazyVerticalGrid(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
                .haze(hazeState),
            columns = GridCells.Fixed(2),
            contentPadding = insetsPadding,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            state = lazyGridState
        ) {

            items(
                count = 100,
                key = { "item_$it" }
            ) {

                val context = LocalContext.current

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1f)
                        .clip(SquircleShape(12.dp))
                        .background(BreathTheme.colors.card)
                        .clickable {

//                            val intent = Intent(
//                                context,
//                                SoundscapePlaybackService::class.java
//                            ).apply {
//
//                                putExtra(
//                                    "uri",
//                                    context.uriFromResource(R.raw.midnight).path
//                                )
//
//                                putExtra(
//                                    "image",
//                                    context.uriFromResource(R.drawable.).path
//                                )
//
//                                putExtra(
//                                    "title",
//                                    "Midnight"
//                                )
//
//                            }
//
//                            context.startService(intent)

                        }
                )

            }

        }

        Box(modifier = Modifier.fillMaxSize())

    }

}

@Preview
@Composable
private fun SoundScapeScreenPreview() = BreathTheme {
    ProvideBreathColors(DreamyNightColors) {
        SoundScapeScreen(
            screenState = SoundscapeScreenState(),
            onUIAction = {}
        )
    }
}
// i think its smth to do with the player start
// yes, let's see