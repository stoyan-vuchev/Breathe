package io.duckcat.d.presentation.main.pomodoro

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager

// Extension property for DataStore (can be placed in a common file)
private val Context.dataStore by preferencesDataStore(name = "pomodoro_prefs")

// Keys for DataStore
private val TIMER_RUNNING_KEY = booleanPreferencesKey("timer_running")
private val TARGET_TIME_KEY = longPreferencesKey("target_time")
private val IS_FOCUS_KEY = booleanPreferencesKey("is_focus")
private val POMODORO_COUNT_KEY = intPreferencesKey("pomodoro_count")
private val TASK_TITLE_KEY = stringPreferencesKey("task_title")

@HiltViewModel
class PomodoroViewModel @Inject constructor(
    @ApplicationContext private val context: Context
) : ViewModel() {

    private val focusTime = 25 * 60L  // 25 minutes in seconds
    private val shortBreak = 5 * 60L   // 5 minutes
    private val midBreak = 15 * 60L    // 15 minutes
    private val longBreak = 30 * 60L   // 30 minutes

    // Timer state flows
    private val _currentTimer = MutableStateFlow(focusTime)
    val currentTimer: StateFlow<Long> = _currentTimer.asStateFlow()

    // Indicates whether the current session is Focus (true) or Break (false)
    private val _isFocusSession = MutableStateFlow(true)
    val isFocusSession: StateFlow<Boolean> = _isFocusSession.asStateFlow()

    private val _pomodoroCount = MutableStateFlow(0)
    val pomodoroCount: StateFlow<Int> = _pomodoroCount.asStateFlow()

    // Task title state
    private val _taskTitle = MutableStateFlow("")
    val taskTitle: StateFlow<String> = _taskTitle.asStateFlow()

    // Timer control state flows
    private val _isTimerRunning = MutableStateFlow(false)
    val isTimerRunning: StateFlow<Boolean> = _isTimerRunning.asStateFlow()

    private val _isTimerPaused = MutableStateFlow(false)
    val isTimerPaused: StateFlow<Boolean> = _isTimerPaused.asStateFlow()

    private var timerJob: Job? = null

    // UI actions flow for navigation or other UI events.
    private val _uiActionFlow = MutableSharedFlow<PomodoroScreenUIAction>()
    val uiActionFlow: SharedFlow<PomodoroScreenUIAction> = _uiActionFlow.asSharedFlow()

    init {
        viewModelScope.launch {
            val preferences = context.dataStore.data.first()
            _taskTitle.value = preferences[TASK_TITLE_KEY] ?: ""
            val timerRunning = preferences[TIMER_RUNNING_KEY] ?: false
            if (timerRunning) {
                val targetTime = preferences[TARGET_TIME_KEY] ?: 0L
                val isFocus = preferences[IS_FOCUS_KEY] ?: true
                val pomodoroCountStored = preferences[POMODORO_COUNT_KEY] ?: 0
                val remaining = (targetTime - System.currentTimeMillis()) / 1000
                if (remaining > 0) {
                    _currentTimer.value = remaining
                    _isFocusSession.value = isFocus
                    _pomodoroCount.value = pomodoroCountStored
                    _isTimerRunning.value = true
                    _isTimerPaused.value = false
                    startTimerInternal(targetTime)
                } else {
                    clearTimerState()
                }
            }
        }
    }

    // Internal function to run the countdown
    private fun startTimerInternal(targetTime: Long) {
        timerJob?.cancel()
        timerJob = viewModelScope.launch {
            while (_currentTimer.value > 0 && _isTimerRunning.value) {
                delay(1000L)
                val newRemaining = (targetTime - System.currentTimeMillis()) / 1000
                _currentTimer.value = newRemaining
            }
            if (_currentTimer.value <= 0) {
                clearTimerState()
                triggerNotificationAndSound()
                onSessionComplete()
            }
        }
    }

    // Start timer (for initial start or resuming from pause)
    fun startTimer() {
        _isTimerRunning.value = true
        _isTimerPaused.value = false
        val targetTime = System.currentTimeMillis() + _currentTimer.value * 1000
        viewModelScope.launch {
            storeTimerState(targetTime)
        }
        startTimerInternal(targetTime)
    }

    // Pause the timer: cancel the job, but retain the current remaining time.
    fun pauseTimer() {
        timerJob?.cancel()
        _isTimerRunning.value = false
        _isTimerPaused.value = true
    }

    // Stop the entire session and reset all state.
    fun stopSession() {
        timerJob?.cancel()
        _isTimerRunning.value = false
        _isTimerPaused.value = false
        _currentTimer.value = focusTime
        _isFocusSession.value = true
        _pomodoroCount.value = 0
        viewModelScope.launch {
            clearTimerState()
        }
    }

    private fun onSessionComplete() {
        if (_isFocusSession.value) {
            _pomodoroCount.value += 1
            val breakTime = when {
                _pomodoroCount.value % 8 == 0 -> longBreak
                _pomodoroCount.value % 4 == 0 -> midBreak
                else -> shortBreak
            }
            _currentTimer.value = breakTime
            _isFocusSession.value = false
        } else {
            _currentTimer.value = focusTime
            _isFocusSession.value = true
        }
        // Automatically start the next session.
        startTimer()
    }

    private suspend fun storeTimerState(targetTime: Long) {
        context.dataStore.edit { preferences ->
            preferences[TIMER_RUNNING_KEY] = true
            preferences[TARGET_TIME_KEY] = targetTime
            preferences[IS_FOCUS_KEY] = _isFocusSession.value
            preferences[POMODORO_COUNT_KEY] = _pomodoroCount.value
        }
    }

    private suspend fun clearTimerState() {
        context.dataStore.edit { preferences ->
            preferences[TIMER_RUNNING_KEY] = false
            preferences[TARGET_TIME_KEY] = 0L
        }
    }

    private fun triggerNotificationAndSound() {
        val workRequest = OneTimeWorkRequestBuilder<PomodoroNotificationWorker>().build()
        WorkManager.getInstance(context).enqueue(workRequest)
    }

    fun onUIAction(action: PomodoroScreenUIAction) {
        viewModelScope.launch {
            _uiActionFlow.emit(action)
        }
    }

    // Update and persist the task title.
    fun onTaskTitleChanged(newTitle: String) {
        _taskTitle.value = newTitle
        viewModelScope.launch {
            context.dataStore.edit { preferences ->
                preferences[TASK_TITLE_KEY] = newTitle
            }
        }
    }
}
