package io.proxima.breathe.presentation.main.settings.profile

import androidx.compose.runtime.Stable

@Stable
data class SettingsProfileScreenState(
    val username: String = "",
    val usernameTextFieldText: String = "",
    val isInEditMode: Boolean = false
)