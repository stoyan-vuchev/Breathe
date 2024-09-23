package io.proxima.breathe.presentation.main.breathe

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.proxima.breathe.presentation.main.breathe.segment.BreathePhase
import io.proxima.breathe.presentation.main.breathe.segment.BreatheScreenSegmentState
import io.proxima.breathe.presentation.main.breathe.segment.BreatheScreenSegmentType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.time.Duration.Companion.seconds

@HiltViewModel
class BreatheScreenViewModel @Inject constructor() : ViewModel() {

    private val _screenState = MutableStateFlow(BreatheScreenState())
    val screenState = _screenState.asStateFlow()

    private val _uiActionChannel = Channel<BreatheScreenUIAction>()
    val uiActionFlow = _uiActionChannel.receiveAsFlow()

    private var segmentJob: Job? = null

    fun onUIAction(uiAction: BreatheScreenUIAction) = when (uiAction) {
        is BreatheScreenUIAction.NavigateUp -> sendUIAction(uiAction)
        is BreatheScreenUIAction.SetSegment -> setSegment(uiAction.segment)
        is BreatheScreenUIAction.StartSegment -> startSegment(uiAction.segment)
        is BreatheScreenUIAction.ClearSegment -> clearSegment()
    }

    private fun setSegment(segmentType: BreatheScreenSegmentType) {
        _screenState.update { currentState ->
            currentState.copy(segmentType = segmentType)
        }
    }

    private fun sendUIAction(uiAction: BreatheScreenUIAction) {
        viewModelScope.launch { _uiActionChannel.send(uiAction) }
    }

    private fun startSegment(segmentType: BreatheScreenSegmentType) {

        _screenState.update { it.copy(segmentState = it.segmentState.copy(isRunning = true)) }
        segmentJob?.cancel(); segmentJob = null

        when (segmentType) {
            is BreatheScreenSegmentType.DeepBreathing -> initializeDeepBreathing()
            is BreatheScreenSegmentType.AlertMode -> initializeAlertMode()
            is BreatheScreenSegmentType.CalmMode -> initializeCalmMode()
            else -> Unit
        }

    }

    private fun clearSegment() {
        segmentJob?.cancel(); segmentJob = null
        _screenState.update { it.copy(segmentState = BreatheScreenSegmentState()) }
    }

    private fun initializeDeepBreathing() {
        segmentJob = viewModelScope.launch {
            withContext(Dispatchers.Default) {

                delay(500L)

                while (_screenState.value.segmentState.isRunning) {
                    _screenState.update {
                        it.copy(
                            segmentState = it.segmentState.copy(
                                phase = BreathePhase.Inhale(1f),
                                modeDurationInSeconds = 5
                            )
                        )
                    }
                    countDown()
                    _screenState.update {
                        it.copy(
                            segmentState = it.segmentState.copy(
                                phase = BreathePhase.Exhale(0f),
                                modeDurationInSeconds = 5
                            )
                        )
                    }
                    countDown()
                }
            }
        }

    }

    private fun initializeAlertMode() {
        segmentJob = viewModelScope.launch {
            withContext(Dispatchers.Default) {

                delay(500L)

                while (_screenState.value.segmentState.isRunning) {
                    _screenState.update {
                        it.copy(
                            segmentState = it.segmentState.copy(
                                phase = BreathePhase.Inhale(1f),
                                modeDurationInSeconds = 5
                            )
                        )
                    }
                    countDown(5)
                    _screenState.update {
                        it.copy(
                            segmentState = it.segmentState.copy(
                                phase = BreathePhase.Exhale(0f),
                                modeDurationInSeconds = 2
                            )
                        )
                    }
                    countDown(2)
                }
            }
        }

    }

    private fun initializeCalmMode() {
        segmentJob = viewModelScope.launch {
            withContext(Dispatchers.Default) {

                delay(500L)

                while (_screenState.value.segmentState.isRunning) {
                    _screenState.update {
                        it.copy(
                            segmentState = it.segmentState.copy(
                                phase = BreathePhase.Inhale(.5f),
                                modeDurationInSeconds = 1
                            )
                        )
                    }
                    countDown(1)
                    _screenState.update {
                        it.copy(
                            segmentState = it.segmentState.copy(
                                phase = BreathePhase.ForceInhale(1f),
                                modeDurationInSeconds = 1
                            )
                        )
                    }
                    countDown(1)
                    _screenState.update {
                        it.copy(
                            segmentState = it.segmentState.copy(
                                phase = BreathePhase.Exhale(0f),
                                modeDurationInSeconds = 5
                            )
                        )
                    }
                    countDown()
                }
            }
        }

    }

    private suspend fun countDown(duration: Int = 5) {
        for (i in duration downTo 1) {
            _screenState.update { it.copy(segmentState = it.segmentState.copy(countDown = i)) }
            delay(1.seconds)
        }
    }

}