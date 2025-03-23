package io.proxima.breathe.presentation.main.soundscape

import androidx.compose.runtime.Stable
import androidx.media3.common.MediaItem
import io.proxima.breathe.domain.model.SoundScapeItem
import io.proxima.breathe.domain.model.soundScapeItemsList

@Stable
data class SoundscapeScreenState(
    val isPlaying: Boolean = false,
    val currentMediaItem: MediaItem? = null,
    val selectedMood: String = "",
    val filteredSounds: List<SoundScapeItem> = soundScapeItemsList // default to full list
)
