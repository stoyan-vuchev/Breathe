package io.proxima.breathe.domain.model

import androidx.annotation.DrawableRes
import androidx.annotation.RawRes
import androidx.compose.runtime.Stable
import io.proxima.breathe.R

@Stable
data class SoundScapeItem(
    val id: Int = 1,
    val name: String,
    @RawRes val audioSrc: Int,
    @DrawableRes val image: Int
)

val soundScapeItemsList = listOf(
    SoundScapeItem(
        id = 1,
        name = "Dreamy Night",
        audioSrc = R.raw.midnight,
        image = R.drawable.dreamy_night
    ),
    SoundScapeItem(
        id = 2,
        name = "Rain",
        audioSrc = R.raw.rain_with_thunderstorms,
        image = R.drawable.rain
    ),
    SoundScapeItem(
        id = 3,
        name = "Fireplace",
        audioSrc = R.raw.fireplace,
        image = R.drawable.fireplace
    ),
    SoundScapeItem(
        id = 4,
        name = "Birds",
        audioSrc = R.raw.birds,
        image = R.drawable.birds
    ),

    SoundScapeItem(
        id = 5,
        name = "Beach",
        audioSrc = R.raw.beach,
        image = R.drawable.beach
    ),
    SoundScapeItem(
        id = 6,
        name = "White noise",
        audioSrc = R.raw.white_noise,
        image = R.drawable.white_noise
    ),

    SoundScapeItem(
        id = 7,
        name = "Pink noise",
        audioSrc = R.raw.pink_noise,
        image = R.drawable.pink_noise
    ),
    SoundScapeItem(
        id = 8,
        name = "Brown noise",
        audioSrc = R.raw.brown_noise,
        image = R.drawable.brown_noise
    ),
)