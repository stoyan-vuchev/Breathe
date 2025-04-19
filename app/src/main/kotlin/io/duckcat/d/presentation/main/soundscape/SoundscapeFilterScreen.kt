package io.duckcat.d.presentation.main.soundscape

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import io.duckcat.d.R
import io.duckcat.d.core.ui.components.rememberBreathRipple
import io.duckcat.d.core.ui.theme.BreathTheme
import io.duckcat.d.domain.model.soundScapeItemsList

@Composable
fun SoundscapeFilterScreenContent(
    screenState: SoundscapeScreenState,
    onUIAction: (SoundscapeUIAction) -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {
        // Background Image Layer
        Image(
            painter = painterResource(id = R.drawable.soundscape_bg),
            contentDescription = "Background",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        // Foreground Content without outer verticalScroll:
        // The header stays fixed while the LazyVerticalGrid scrolls on its own.
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Spacer(modifier = Modifier.height(45.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                ToggleButton(
                    text = "Enhance Sleep",
                    selected = screenState.selectedMood == "Enhance Sleep"
                ) { onUIAction(SoundscapeUIAction.MoodSelected("Enhance Sleep")) }
                ToggleButton(
                    text = "Focus",
                    selected = screenState.selectedMood == "Focus"
                ) { onUIAction(SoundscapeUIAction.MoodSelected("Focus")) }
                ToggleButton(
                    text = "Sad",
                    selected = screenState.selectedMood == "Sad"
                ) { onUIAction(SoundscapeUIAction.MoodSelected("Sad")) }
            }
            // LazyVerticalGrid handles its own scrolling.
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(screenState.filteredSounds, key = { it.id }) { item ->
                    SoundScapeScreenItem(
                        soundscapeItem = item,
                        isPlaying = false,
                        onUIAction = onUIAction
                    )
                }
            }
        }
        // Playback Feedback: Animated Visibility block at bottom of the screen.
        AnimatedVisibility(
            modifier = Modifier
                .fillMaxWidth()
                .height(256.dp)
                .align(Alignment.BottomCenter),
            visible = screenState.isPlaying,
            enter = slideInVertically { it } + fadeIn() + scaleIn(initialScale = 0.5f),
            exit = slideOutVertically { it } + fadeOut() + scaleOut(targetScale = 0.5f),
            label = "PlaybackFeedback"
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                Column(
                    modifier = Modifier
                        .navigationBarsPadding()
                        .padding(bottom = 32.dp)
                        .padding(horizontal = 32.dp)
                        .shadow(
                            elevation = 12.dp,
                            shape = RoundedCornerShape(100),
                            ambientColor = BreathTheme.colors.secondarySoul,
                            spotColor = BreathTheme.colors.secondarySoul,
                            clip = false
                        )
                        .clip(RoundedCornerShape(100))
                        .background(BreathTheme.colors.card)
                        .padding(12.dp)
                        .align(Alignment.BottomCenter)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(32.dp)
                    ) {
                        AnimatedContent(
                            targetState = screenState.currentMediaItem?.mediaMetadata?.artworkUri,
                            label = "Artwork"
                        ) { uri ->
                            AsyncImage(
                                modifier = Modifier
                                    .size(80.dp)
                                    .clip(CircleShape)
                                    .border(
                                        color = BreathTheme.colors.secondarySoul.copy(alpha = 0.5f),
                                        shape = CircleShape,
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
                            targetState = screenState.currentMediaItem?.mediaMetadata?.title,
                            label = "Title"
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
                                .padding(end = 16.dp)
                                .size(48.dp)
                                .clickable(
                                    indication = rememberBreathRipple(),
                                    interactionSource = remember { MutableInteractionSource() },
                                    onClick = {
                                        if (screenState.isPlaying) {
                                            onUIAction(SoundscapeUIAction.PausePlayback)
                                        } else {
                                            onUIAction(
                                                SoundscapeUIAction.PlayOrPauseSound(
                                                    screenState.currentMediaItem?.mediaMetadata?.extras?.getInt("audioSrc")
                                                )
                                            )
                                        }
                                    }
                                )
                                .clip(RoundedCornerShape(24.dp))
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

@Composable
fun ToggleButton(
    text: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .padding(4.dp)
            .background(
                color = if (selected) Color.Black.copy(alpha = 0.3f) else Color.White.copy(alpha = 0.2f),
                shape = RoundedCornerShape(100.dp)
            )
            .clickable { onClick() }
            .padding(horizontal = 16.dp, vertical = 8.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(text = text, color = Color.White)
    }
}

// ----------------------
// New wrapper composable
// ----------------------
@Composable
fun SoundscapeFilterScreen(
    viewModel: SoundscapeViewModel,
    onUIAction: (SoundscapeUIAction) -> Unit
) {
    val screenState by viewModel.screenState.collectAsState()
    SoundscapeFilterScreenContent(screenState, onUIAction)
}

@Preview(showBackground = true)
@Composable
fun SoundscapeFilterScreenPreview() {
    // Create a dummy state for preview
    val dummyState = SoundscapeScreenState(
        selectedMood = "Enhance Sleep",
        isPlaying = true,
        currentMediaItem = null, // or provide a dummy media item if needed
        filteredSounds = soundScapeItemsList.filter { it.id % 2 == 0 }
    )
    BreathTheme {
        SoundscapeFilterScreenContent(dummyState) {}
    }
}
