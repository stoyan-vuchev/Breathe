package io.proxima.breathe.framework.worker

import io.proxima.breathe.presentation.main.productivity.ProductivityReminders

interface ProductivityRemindersWorkerManager {
    fun enqueueWaterIntakeReminder(interval: Long)
    fun cancel(tag: String = ProductivityReminders.MAIN_TAG)
}