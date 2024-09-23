package io.proxima.breathe.presentation.boarding.user

import androidx.annotation.StringRes
import io.proxima.breathe.R

object UsernameValidator {

    fun validateUsername(username: String) = when {
        username.isBlank() -> UsernameValidationResult.BlankUsername
        username.length < 2 -> UsernameValidationResult.ShortUsername
        username.length > 64 -> UsernameValidationResult.LongUsername
        else -> UsernameValidationResult.ValidUsername
    }

}

sealed class UsernameValidationResult(
    @StringRes val errorMessage: Int?
) {

    data object BlankUsername : UsernameValidationResult(
        errorMessage = R.string.username_validation_blank_username
    )

    data object ShortUsername : UsernameValidationResult(
        errorMessage = R.string.username_validation_short_username
    )

    data object LongUsername : UsernameValidationResult(
        errorMessage = R.string.username_validation_long_username
    )

    data object ValidUsername : UsernameValidationResult(
        errorMessage = null
    )

}