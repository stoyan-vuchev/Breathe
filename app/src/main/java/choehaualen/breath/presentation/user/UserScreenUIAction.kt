package choehaualen.breath.presentation.user

import androidx.compose.runtime.Immutable

@Immutable
interface UserScreenUIAction {

    // The UI Actions remain unchanged cuz they're just an immutable data objects
    // It's safe to mark them as immutable

    data object Next : UserScreenUIAction // This is always the same, unlike the state
    data class SetNameText(
        val text: String
    ) : UserScreenUIAction

}