package io.duckcat.d.presentation.main.productivity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.duckcat.d.data.preferences.productivity_reminders.ProductivityRemindersPreferences
import io.duckcat.d.framework.worker.productivity.ProductivityRemindersWorkerManager
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
    private val productivityRemindersManager: ProductivityRemindersWorkerManager
) : ViewModel() {

    val state: StateFlow<ProductivityScreenState> = combine(
        preferences.getWaterIntakeReminderEnabled(),
        preferences.getReadBookReminderEnabled(),
        preferences.getWorkoutReminderEnabled(),
        preferences.getTouchGrassReminderEnabled()
    ) { water, read, workout, grass ->
        ProductivityScreenState(
            isWaterIntakeReminderEnabled = water,
            isReadBookReminderEnabled = read,
            isBasicWorkoutReminderEnabled = workout,
            isTouchGrassReminderEnabled = grass
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5.seconds.inWholeMilliseconds),
        initialValue = ProductivityScreenState()
    )

    private val _uiActionChannel = Channel<ProductivityScreenUIAction>()
    val uiActionFlow = _uiActionChannel.receiveAsFlow()

    fun onUIAction(uiAction: ProductivityScreenUIAction) = when (uiAction) {
        is ProductivityScreenUIAction.NavigateUp -> sendUIAction(uiAction)
        is ProductivityScreenUIAction.SetReminderEnabled -> onSetReminderEnabled(uiAction)
    }

    private fun onSetReminderEnabled(action: ProductivityScreenUIAction.SetReminderEnabled) {
        when (action.id) {
            ProductivityReminders.WATER_INTAKE -> {
                if (action.enabled) {
                    productivityRemindersManager.enqueueWaterIntakeReminder(
                        interval = ProductivityReminderInterval.FortyFiveMinutes.inMilliseconds
                    )
                    viewModelScope.launch { preferences.setWaterIntakeReminderEnabled(true) }
                } else {
                    productivityRemindersManager.cancelWaterIntakeReminder()
                    viewModelScope.launch { preferences.setWaterIntakeReminderEnabled(false) }
                }
            }
            ProductivityReminders.READ_BOOK -> {
                if (action.enabled) {
                    productivityRemindersManager.enqueueReadBookReminder(
                        interval = ProductivityReminderInterval.FortyFiveMinutes.inMilliseconds
                    )
                    viewModelScope.launch { preferences.setReadBookReminderEnabled(true) }
                } else {
                    productivityRemindersManager.cancelReadBookReminder()
                    viewModelScope.launch { preferences.setReadBookReminderEnabled(false) }
                }
            }
            ProductivityReminders.BASIC_WORKOUT -> {
                if (action.enabled) {
                    productivityRemindersManager.enqueueBasicWorkoutReminder(
                        interval = ProductivityReminderInterval.FortyFiveMinutes.inMilliseconds
                    )
                    viewModelScope.launch { preferences.setWorkoutReminderEnabled(true) }
                } else {
                    productivityRemindersManager.cancelBasicWorkoutReminder()
                    viewModelScope.launch { preferences.setWorkoutReminderEnabled(false) }
                }
            }
            ProductivityReminders.TOUCH_GRASS -> {
                if (action.enabled) {
                    productivityRemindersManager.enqueueTouchGrassReminder(
                        interval = ProductivityReminderInterval.FortyFiveMinutes.inMilliseconds
                    )
                    viewModelScope.launch { preferences.setTouchGrassReminderEnabled(true) }
                } else {
                    productivityRemindersManager.cancelTouchGrassReminder()
                    viewModelScope.launch { preferences.setTouchGrassReminderEnabled(false) }
                }
            }
        }
    }

    private fun sendUIAction(uiAction: ProductivityScreenUIAction) {
        viewModelScope.launch { _uiActionChannel.send(uiAction) }
    }
}
