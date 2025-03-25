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
    @DrawableRes val image: Int,
    val tags: List<String> = emptyList()  // <-- new property for filtering
)

val soundScapeItemsList = listOf(
    SoundScapeItem(
        id = 1,
        name = "Dreamy Night",
        audioSrc = R.raw.midnight,
        image = R.drawable.dreamy_night,
        tags = listOf("sleep") // example tags
    ),
    SoundScapeItem(
        id = 2,
        name = "Rain",
        audioSrc = R.raw.rain_with_thunderstorms,
        image = R.drawable.rain,
        tags = listOf("sleep", "focus")
    ),
    SoundScapeItem(
        id = 3,
        name = "Fireplace",
        audioSrc = R.raw.fireplace,
        image = R.drawable.fireplace,
        tags = listOf("focus")
    ),
    SoundScapeItem(
        id = 4,
        name = "Distant Thunder",
        audioSrc = R.raw.stormy_weather,
        image = R.drawable.stormy_weather,
        tags = listOf("sleep")
    ),
    SoundScapeItem(
        id = 5,
        name = "Birds",
        audioSrc = R.raw.birds,
        image = R.drawable.birds,
        tags = listOf("sad")
    ),
    SoundScapeItem(
        id = 6,
        name = "Beach",
        audioSrc = R.raw.beach,
        image = R.drawable.beach,
        tags = listOf("sad")
    ),
    SoundScapeItem(
        id = 7,
        name = "White noise",
        audioSrc = R.raw.white_noise,
        image = R.drawable.white_noise,
        tags = listOf("focus")
    ),
    SoundScapeItem(
        id = 8,
        name = "Pink noise",
        audioSrc = R.raw.pink_noise,
        image = R.drawable.pink_noise,
        tags = listOf("sleep")
    ),
    SoundScapeItem(
        id = 9,
        name = "Brown noise",
        audioSrc = R.raw.brown_noise,
        image = R.drawable.brown_noise,
        tags = listOf("sleep", "focus")
    )
)
