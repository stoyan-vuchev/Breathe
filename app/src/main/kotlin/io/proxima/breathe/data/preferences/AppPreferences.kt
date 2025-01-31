package io.proxima.breathe.data.preferences

import io.proxima.breathe.core.etc.Result

interface AppPreferences {

    suspend fun setUser(name: String)
    suspend fun getUser(): Result<String>

    suspend fun setSleepGoal(sleepGoalDuration: Long)
    suspend fun getSleepGoal(): Result<Long>

    suspend fun setUsualBedtime(bedtime: Pair<Int, Int>)
    suspend fun getUsualBedtime(): Result<Pair<Int, Int>>

    suspend fun setUsualWakeUpTime(wakeUpTime: Pair<Int, Int>)
    suspend fun getUsualWakeUpTime(): Result<Pair<Int, Int>>

    suspend fun setHabitName(name: String)
    suspend fun getHabitName(): Result<String>

    suspend fun setHabitQuote(quote: String)
    suspend fun getHabitQuote(): Result<String>

    suspend fun setHabitDuration(duration: Int)
    suspend fun getHabitDuration(): Result<Int>

    suspend fun getHabitProgress(): Result<Int>
    suspend fun incrementHabitProgressCounter()
    suspend fun resetHabitProgressCounter()

    suspend fun deleteData(): Result<Unit>

}