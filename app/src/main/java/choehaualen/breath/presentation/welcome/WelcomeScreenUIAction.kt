package choehaualen.breath.presentation.welcome

import androidx.compose.runtime.Stable

@Stable
sealed interface WelcomeScreenUIAction {

    data class Next(
        val currentSegment: WelcomeScreenSegment
    ) : WelcomeScreenUIAction

    data class Back(
        val currentSegment: WelcomeScreenSegment
    ) : WelcomeScreenUIAction

}