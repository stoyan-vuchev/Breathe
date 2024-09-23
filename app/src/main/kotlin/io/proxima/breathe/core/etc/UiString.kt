package io.proxima.breathe.core.etc

import android.content.Context
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.res.stringResource

@Stable
sealed interface UiString {

    class StringResource(@StringRes val resId: Int, vararg val args: Any) : UiString
    data class BasicString(val value: String) : UiString

    companion object {

        val Empty: UiString get() = BasicString("")

        @Composable
        fun UiString.asComposeString(): String {
            return when (this) {
                is StringResource -> stringResource(id = this.resId, *this.args)
                is BasicString -> this.value
            }
        }

        fun UiString.asString(context: Context): String {
            return when (this) {
                is StringResource -> context.getString(this.resId)
                is BasicString -> this.value
            }
        }

    }

}