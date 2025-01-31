package io.proxima.breathe.presentation.boarding.user

import androidx.compose.runtime.Immutable

@Immutable
interface UserScreenUIAction {

    data class SetUsernameText(val text: String) : UserScreenUIAction
    data object GoToUsernameSegment : UserScreenUIAction
    data object GoToBedtimeSegment : UserScreenUIAction

    data class SetBedtime(val hour: Int, val minute: Int) : UserScreenUIAction
    data object GoToWakeUpSegment : UserScreenUIAction

    data class SetWakeUp(val hour: Int, val minute: Int) : UserScreenUIAction
    data object GoToGreetSegment : UserScreenUIAction

    data object GetStarted : UserScreenUIAction
    data object PopBackStack : UserScreenUIAction

}