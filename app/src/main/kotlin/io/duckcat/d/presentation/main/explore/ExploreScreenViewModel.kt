package io.duckcat.d.presentation.main.explore

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.common.reflect.Reflection.initialize
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExploreScreenViewModel @Inject constructor() : ViewModel() {

    private val _uiActionChannel = Channel<ExploreScreenUIAction>()
    val uiActionFlow = _uiActionChannel.receiveAsFlow()

    private val _screenState = MutableStateFlow(ExploreScreenState())
    val screenState = _screenState.asStateFlow()

    init {
        initialize()
    }

    fun onUIAction(uiAction: ExploreScreenUIAction) {
        when (uiAction) {
            is ExploreScreenUIAction.NavigateToHome -> sendUIAction(uiAction)
            is ExploreScreenUIAction.NavigateToProfile -> sendUIAction(uiAction)
            is ExploreScreenUIAction.NavigateToSettings -> sendUIAction(uiAction)
            is ExploreScreenUIAction.NavigateToSoundscapeFilter -> sendUIAction(uiAction)
            is ExploreScreenUIAction.NavigateToSleep -> sendUIAction(uiAction) // ✅ Added
            is ExploreScreenUIAction.NavigateToBreathe -> sendUIAction(uiAction) // ✅ Added
            is ExploreScreenUIAction.NavigateToHabitControl -> sendUIAction(uiAction) // ✅ Added
            is ExploreScreenUIAction.NavigateToProductivity -> sendUIAction(uiAction) // ✅ Added // ✅ Added
            is ExploreScreenUIAction.NavigateToPomodoro -> sendUIAction(uiAction) // ✅ Added
            is ExploreScreenUIAction.NavigateToFitness -> sendUIAction(uiAction)
            is ExploreScreenUIAction.NavigateToStudy -> sendUIAction(uiAction)
            else -> Unit // Ignore other actions
        }
    }


    private fun sendUIAction(uiAction: ExploreScreenUIAction) {
        viewModelScope.launch { _uiActionChannel.send(uiAction) }
    }
}
