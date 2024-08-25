package choehaualen.breath.data.preferences.productivity_reminders

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

    override fun getWaterIntakeReminderEnabled(): Flow<Boolean> {
        return preferences.data
            .filter { it[WATER_INTAKE_REMINDER_KEY] != null }
            .map { it[WATER_INTAKE_REMINDER_KEY]!! }
    }

    companion object {

        private val WATER_INTAKE_REMINDER_KEY = booleanPreferencesKey("water_intake_reminder")

        val Context.productivityRemindersPreferences by preferencesDataStore(
            name = "productivity_reminders_preferences"
        )

    }

}