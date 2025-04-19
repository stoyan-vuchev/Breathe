package io.duckcat.d.data.remote

import io.duckcat.d.data.remote.dto.QuoteResponseDto
import retrofit2.Response
import retrofit2.http.GET

interface QuotesAPI {

    @GET("api/today")
    suspend fun getDailyQuote(): Response<List<QuoteResponseDto>>

}