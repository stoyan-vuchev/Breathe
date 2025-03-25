package io.proxima.breathe.framework.worker.productivity

import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import io.proxima.breathe.framework.worker.water_intake.WaterIntakeReminderWorker
import io.proxima.breathe.presentation.main.productivity.ProductivityReminders
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class ProductivityRemindersWorkerManagerImpl @Inject constructor(
    private val workManager: WorkManager
) : ProductivityRemindersWorkerManager {

    override fun enqueueWaterIntakeReminder(interval: Long) {
        workManager.enqueue(
            PeriodicWorkRequestBuilder<WaterIntakeReminderWorker>(
                repeatInterval = interval,
                repeatIntervalTimeUnit = TimeUnit.MILLISECONDS,
                flexTimeInterval = interval,
                flexTimeIntervalUnit = TimeUnit.MINUTES
            ).apply {
                addTag(ProductivityReminders.WATER_INTAKE)
                addTag(ProductivityReminders.MAIN_TAG)
                setInitialDelay(interval, TimeUnit.MILLISECONDS)
            }.build()
        )
    }

    override fun cancelWaterIntakeReminder() {
        workManager.cancelAllWorkByTag(ProductivityReminders.WATER_INTAKE)
    }

    override fun enqueueReadBookReminder(interval: Long) {
        workManager.enqueue(
            PeriodicWorkRequestBuilder<ReadBookReminderWorker>(
                repeatInterval = interval,
                repeatIntervalTimeUnit = TimeUnit.MILLISECONDS,
                flexTimeInterval = interval,
                flexTimeIntervalUnit = TimeUnit.MINUTES
            ).apply {
                addTag(ProductivityReminders.READ_BOOK)
                addTag(ProductivityReminders.MAIN_TAG)
                setInitialDelay(interval, TimeUnit.MILLISECONDS)
            }.build()
        )
    }

    override fun cancelReadBookReminder() {
        workManager.cancelAllWorkByTag(ProductivityReminders.READ_BOOK)
    }

    override fun enqueueBasicWorkoutReminder(interval: Long) {
        workManager.enqueue(
            PeriodicWorkRequestBuilder<BasicWorkoutReminderWorker>(
                repeatInterval = interval,
                repeatIntervalTimeUnit = TimeUnit.MILLISECONDS,
                flexTimeInterval = interval,
                flexTimeIntervalUnit = TimeUnit.MINUTES
            ).apply {
                addTag(ProductivityReminders.BASIC_WORKOUT)
                addTag(ProductivityReminders.MAIN_TAG)
                setInitialDelay(interval, TimeUnit.MILLISECONDS)
            }.build()
        )
    }

    override fun cancelBasicWorkoutReminder() {
        workManager.cancelAllWorkByTag(ProductivityReminders.BASIC_WORKOUT)
    }

    override fun enqueueTouchGrassReminder(interval: Long) {
        workManager.enqueue(
            PeriodicWorkRequestBuilder<TouchGrassReminderWorker>(
                repeatInterval = interval,
                repeatIntervalTimeUnit = TimeUnit.MILLISECONDS,
                flexTimeInterval = interval,
                flexTimeIntervalUnit = TimeUnit.MINUTES
            ).apply {
                addTag(ProductivityReminders.TOUCH_GRASS)
                addTag(ProductivityReminders.MAIN_TAG)
                setInitialDelay(interval, TimeUnit.MILLISECONDS)
            }.build()
        )
    }

    override fun cancelTouchGrassReminder() {
        workManager.cancelAllWorkByTag(ProductivityReminders.TOUCH_GRASS)
    }
}
