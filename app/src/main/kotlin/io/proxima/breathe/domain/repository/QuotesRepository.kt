package io.proxima.breathe.domain.repository

import io.proxima.breathe.domain.model.QuoteModel

interface QuotesRepository {

    suspend fun getDailyQuote(): QuoteModel

}