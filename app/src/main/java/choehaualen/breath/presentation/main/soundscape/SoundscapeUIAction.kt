package choehaualen.breath.presentation.main.soundscape

import androidx.compose.runtime.Immutable

@Immutable
sealed interface SoundscapeUIAction {

    data object NavigateUp : SoundscapeUIAction

}