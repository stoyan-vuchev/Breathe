package choehaualen.breath.di

import android.content.Context
import androidx.work.WorkManager
import choehaualen.breath.data.preferences.productivity_reminders.ProductivityRemindersPreferences
import choehaualen.breath.data.preferences.productivity_reminders.ProductivityRemindersPreferencesImpl
import choehaualen.breath.data.preferences.productivity_reminders.ProductivityRemindersPreferencesImpl.Companion.productivityRemindersPreferences
import choehaualen.breath.framework.worker.ProductivityRemindersWorkerManager
import choehaualen.breath.framework.worker.water_intake.ProductivityRemindersWorkerManagerImpl
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