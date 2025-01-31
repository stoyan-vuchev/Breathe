package io.proxima.breathe.di

import android.content.Context
import androidx.work.WorkManager
import io.proxima.breathe.data.preferences.productivity_reminders.ProductivityRemindersPreferences
import io.proxima.breathe.data.preferences.productivity_reminders.ProductivityRemindersPreferencesImpl
import io.proxima.breathe.data.preferences.productivity_reminders.ProductivityRemindersPreferencesImpl.Companion.productivityRemindersPreferences
import io.proxima.breathe.framework.worker.ProductivityRemindersWorkerManager
import io.proxima.breathe.framework.worker.water_intake.ProductivityRemindersWorkerManagerImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ProductivityRemindersModule {

    @Provides
    @Singleton
    fun provideProductivityRemindersPreferences(
        @ApplicationContext context: Context
    ): ProductivityRemindersPreferences = ProductivityRemindersPreferencesImpl(
        preferences = context.applicationContext.productivityRemindersPreferences
    )

    @Provides
    @Singleton
    fun provideWorkManager(
        @ApplicationContext context: Context
    ): WorkManager = WorkManager.getInstance(context.applicationContext)

    @Provides
    @Singleton
    fun provideWaterIntakeReminderWorkerManager(
        workManager: WorkManager
    ): ProductivityRemindersWorkerManager = ProductivityRemindersWorkerManagerImpl(workManager)

}