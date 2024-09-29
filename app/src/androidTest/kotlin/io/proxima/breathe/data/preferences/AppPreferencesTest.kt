package io.proxima.breathe.data.preferences

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import assertk.assertThat
import assertk.assertions.isEqualTo
import io.proxima.breathe.data.preferences.AppPreferencesImpl.Companion.preferences
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AppPreferencesTest {

    private lateinit var preferences: AppPreferences

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        preferences =
            AppPreferencesImpl(preferences = context.applicationContext.preferences)
    }

    @Test
    fun set_username() = runTest {

        val expected = "Alen"
        preferences.setUser(expected)

        val actual = preferences.getUser().data
        assertThat(actual).isEqualTo(expected)

    }

}