package io.proxima.breathe.core.etc

import io.proxima.breathe.R

sealed class Result<T>(
    val data: T? = null,
    val error: UiString? = null
) {

    class Success<T>(
        data: T? = null
    ) : Result<T>(data = data)

    class Error<T>(
        error: UiString? = DefaultError
    ) : Result<T>(error = error)

    companion object {

        val DefaultError = UiString.StringResource(resId = R.string.something_went_wrong)

        fun extractErrorFromException(e: Exception): UiString {
            return e.localizedMessage?.let { UiString.BasicString(it) } ?: DefaultError
        }

    }

}
