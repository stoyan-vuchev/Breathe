package choehaualen.breath.presentation.welcome

import androidx.compose.runtime.Stable

@Stable
data class WelcomeScreenState(
    val segment: WelcomeScreenSegment = WelcomeScreenSegment.Welcome
)