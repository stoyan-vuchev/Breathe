package choehaualen.breath.di

import android.content.Context
import choehaualen.breath.data.local.AppDatabase
import choehaualen.breath.data.manager.SleepManager
import choehaualen.breath.data.preferences.AppPreferences
import choehaualen.breath.data.preferences.AppPreferencesImpl
import choehaualen.breath.data.preferences.AppPreferencesImpl.Companion.preferences
import dagger.Module
import dagger.Provides
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestCoroutineScheduler
import kotlinx.coroutines.test.TestDispatcher
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [AppModule::class]
)
object TestAppModule {

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
            inMemory = true
        )
    }

    @Provides
    @Singleton
    fun provideTestCoroutineScheduler(): TestDispatcher {
        return StandardTestDispatcher(TestCoroutineScheduler())
    }

    @Provides
    @Singleton
    fun provideSleepManager(
        appDatabase: AppDatabase,
        testDispatcher: TestDispatcher
    ): SleepManager {
        return SleepManager(
            sleepDao = appDatabase.sleepDao,
            ioDispatcher = testDispatcher
        )
    }

}