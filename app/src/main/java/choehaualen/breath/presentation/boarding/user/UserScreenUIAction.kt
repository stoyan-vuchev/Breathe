package choehaualen.breath.presentation.boarding.user

import androidx.compose.runtime.Immutable

@Immutable
interface UserScreenUIAction {

    data object Next : UserScreenUIAction
    data object GetStarted : UserScreenUIAction
    data object PopBackStack : UserScreenUIAction

    data class SetNameText(
        val text: String
    ) : UserScreenUIAction

}