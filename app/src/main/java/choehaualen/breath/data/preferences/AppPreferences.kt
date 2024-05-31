package choehaualen.breath.data.preferences

interface AppPreferences {

    suspend fun setUser(name: String)
    suspend fun getUser(): String?

    suspend fun setSleepGoal(sleepGoalDuration: Long)
    suspend fun getSleepGoal(): Long?

}