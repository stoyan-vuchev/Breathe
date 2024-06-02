package choehaualen.breath.core.etc

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource

sealed interface UiString {

    data class StringResource(@StringRes val resId: Int) : UiString
    data class BasicString(val value: String) : UiString

    companion object {

        val Empty: UiString get() = BasicString("")

        @Composable
        fun UiString.asComposeString(): String {
            return when (this) {
                is StringResource -> stringResource(id = this.resId)
                is BasicString -> this.value
            }
        }

    }

}