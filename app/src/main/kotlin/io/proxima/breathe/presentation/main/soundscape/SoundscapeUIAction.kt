package io.proxima.breathe.presentation.main.soundscape

import androidx.annotation.RawRes
import androidx.compose.runtime.Immutable
import io.proxima.breathe.domain.model.SoundScapeItem

@Immutable
sealed interface SoundscapeUIAction {

    data object NavigateUp : SoundscapeUIAction
    data object PausePlayback : SoundscapeUIAction

    data class PlayOrPauseSound(
        @RawRes val audioSrc: Int?,
        val soundScapeItem: SoundScapeItem? = null
    ) : SoundscapeUIAction

}