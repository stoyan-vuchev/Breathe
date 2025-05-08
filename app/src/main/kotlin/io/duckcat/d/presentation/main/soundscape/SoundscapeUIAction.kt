package io.duckcat.d.presentation.main.soundscape

import androidx.annotation.RawRes
import androidx.compose.runtime.Immutable
import io.duckcat.d.domain.model.SoundScapeItem

@Immutable
sealed interface SoundscapeUIAction {
    data object NavigateUp : SoundscapeUIAction
    data object PausePlayback : SoundscapeUIAction
    data class PlayOrPauseSound(
        @RawRes val audioSrc: Int?,
        val soundScapeItem: SoundScapeItem? = null
    ) : SoundscapeUIAction
    data class MoodSelected(val mood: String) : SoundscapeUIAction
}
