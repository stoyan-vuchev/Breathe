package io.proxima.breathe.presentation.main.soundscape

import android.net.Uri
import androidx.annotation.OptIn
import androidx.annotation.RawRes
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.MediaItem
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import dagger.hilt.android.lifecycle.HiltViewModel
import io.proxima.breathe.BuildConfig
import io.proxima.breathe.domain.model.SoundScapeItem
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SoundscapeViewModel @Inject constructor(
    private val player: ExoPlayer
) : ViewModel() {

    private val _screenState = MutableStateFlow(SoundscapeScreenState())
    val screenState = _screenState.asStateFlow()

    private val _uiActionChannel = Channel<SoundscapeUIAction>()
    val uiActionFlow = _uiActionChannel.receiveAsFlow()

    init {
        initialize()
    }

    fun onUIAction(uiAction: SoundscapeUIAction) = when (uiAction) {
        is SoundscapeUIAction.NavigateUp -> sendUIAction(uiAction)
        is SoundscapeUIAction.PausePlayback -> pausePlayback()
        is SoundscapeUIAction.PlayOrPauseSound -> playOrPauseSound(
            audioSrc = uiAction.audioSrc,
            soundScapeItem = uiAction.soundScapeItem
        )
    }

    private fun initialize() {
        player.playWhenReady = false
    }

    private fun pausePlayback() {
        player.playWhenReady = false
        _screenState.update { it.copy(isPlaying = false) }
    }

    @OptIn(UnstableApi::class)
    private fun playOrPauseSound(
        @RawRes audioSrc: Int?,
        soundScapeItem: SoundScapeItem?
    ) {
        viewModelScope.launch {

            @RawRes
            val id = player
                .currentMediaItem
                ?.mediaMetadata
                ?.extras
                ?.getInt("audioSrc")

            if (
                player.isReleased && audioSrc != null
                || player.isPlaying && id != audioSrc && audioSrc != null
                || !player.isPlaying && id != audioSrc && audioSrc != null
            ) {

                val mediaItem = MediaItem.fromUri(
                    Uri.parse(
                        "android.resource://" +
                                BuildConfig.APPLICATION_ID +
                                "/" + audioSrc.toString()
                    )
                )

                val artworkUri = Uri.parse(
                    "android.resource://" +
                            BuildConfig.APPLICATION_ID +
                            "/" + soundScapeItem?.image.toString()
                )

                val newMediaItem = mediaItem.buildUpon().setMediaMetadata(
                    mediaItem.mediaMetadata.buildUpon()
                        .setTitle(soundScapeItem?.name)
                        .setArtworkUri(artworkUri)
                        .setExtras(bundleOf(Pair("audioSrc", audioSrc)))
                        .build()
                ).build()

                _screenState.update {
                    it.copy(
                        currentMediaItem = newMediaItem,
                        isPlaying = true
                    )
                }

                player.prepare()
                player.setMediaItem(newMediaItem)
                player.playWhenReady = true

            } else {

                _screenState.update { it.copy(isPlaying = !it.isPlaying) }
                player.playWhenReady = _screenState.value.isPlaying

            }

        }
    }

    private fun sendUIAction(uiAction: SoundscapeUIAction) {
        viewModelScope.launch { _uiActionChannel.send(uiAction) }
    }

}