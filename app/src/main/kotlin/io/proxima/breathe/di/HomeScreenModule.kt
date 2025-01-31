package io.proxima.breathe.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.proxima.breathe.data.local.AppDatabase
import io.proxima.breathe.data.local.dao.QuotesDao
import io.proxima.breathe.data.remote.QuotesAPI
import io.proxima.breathe.data.repository.QuotesRepositoryImpl
import io.proxima.breathe.domain.repository.QuotesRepository
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object HomeScreenModule {

    @Provides
    @Singleton
    fun provideQuotesDao(appDatabase: AppDatabase): QuotesDao {
        return appDatabase.quotesDao
    }

    @Provides
    @Singleton
    fun provideQuotesAPI(): QuotesAPI {
        return Retrofit.Builder()
            .baseUrl("https://zenquotes.io/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(QuotesAPI::class.java)
    }

    @Provides
    @Singleton
    fun provideQuotesRepository(
        quotesAPI: QuotesAPI,
        quotesDao: QuotesDao
    ): QuotesRepository {
        return QuotesRepositoryImpl(
            quotesAPI = quotesAPI,
            quotesDao = quotesDao
        )
    }

}