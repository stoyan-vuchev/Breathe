package io.proxima.breathe.presentation.main.soundscape

import android.view.animation.OvershootInterpolator
import androidx.annotation.OptIn
import androidx.annotation.RawRes
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.KeyboardArrowLeft
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.media3.common.util.UnstableApi
import coil.compose.AsyncImage
import coil.request.ImageRequest
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.HazeStyle
import dev.chrisbanes.haze.haze
import dev.chrisbanes.haze.hazeChild
import io.proxima.breathe.R
import io.proxima.breathe.core.etc.transformFraction
import io.proxima.breathe.core.ui.components.rememberBreathRipple
import io.proxima.breathe.core.ui.components.topbar.TopBarDefaults
import io.proxima.breathe.core.ui.components.topbar.basic_topbar.BasicTopBar
import io.proxima.breathe.core.ui.theme.BreathTheme
import io.proxima.breathe.core.ui.theme.DreamyNightColors
import io.proxima.breathe.core.ui.theme.ProvideBreathColors
import io.proxima.breathe.core.ui.theme.backgroundBrush
import io.proxima.breathe.domain.model.soundScapeItemsList
import sv.lib.squircleshape.SquircleShape

@OptIn(UnstableApi::class)
@Composable
fun SoundScapeScreen(
    screenState: SoundscapeScreenState,
    onUIAction: (SoundscapeUIAction) -> Unit
) {

    val scrollBehavior = TopBarDefaults.exitUntilCollapsedScrollBehavior()
    val topBarBgAlpha by remember(scrollBehavior.state.collapsedFraction) { // this ensures smooth color transition
        derivedStateOf {
            transformFraction(
                value = scrollBehavior.state.collapsedFraction,
                startX = .8f,
                endX = 1f,
                startY = 0f,
                endY = .33f
            )
        }
    }

    val hazeState = remember { HazeState() }

    @RawRes
    val currentlyPlaying by rememberUpdatedState(
        screenState.currentMediaItem
            ?.mediaMetadata
            ?.extras
            ?.getInt("audioSrc")
    )

    DisposableEffect(Unit) {
        onDispose { onUIAction(SoundscapeUIAction.PausePlayback) }
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(BreathTheme.colors.backgroundBrush()),
        containerColor = Color.Unspecified,
        contentColor = BreathTheme.colors.text,
        topBar = {

            BasicTopBar(
                modifier = Modifier.hazeChild(
                    state = hazeState,
                    style = HazeStyle(
                        tint = BreathTheme.colors.backgroundGradientStart.copy(topBarBgAlpha),
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
                backgroundColor = BreathTheme.colors.backgroundGradientStart.copy(topBarBgAlpha)
            )

        },
    ) { insetsPadding ->

        Box(
            modifier = Modifier.fillMaxSize()
        ) {

            LazyVerticalGrid(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
                    .haze(hazeState)
                    .nestedScroll(scrollBehavior.nestedScrollConnection),
                columns = GridCells.Fixed(2),
                contentPadding = insetsPadding,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {

                item(
                    key = "item_top_spacer",
                    span = { GridItemSpan(2) },
                    content = { Spacer(modifier = Modifier.height(8.dp)) }
                )

                items(
                    items = soundScapeItemsList,
                    key = { "item_${it.id}" },
                    itemContent = { soundscapeItem ->

                        val isItemPlaying by rememberUpdatedState(
                            currentlyPlaying == soundscapeItem.audioSrc
                                    && screenState.isPlaying
                        )

                        SoundScapeScreenItem(
                            soundscapeItem = soundscapeItem,
                            isPlaying = isItemPlaying,
                            onUIAction = onUIAction
                        )

                    }
                )

                item(
                    key = "item_bottom_spacer",
                    span = { GridItemSpan(2) },
                    content = { Spacer(modifier = Modifier.height(32.dp)) }
                )

            }

            AnimatedVisibility(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(256.dp)
                    .align(Alignment.BottomCenter),
                visible = screenState.isPlaying,
                enter = slideInVertically(
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioLowBouncy,
                        stiffness = Spring.StiffnessLow
                    )
                ) { it } + fadeIn() + scaleIn(initialScale = .5f),
                exit = slideOutVertically(
                    animationSpec = tween()
                ) { it } + fadeOut() + scaleOut(targetScale = .5f),
                label = ""
            ) {

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                ) {

                    Column(
                        modifier = Modifier
                            .navigationBarsPadding()
                            .padding(bottom = 32.dp)
                            .padding(horizontal = 64.dp)
                            .shadow(
                                elevation = 8.dp,
                                shape = SquircleShape(),
                                ambientColor = BreathTheme.colors.secondarySoul,
                                spotColor = BreathTheme.colors.secondarySoul,
                                clip = false
                            )
                            .clip(SquircleShape())
                            .background(BreathTheme.colors.card)
                            .padding(16.dp)
                            .align(Alignment.BottomCenter)
                    ) {

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(20.dp)
                        ) {

                            AnimatedContent(
                                targetState = screenState.currentMediaItem?.mediaMetadata?.artworkUri,
                                label = ""
                            ) { uri ->

                                AsyncImage(
                                    modifier = Modifier
                                        .size(48.dp)
                                        .clip(SquircleShape())
                                        .border(
                                            color = BreathTheme.colors.text,
                                            shape = SquircleShape(),
                                            width = 1.dp
                                        ),
                                    model = ImageRequest.Builder(LocalContext.current)
                                        .data(uri)
                                        .crossfade(true)
                                        .build(),
                                    contentDescription = null,
                                    contentScale = ContentScale.Crop
                                )

                            }

                            AnimatedContent(
                                modifier = Modifier
                                    .padding(end = 20.dp),
                                targetState = screenState.currentMediaItem?.mediaMetadata?.title,
                                label = ""
                            ) { title ->

                                Text(
                                    modifier = Modifier.width(IntrinsicSize.Min),
                                    text = title?.toString() ?: "Playing Now",
                                    style = BreathTheme.typography.labelLarge,
                                    color = BreathTheme.colors.text,
                                    textAlign = TextAlign.Start
                                )

                            }

                            Box(
                                modifier = Modifier
                                    .size(48.dp)
                                    .clickable(
                                        indication = rememberBreathRipple(),
                                        interactionSource = remember { MutableInteractionSource() },
                                        onClick = {
                                            if (screenState.isPlaying) {
                                                onUIAction(SoundscapeUIAction.PausePlayback)
                                            } else SoundscapeUIAction.PlayOrPauseSound(
                                                currentlyPlaying
                                            )
                                        }
                                    )
                                    .clip(SquircleShape(24.dp))
                                    .background(BreathTheme.colors.secondarySoul),
                                contentAlignment = Alignment.Center
                            ) {

                                AsyncImage(
                                    modifier = Modifier.size(32.dp),
                                    model = ImageRequest.Builder(LocalContext.current)
                                        .data(
                                            if (screenState.isPlaying) R.drawable.round_pause_24
                                            else R.drawable.round_play_arrow_24
                                        )
                                        .crossfade(true)
                                        .build(),
                                    contentDescription = null,
                                    colorFilter = ColorFilter.tint(BreathTheme.colors.background)
                                )

                            }

                        }

                    }

                }

            }

        }

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