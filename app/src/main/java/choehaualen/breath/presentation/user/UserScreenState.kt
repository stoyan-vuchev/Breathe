package choehaualen.breath.presentation.user

import androidx.compose.runtime.Stable

@Stable
data class UserScreenState(
    val username: String? = null,
    val nameText: String = ""
)