package io.proxima.breathe.framework.worker.water_intake

import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import io.proxima.breathe.framework.worker.ProductivityRemindersWorkerManager
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

    override fun cancel(tag: String) {
        workManager.cancelAllWorkByTag(ProductivityReminders.WATER_INTAKE)
    }

}// the interval had 45min default, also 60, yes