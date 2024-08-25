package choehaualen.breath.presentation.main.productivity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import choehaualen.breath.data.preferences.productivity_reminders.ProductivityRemindersPreferences
import choehaualen.breath.framework.worker.water_intake.ProductivityRemindersWorkerManagerImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.time.Duration.Companion.seconds

@HiltViewModel
class ProductivityScreenViewModel @Inject constructor(
    private val preferences: ProductivityRemindersPreferences,
    private val productivityRemindersManager: ProductivityRemindersWorkerManagerImpl
) : ViewModel() {

    val state: StateFlow<ProductivityScreenState> = combine(
        preferences.getWaterIntakeReminderEnabled(),
    ) { flows -> ProductivityScreenState(isWaterIntakeReminderEnabled = flows[0]) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5.seconds.inWholeMilliseconds),
            initialValue = ProductivityScreenState()
        )

    private val _uiActionChannel = Channel<ProductivityScreenUIAction>()
    val uiActionFlow = _uiActionChannel.receiveAsFlow()

    fun onUIAction(uiAction: ProductivityScreenUIAction) = when (uiAction) {
        is ProductivityScreenUIAction.NavigateUp -> sendUIAction(uiAction)
        is ProductivityScreenUIAction.SetReminderEnabled -> onSetReminderEnabled(uiAction)
        else -> Unit
    }

    private fun onSetReminderEnabled(
        uiAction: ProductivityScreenUIAction.SetReminderEnabled
    ) = when (uiAction.id) {

        ProductivityReminders.WATER_INTAKE -> {

            if (uiAction.enabled) {

                productivityRemindersManager.enqueueWaterIntakeReminder(
                    interval = ProductivityReminderInterval.FortyFiveMinutes.inMilliseconds
                ).also {
                    viewModelScope.launch {
                        preferences.setWaterIntakeReminderEnabled(true)
                    }
                }

            } else {

                productivityRemindersManager.cancel().also {
                    viewModelScope.launch { preferences.setWaterIntakeReminderEnabled(false) }
                }

            }

        }

        else -> Unit

    }

    private fun sendUIAction(uiAction: ProductivityScreenUIAction) {
        viewModelScope.launch { _uiActionChannel.send(uiAction) }
    }

}