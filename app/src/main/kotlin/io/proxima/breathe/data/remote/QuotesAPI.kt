package io.proxima.breathe.data.remote

import io.proxima.breathe.data.remote.dto.QuoteResponseDto
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface QuotesAPI {

    @GET("api/today")
    suspend fun getDailyQuote(): Response<List<QuoteResponseDto>>

    companion object {

        private const val BASE_URL = "https://zenquotes.io/"

        fun createInstance(): QuotesAPI {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(QuotesAPI::class.java)
        }

    }

}