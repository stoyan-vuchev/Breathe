package io.duckcat.d.domain.repository

import io.duckcat.d.domain.model.QuoteModel

interface QuotesRepository {

    suspend fun getDailyQuote(): QuoteModel

}