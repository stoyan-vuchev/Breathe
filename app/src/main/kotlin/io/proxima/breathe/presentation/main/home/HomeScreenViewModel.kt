package io.proxima.breathe.presentation.main.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.proxima.breathe.core.etc.UiString
import io.proxima.breathe.data.preferences.AppPreferences  // ✅ Import AppPreferences
import io.proxima.breathe.domain.repository.QuotesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val quotesRepository: QuotesRepository,
    val appPreferences: AppPreferences   // ✅ Inject AppPreferences here
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
        is HomeScreenUIAction.NavigateToSleep,
        is HomeScreenUIAction.NavigateToBreathe,
        is HomeScreenUIAction.NavigateToSoundscapeFilter,
        is HomeScreenUIAction.NavigateToHabitControl,
        is HomeScreenUIAction.NavigateToProductivity,
        is HomeScreenUIAction.NavigateToMlAssist,
        is HomeScreenUIAction.NavigateToPomodoro,
        is HomeScreenUIAction.NavigateToSettings,
        is HomeScreenUIAction.NavigateToExplore -> sendUIAction(uiAction)

        is HomeScreenUIAction.ExpandQuote -> _screenState.update {
            it.copy(isQuotesDialogShown = true)
        }

        is HomeScreenUIAction.ShrinkQuote -> _screenState.update {
            it.copy(isQuotesDialogShown = false)
        }

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
            val quote = withContext(Dispatchers.IO) { quotesRepository.getDailyQuote() }
            _screenState.update { it.copy(quote = quote) }
        }
    }
}
