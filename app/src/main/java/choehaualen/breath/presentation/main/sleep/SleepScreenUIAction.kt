package choehaualen.breath.presentation.main.sleep

import androidx.compose.runtime.Immutable

@Immutable
sealed interface SleepScreenUIAction {

    data object NavigateUp : SleepScreenUIAction

}