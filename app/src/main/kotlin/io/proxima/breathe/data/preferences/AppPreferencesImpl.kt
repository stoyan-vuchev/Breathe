package io.proxima.breathe.data.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.byteArrayPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import io.proxima.breathe.core.etc.Result
import io.proxima.breathe.core.etc.UiString
import kotlinx.coroutines.flow.firstOrNull
import javax.inject.Inject

class AppPreferencesImpl @Inject constructor(
    private val preferences: DataStore<Preferences>
) : AppPreferences {

    override suspend fun setUser(name: String) {
        preferences.edit { it[USER_KEY] = name }
    }

    override suspend fun getUser(): Result<String> {
        return try {
            return preferences.data.firstOrNull()?.let {
                Result.Success(it[USER_KEY])
            } ?: Result.Error(UiString.BasicString("Username not set."))
        } catch (e: Exception) {
            Result.Error(
                UiString.BasicString(
                    e.localizedMessage ?: "Something went wrong."
                )
            )
        }
    }

    //

    override suspend fun setSleepGoal(sleepGoalDuration: Long) {
        preferences.edit { it[SLEEP_GOAL_KEY] = sleepGoalDuration }
    }

    override suspend fun getSleepGoal(): Result<Long> {
        return try {
            return preferences.data.firstOrNull()?.let {
                Result.Success(it[SLEEP_GOAL_KEY])
            } ?: Result.Error(UiString.BasicString("Sleep goal not set."))
        } catch (e: Exception) {
            Result.Error(
                UiString.BasicString(
                    e.localizedMessage ?: "Something went wrong."
                )
            )
        }
    }

    //

    override suspend fun setUsualBedtime(bedtime: Pair<Int, Int>) {
        preferences.edit {
            it[USUAL_BEDTIME_KEY] = byteArrayOf(
                bedtime.first.toByte(),
                bedtime.second.toByte()
            )
        }
    }

    override suspend fun getUsualBedtime(): Result<Pair<Int, Int>> {
        return try {
            return preferences.data.firstOrNull()?.let {

                val hour = it[USUAL_BEDTIME_KEY]?.get(0)?.toInt()
                val minute = it[USUAL_BEDTIME_KEY]?.get(1)?.toInt()

                if (hour != null && minute != null) Result.Success(Pair(hour, minute))
                else Result.Error(UiString.BasicString("Usual bed time not set."))

            } ?: Result.Error(UiString.BasicString("Usual bed time not set."))

        } catch (e: Exception) {
            Result.Error(
                UiString.BasicString(
                    e.localizedMessage ?: "Something went wrong."
                )
            )
        }
    }

    //

    override suspend fun setUsualWakeUpTime(wakeUpTime: Pair<Int, Int>) {
        preferences.edit {
            it[USUAL_WAKE_UP_TIME_KEY] = byteArrayOf(
                wakeUpTime.first.toByte(),
                wakeUpTime.second.toByte()
            )
        }
    }

    override suspend fun getUsualWakeUpTime(): Result<Pair<Int, Int>> {
        return try {
            return preferences.data.firstOrNull()?.let {

                val hour = it[USUAL_WAKE_UP_TIME_KEY]?.get(0)?.toInt()
                val minute = it[USUAL_WAKE_UP_TIME_KEY]?.get(1)?.toInt()

                if (hour != null && minute != null) Result.Success(Pair(hour, minute))
                else Result.Error(UiString.BasicString("Usual wake up time not set."))

            } ?: Result.Error(UiString.BasicString("Usual wake up time not set."))

        } catch (e: Exception) {
            Result.Error(
                UiString.BasicString(
                    e.localizedMessage ?: "Something went wrong."
                )
            )
        }
    }

    //

    override suspend fun setHabitName(name: String) {
        preferences.edit { it[HABIT_NAME_KEY] = name }
    }

    override suspend fun getHabitName(): Result<String> {
        return try {
            return preferences.data.firstOrNull()
                ?.let { Result.Success(it[HABIT_NAME_KEY]) }
                ?: Result.Error(UiString.BasicString("Habit name not set."))
        } catch (e: Exception) {
            Result.Error(
                UiString.BasicString(
                    e.localizedMessage ?: "Something went wrong."
                )
            )
        }
    }

    //

    override suspend fun setHabitQuote(quote: String) {
        preferences.edit { it[HABIT_QUOTE_KEY] = quote }
    }

    override suspend fun getHabitQuote(): Result<String> {
        return try {
            return preferences.data.firstOrNull()
                ?.let { Result.Success(it[HABIT_QUOTE_KEY]) }
                ?: Result.Error(UiString.BasicString("Habit quote not set."))
        } catch (e: Exception) {
            Result.Error(
                UiString.BasicString(
                    e.localizedMessage ?: "Something went wrong."
                )
            )
        }
    }

    //

    override suspend fun setHabitDuration(duration: Int) {
        preferences.edit { prefs ->
            prefs[HABIT_GOAL_DURATION_KEY] = duration
        }
    }

    override suspend fun getHabitDuration(): Result<Int> {
        return try {
            return preferences.data.firstOrNull()
                ?.let { Result.Success(it[HABIT_GOAL_DURATION_KEY]) }
                ?: Result.Error(UiString.BasicString("Habit goal duration not set."))
        } catch (e: Exception) {
            Result.Error(
                UiString.BasicString(
                    e.localizedMessage ?: "Something went wrong."
                )
            )
        }
    }

    //

    override suspend fun getHabitProgress(): Result<Int> {
        return try {
            return preferences.data.firstOrNull()
                ?.let { Result.Success(it[HABIT_CURRENT_PROGRESS_KEY]) }
                ?: Result.Error(UiString.BasicString("Habit current progress not started."))
        } catch (e: Exception) {
            Result.Error(
                UiString.BasicString(
                    e.localizedMessage ?: "Something went wrong."
                )
            )
        }
    }

    override suspend fun incrementHabitProgressCounter() {

        val currentProgress = preferences.data.firstOrNull()?.let {
            it[HABIT_CURRENT_PROGRESS_KEY]
        }

        preferences.edit { prefs ->
            currentProgress?.let { prefs[HABIT_CURRENT_PROGRESS_KEY] = it + 1 }
        }

    }

    override suspend fun resetHabitProgressCounter() {
        preferences.edit { it[HABIT_CURRENT_PROGRESS_KEY] = 0 }
    }

    //

    override suspend fun deleteData(): Result<Unit> {
        return try {
            preferences.edit { prefs ->
                prefs[USER_KEY] = ""
                prefs[SLEEP_GOAL_KEY] = 0L
                prefs[USUAL_BEDTIME_KEY] = byteArrayOf()
                prefs[USUAL_WAKE_UP_TIME_KEY] = byteArrayOf()
                prefs[HABIT_NAME_KEY] = ""
                prefs[HABIT_QUOTE_KEY] = ""
                prefs[HABIT_GOAL_DURATION_KEY] = 0
                prefs[HABIT_CURRENT_PROGRESS_KEY] = 0
            }
            Result.Success(Unit)
        } catch (e: Exception) {
            Result.Error(
                UiString.BasicString(
                    e.localizedMessage ?: "Something went wrong."
                )
            )
        }
    }

    //

    companion object {

        private val USER_KEY = stringPreferencesKey("user")
        private val SLEEP_GOAL_KEY = longPreferencesKey("sleep_goal")
        private val USUAL_BEDTIME_KEY = byteArrayPreferencesKey("usual_bedtime")
        private val USUAL_WAKE_UP_TIME_KEY = byteArrayPreferencesKey("usual_wake_up_time")

        private val HABIT_NAME_KEY = stringPreferencesKey("habit_name")
        private val HABIT_QUOTE_KEY = stringPreferencesKey("habit_quote")
        private val HABIT_GOAL_DURATION_KEY = intPreferencesKey("habit_goal_duration")
        private val HABIT_CURRENT_PROGRESS_KEY = intPreferencesKey("habit_current_progress")

        val Context.preferences by preferencesDataStore(name = "app_preferences")

    }

}