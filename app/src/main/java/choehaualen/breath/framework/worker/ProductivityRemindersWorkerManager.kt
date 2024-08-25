package choehaualen.breath.framework.worker

import choehaualen.breath.presentation.main.productivity.ProductivityReminders

interface ProductivityRemindersWorkerManager {
    fun enqueueWaterIntakeReminder(interval: Long)
    fun cancel(tag: String = ProductivityReminders.MAIN_TAG)
}