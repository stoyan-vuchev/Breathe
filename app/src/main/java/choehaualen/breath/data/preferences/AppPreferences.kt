package choehaualen.breath.data.preferences

interface AppPreferences {

    suspend fun setUser(name: String)
    suspend fun getUser(): String?

}