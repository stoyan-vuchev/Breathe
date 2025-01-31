package io.proxima.breathe.presentation.main.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.proxima.breathe.core.etc.Result
import io.proxima.breathe.core.etc.UiString
import io.proxima.breathe.data.local.AppDatabase
import io.proxima.breathe.data.preferences.AppPreferences
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SettingsScreenViewModel @Inject constructor(
    private val appPreferences: AppPreferences,
    private val appDatabase: AppDatabase
) : ViewModel() {

    private val _uiActionChannel = Channel<SettingsScreenUIAction>()
    val uiActionFlow = _uiActionChannel.receiveAsFlow()

    private val _snackBarChannel = Channel<UiString>()
    val snackBarFlow = _snackBarChannel.receiveAsFlow()

    fun onUIAction(uiAction: SettingsScreenUIAction) = when (uiAction) {

        is SettingsScreenUIAction.NavigateUp -> sendUIAction(uiAction)

        is SettingsScreenUIAction.Profile -> sendUIAction(uiAction)
        is SettingsScreenUIAction.Notifications -> sendUIAction(uiAction)

        is SettingsScreenUIAction.ShowDeleteDataDialog -> sendUIAction(uiAction)
        is SettingsScreenUIAction.DismissDeleteDataDialog -> sendUIAction(uiAction)
        is SettingsScreenUIAction.ConfirmDeleteData -> deleteData()

        is SettingsScreenUIAction.About -> sendUIAction(uiAction)

        else -> showSnackBar(
            msg = UiString.BasicString("Coming soon! :)")
        )

    }

    private fun deleteData() {
        viewModelScope.launch {
            val result = withContext(Dispatchers.IO) { appPreferences.deleteData() }
            when (result) {

                is Result.Success -> {
                    appDatabase.sleepDao.deleteAllSleepData()
                        .also { appDatabase.quotesDao.deleteQuote() }
                        .also { sendUIAction(SettingsScreenUIAction.ConfirmDeleteData) }
                }

                is Result.Error -> showSnackBar(
                    result.error ?: UiString.BasicString("Something went wrong.")
                )

            }
        }
    }

    private fun sendUIAction(uiAction: SettingsScreenUIAction) {
        viewModelScope.launch { _uiActionChannel.send(uiAction) }
    }

    private fun showSnackBar(msg: UiString) {
        viewModelScope.launch { _snackBarChannel.send(msg) }
    }

}