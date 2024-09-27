package io.proxima.breathe.di

import android.content.Context
import io.proxima.breathe.data.local.AppDatabase
import io.proxima.breathe.data.manager.SleepManager
import io.proxima.breathe.data.preferences.AppPreferences
import io.proxima.breathe.data.preferences.AppPreferencesImpl
import io.proxima.breathe.data.preferences.AppPreferencesImpl.Companion.preferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.proxima.breathe.data.remote.QuotesAPI
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

/**
 * A DI module containing all the necessary dependencies related to the core application.
 */
@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideAppPreferences(@ApplicationContext context: Context): AppPreferences {
        return AppPreferencesImpl(preferences = context.applicationContext.preferences)
    }

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return AppDatabase.createInstance(
            context = context.applicationContext,
            inMemory = false
        )
    }

    @Provides
    @Singleton
    fun provideSleepManager(appDatabase: AppDatabase): SleepManager {
        return SleepManager(
            sleepDao = appDatabase.sleepDao,
            ioDispatcher = Dispatchers.IO
        )
    }

    @Provides
    @Singleton
    fun provideQuotesAPI(): QuotesAPI {
        return QuotesAPI.createInstance()
    }

}