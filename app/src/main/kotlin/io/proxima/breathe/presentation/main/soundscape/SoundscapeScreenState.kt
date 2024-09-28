package io.proxima.breathe.presentation.main.soundscape

import androidx.compose.runtime.Stable
import androidx.media3.common.MediaItem

@Stable
data class SoundscapeScreenState(
    val isPlaying: Boolean = false,
    val currentMediaItem: MediaItem? = null
)