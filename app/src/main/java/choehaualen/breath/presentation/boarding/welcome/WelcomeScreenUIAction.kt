package choehaualen.breath.presentation.boarding.welcome

import androidx.compose.runtime.Stable

@Stable
sealed interface WelcomeScreenUIAction {

    data class Next(
        val currentSegment: WelcomeScreenSegment
    ) : WelcomeScreenUIAction

    data class Back(
        val currentSegment: WelcomeScreenSegment
    ) : WelcomeScreenUIAction

    data object NavigateToUser : WelcomeScreenUIAction

}