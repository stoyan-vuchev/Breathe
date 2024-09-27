package io.proxima.breathe.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import io.proxima.breathe.data.local.AppDatabase
import io.proxima.breathe.data.local.dao.QuotesDao
import io.proxima.breathe.data.remote.QuotesAPI
import io.proxima.breathe.data.repository.QuotesRepositoryImpl
import io.proxima.breathe.domain.repository.QuotesRepository

@Module
@InstallIn(ViewModelComponent::class)
object HomeScreenModule {

    @Provides
    @ViewModelScoped
    fun provideQuotesDao(appDatabase: AppDatabase): QuotesDao {
        return appDatabase.quotesDao
    }

    @Provides
    @ViewModelScoped
    fun provideQuotesAPI(): QuotesAPI {
        return QuotesAPI.createInstance()
    }

    @Provides
    @ViewModelScoped
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