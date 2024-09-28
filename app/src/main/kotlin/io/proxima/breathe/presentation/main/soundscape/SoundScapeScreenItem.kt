package io.proxima.breathe.presentation.main.soundscape

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import io.proxima.breathe.R
import io.proxima.breathe.core.ui.components.rememberBreathRipple
import io.proxima.breathe.core.ui.theme.BreathTheme
import io.proxima.breathe.core.ui.theme.DreamyNightColors
import io.proxima.breathe.domain.model.SoundScapeItem
import sv.lib.squircleshape.SquircleShape

@Composable
fun SoundScapeScreenItem(
    modifier: Modifier = Modifier,
    soundscapeItem: SoundScapeItem,
    isPlaying: Boolean,
    onUIAction: (SoundscapeUIAction) -> Unit
) {

    Box(
        modifier = modifier
            .fillMaxWidth()
            .aspectRatio(1f)
            .clickable(
                indication = rememberBreathRipple(),
                interactionSource = remember { MutableInteractionSource() },
                onClick = {
                    onUIAction(
                        SoundscapeUIAction.PlayOrPauseSound(
                            soundscapeItem.audioSrc,
                            soundscapeItem
                        )
                    )
                }
            )
            .clip(shape = SquircleShape(16.dp))
            .background(BreathTheme.colors.card)
    ) {

        AsyncImage(
            modifier = Modifier.matchParentSize(),
            model = ImageRequest.Builder(LocalContext.current)
                .data(soundscapeItem.image)
                .crossfade(true)
                .build(),
            contentDescription = null,
            contentScale = ContentScale.Crop
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            BreathTheme.colors.secondarySoul.copy(.0f),
                            BreathTheme.colors.secondarySoul.copy(.25f),
                            BreathTheme.colors.secondarySoul.copy(.8f),
                        )
                    )
                )
                .align(Alignment.BottomCenter)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
                .align(Alignment.BottomStart),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clickable(
                        indication = rememberBreathRipple(),
                        interactionSource = remember { MutableInteractionSource() },
                        onClick = {
                            onUIAction(
                                SoundscapeUIAction.PlayOrPauseSound(
                                    soundscapeItem.audioSrc,
                                    soundscapeItem
                                )
                            )
                        }
                    )
                    .clip(SquircleShape(24.dp))
                    .background(BreathTheme.colors.background),
                contentAlignment = Alignment.Center
            ) {

                AsyncImage(
                    modifier = Modifier.size(32.dp),
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(
                            if (isPlaying) R.drawable.round_pause_24
                            else R.drawable.round_play_arrow_24
                        )
                        .crossfade(true)
                        .build(),
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(BreathTheme.colors.text)
                )

            }

            Text(
                modifier = Modifier
                    .weight(1f)
                    .padding(
                        start = 20.dp,
                        end = 20.dp
                    ),
                text = soundscapeItem.name,
                style = BreathTheme.typography.labelMedium,
                color = BreathTheme.colors.background,
                textAlign = TextAlign.Start
            )

        }

    }

}

@Preview
@Composable
fun SoundScapeScreenItemPreview() = BreathTheme(colors = DreamyNightColors) {
    SoundScapeScreenItem(
        modifier = Modifier.width(200.dp),
        soundscapeItem = SoundScapeItem(
            name = "Dreamy Night",
            audioSrc = R.raw.midnight,
            image = R.drawable.dreamy_night
        ),
        isPlaying = false,
        onUIAction = {}
    )
}