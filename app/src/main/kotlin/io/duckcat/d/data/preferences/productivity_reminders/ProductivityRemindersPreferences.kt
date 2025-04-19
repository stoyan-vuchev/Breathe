package io.duckcat.d.data.preferences.productivity_reminders

import kotlinx.coroutines.flow.Flow

interface ProductivityRemindersPreferences {
    suspend fun setWaterIntakeReminderEnabled(enabled: Boolean)
    fun getWaterIntakeReminderEnabled(): Flow<Boolean>

    suspend fun setReadBookReminderEnabled(enabled: Boolean)
    fun getReadBookReminderEnabled(): Flow<Boolean>

    suspend fun setWorkoutReminderEnabled(enabled: Boolean)
    fun getWorkoutReminderEnabled(): Flow<Boolean>

    suspend fun setTouchGrassReminderEnabled(enabled: Boolean)
    fun getTouchGrassReminderEnabled(): Flow<Boolean>
}
