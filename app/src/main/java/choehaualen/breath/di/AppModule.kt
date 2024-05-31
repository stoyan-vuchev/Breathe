package choehaualen.breath.di

import android.content.Context
import choehaualen.breath.data.local.AppDatabase
import choehaualen.breath.data.manager.SleepManager
import choehaualen.breath.data.preferences.AppPreferences
import choehaualen.breath.data.preferences.AppPreferencesImpl
import choehaualen.breath.data.preferences.AppPreferencesImpl.Companion.preferences
import com.google.android.gms.location.ActivityRecognition
import com.google.android.gms.location.ActivityRecognitionClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
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

}