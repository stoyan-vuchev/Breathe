package choehaualen.breath.presentation.boarding.user

import androidx.compose.runtime.Stable

@Stable
data class UserScreenState(
    val username: String? = null,
    val nameText: String = "",
    val usernameValidationResult: UsernameValidationResult = UsernameValidationResult.ValidUsername
)