package choehaualen.breath.data.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.firstOrNull
import javax.inject.Inject

class AppPreferencesImpl @Inject constructor(
    private val preferences: DataStore<Preferences>
) : AppPreferences {

    override suspend fun setUser(name: String) {
        preferences.edit { it[USER_KEY] = name }
    }

    override suspend fun getUser(): String? {
        return preferences.data.firstOrNull()?.let { it[USER_KEY] }
    }

    companion object {
        private val USER_KEY = stringPreferencesKey("user")
        val Context.preferences by preferencesDataStore(name = "app_preferences")
    }

}