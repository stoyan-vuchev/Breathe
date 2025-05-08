package io.duckcat.d.data.repository

import io.duckcat.d.data.local.dao.QuotesDao
import io.duckcat.d.data.local.entity.QuoteEntity
import io.duckcat.d.data.remote.QuotesAPI
import io.duckcat.d.domain.model.QuoteModel
import io.duckcat.d.domain.repository.QuotesRepository
import io.duckcat.d.mappers.toEntity
import io.duckcat.d.mappers.toModel
import javax.inject.Inject
import kotlin.time.Duration.Companion.hours

class QuotesRepositoryImpl @Inject constructor(
    private val quotesAPI: QuotesAPI,
    private val quotesDao: QuotesDao
) : QuotesRepository {

    override suspend fun getDailyQuote(): QuoteModel {

        val oldQuote = quotesDao.getQuote()
        return if (oldQuote != null) {

            val now = System.currentTimeMillis()
            val keepDuration = now - 24.hours.inWholeMilliseconds

            if (oldQuote.id <= keepDuration) {
                fetchAndInsertQuoteIntoDB().toModel()
            } else oldQuote.toModel()

        } else fetchAndInsertQuoteIntoDB().toModel()

    }

    private suspend fun fetchAndInsertQuoteIntoDB(): QuoteEntity {

        quotesAPI.getDailyQuote().body()?.first()?.let {
            quotesDao.deleteQuote()
            quotesDao.insertQuote(it.toEntity())
        } ?: Unit

        return quotesDao.getQuote() ?: QuoteEntity.Default

    }

}