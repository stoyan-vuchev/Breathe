package io.proxima.breathe.presentation.main.home.mlassist

import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.Call
import retrofit2.Retrofit
import kotlin.jvm.java

interface GeminiApiService {
    @Headers("Content-Type: application/json")
    @POST("v1beta/models/gemini-pro:generateContent?key=YOUR_GEMINI_API_KEY")
    fun generateContent(@Body request: GeminiRequest): Call<GeminiResponse>
}

data class GeminiRequest(val contents: List<Content>)
data class Content(val parts: List<Part>)
data class Part(val text: String)

data class GeminiResponse(val candidates: List<Candidate>)
data class Candidate(val content: Content)

object RetrofitClient {
    private const val BASE_URL = "https://generativelanguage.googleapis.com/"
    val instance: GeminiApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(GeminiApiService::class.java)
    }
}