package choehaualen.breath.data.preferences

interface AppPreferences {

    // Let's add the user firstly.

    suspend fun setUser(name: String)
    suspend fun getUser(): String?

}