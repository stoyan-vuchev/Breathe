package io.proxima.breathe.data.repository

import io.proxima.breathe.data.local.dao.QuotesDao
import io.proxima.breathe.data.local.entity.QuoteEntity
import io.proxima.breathe.data.remote.QuotesAPI
import io.proxima.breathe.domain.model.QuoteModel
import io.proxima.breathe.domain.repository.QuotesRepository
import io.proxima.breathe.mappers.toEntity
import io.proxima.breathe.mappers.toModel
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
        }

        return quotesDao.getQuote() ?: QuoteEntity.Default

    }

}