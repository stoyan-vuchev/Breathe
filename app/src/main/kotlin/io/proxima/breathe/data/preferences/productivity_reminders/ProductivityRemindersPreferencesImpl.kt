package io.proxima.breathe.data.preferences.productivity_reminders

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ProductivityRemindersPreferencesImpl @Inject constructor(
    private val preferences: DataStore<Preferences>
) : ProductivityRemindersPreferences {

    override suspend fun setWaterIntakeReminderEnabled(enabled: Boolean) {
        preferences.edit { it[WATER_INTAKE_REMINDER_KEY] = enabled }
    }

    override fun getWaterIntakeReminderEnabled(): Flow<Boolean> =
        preferences.data.filter { it.contains(WATER_INTAKE_REMINDER_KEY) }
            .map { it[WATER_INTAKE_REMINDER_KEY] ?: false }

    override suspend fun setReadBookReminderEnabled(enabled: Boolean) {
        preferences.edit { it[READ_BOOK_REMINDER_KEY] = enabled }
    }

    override fun getReadBookReminderEnabled(): Flow<Boolean> =
        preferences.data.filter { it.contains(READ_BOOK_REMINDER_KEY) }
            .map { it[READ_BOOK_REMINDER_KEY] ?: false }

    override suspend fun setWorkoutReminderEnabled(enabled: Boolean) {
        preferences.edit { it[WORKOUT_REMINDER_KEY] = enabled }
    }

    override fun getWorkoutReminderEnabled(): Flow<Boolean> =
        preferences.data.filter { it.contains(WORKOUT_REMINDER_KEY) }
            .map { it[WORKOUT_REMINDER_KEY] ?: false }

    override suspend fun setTouchGrassReminderEnabled(enabled: Boolean) {
        preferences.edit { it[TOUCH_GRASS_REMINDER_KEY] = enabled }
    }

    override fun getTouchGrassReminderEnabled(): Flow<Boolean> =
        preferences.data.filter { it.contains(TOUCH_GRASS_REMINDER_KEY) }
            .map { it[TOUCH_GRASS_REMINDER_KEY] ?: false }

    companion object {
        private val WATER_INTAKE_REMINDER_KEY = booleanPreferencesKey("water_intake_reminder")
        private val READ_BOOK_REMINDER_KEY = booleanPreferencesKey("read_book_reminder")
        private val WORKOUT_REMINDER_KEY = booleanPreferencesKey("workout_reminder")
        private val TOUCH_GRASS_REMINDER_KEY = booleanPreferencesKey("touch_grass_reminder")

        val Context.productivityRemindersPreferences by preferencesDataStore(name = "productivity_reminders_preferences")
    }
}
