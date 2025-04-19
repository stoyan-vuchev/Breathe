// DataStoreExtensions.kt
package io.duckcat.d.data.preferences.pomodoro_session

import android.content.Context
import androidx.datastore.preferences.preferencesDataStore

// Extension property that creates the DataStore instance
val Context.dataStore by preferencesDataStore(name = "pomodoro_prefs")
