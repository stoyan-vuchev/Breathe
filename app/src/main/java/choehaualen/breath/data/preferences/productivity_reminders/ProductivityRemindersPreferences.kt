package choehaualen.breath.data.preferences.productivity_reminders

import kotlinx.coroutines.flow.Flow

interface ProductivityRemindersPreferences {

    suspend fun setWaterIntakeReminderEnabled(enabled: Boolean)
    fun getWaterIntakeReminderEnabled(): Flow<Boolean>

}