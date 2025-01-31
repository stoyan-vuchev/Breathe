package io.proxima.breathe.data.remote.dto

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class QuoteResponseDto(
    @SerializedName("a")
    val author: String,
    @SerializedName("q")
    val quote: String
)