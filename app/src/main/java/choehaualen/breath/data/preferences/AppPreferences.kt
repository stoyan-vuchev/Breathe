package choehaualen.breath.data.preferences

interface AppPreferences {

    suspend fun setUser(name: String)
    suspend fun getUser(): String?

    suspend fun setSleepGoal(sleepGoalDuration: Long)
    suspend fun getSleepGoal(): Long?

    suspend fun setUsualBedtime(bedtime: Pair<Int, Int>)
    suspend fun getUsualBedtime(): Pair<Int, Int>?

    suspend fun setUsualWakeUpTime(wakeUpTime: Pair<Int, Int>)
    suspend fun getUsualWakeUpTime(): Pair<Int, Int>?

}