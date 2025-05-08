package io.duckcat.d.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.duckcat.d.data.local.AppDatabase
import io.duckcat.d.data.local.dao.QuotesDao
import io.duckcat.d.data.remote.QuotesAPI
import io.duckcat.d.data.repository.QuotesRepositoryImpl
import io.duckcat.d.domain.repository.QuotesRepository
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