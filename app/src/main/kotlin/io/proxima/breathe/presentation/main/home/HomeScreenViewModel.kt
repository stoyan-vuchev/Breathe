package io.proxima.breathe.presentation.main.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.proxima.breathe.core.etc.UiString
import io.proxima.breathe.domain.repository.QuotesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val quotesRepository: QuotesRepository
) : ViewModel() {

    private val _uiActionChannel = Channel<HomeScreenUIAction>()
    val uiActionFlow = _uiActionChannel.receiveAsFlow()

    private val _snackBarChannel = Channel<UiString>()
    val snackBarFlow = _snackBarChannel.receiveAsFlow()

    private val _screenState = MutableStateFlow(HomeScreenState())
    val screenState = _screenState.asStateFlow()

    init {
        initialize()
    }

    fun onUIAction(uiAction: HomeScreenUIAction) = when (uiAction) {

        is HomeScreenUIAction.NavigateToSleep -> sendUIAction(uiAction)
        is HomeScreenUIAction.NavigateToBreathe -> sendUIAction(uiAction)
        is HomeScreenUIAction.NavigateToSoundscape -> sendUIAction(uiAction)
        is HomeScreenUIAction.NavigateToHabitControl -> sendUIAction(uiAction)
        is HomeScreenUIAction.NavigateToProductivity -> sendUIAction(uiAction)
        is HomeScreenUIAction.NavigateToMlAssist -> sendUIAction(uiAction)

        is HomeScreenUIAction.ExpandQuote -> _screenState.update {
            it.copy(isQuotesDialogShown = true)
        }

        is HomeScreenUIAction.ShrinkQuote -> _screenState.update {
            it.copy(isQuotesDialogShown = false)
        }

        /*is HomeScreenUIAction.More -> showSnackbar(
         //   msg = UiString.BasicString("More cool stuff is coming soon! <3")
        */

        is HomeScreenUIAction.NavigateToSettings -> sendUIAction(uiAction)

        else -> {}
    }

    private fun sendUIAction(uiAction: HomeScreenUIAction) {
        viewModelScope.launch { _uiActionChannel.send(uiAction) }
    }

    private fun showSnackbar(msg: UiString) {
        viewModelScope.launch { _snackBarChannel.send(msg) }
    }

    private fun initialize() {
        viewModelScope.launch {
            delay(512L)
            val quote = withContext(Dispatchers.IO) { quotesRepository.getDailyQuote() }
            _screenState.update { it.copy(quote = quote) }
        }
    }

}