package io.duckcat.d.framework.worker.productivity

interface ProductivityRemindersWorkerManager {
    fun enqueueWaterIntakeReminder(interval: Long)
    fun cancelWaterIntakeReminder()
    fun enqueueReadBookReminder(interval: Long)
    fun cancelReadBookReminder()
    fun enqueueBasicWorkoutReminder(interval: Long)
    fun cancelBasicWorkoutReminder()
    fun enqueueTouchGrassReminder(interval: Long)
    fun cancelTouchGrassReminder()
}
