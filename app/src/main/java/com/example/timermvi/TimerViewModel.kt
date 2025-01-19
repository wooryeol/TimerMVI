package com.example.timermvi

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class TimerViewModel: ViewModel() {

    private val _data = MutableStateFlow(TimerDataModel())
    val data: StateFlow<TimerDataModel> = _data

    private var timerJob: Job? = null

    fun onIntent(intent: TimerIntent) {
        when (intent) {
            is TimerIntent.Start -> startTimer()
            is TimerIntent.Stop -> stopTimer()
            is TimerIntent.Reset -> resetTimer()
        }
    }

    private fun startTimer() {
        if (_data.value.isRunning) return
        _data.value = _data.value.copy(isRunning = true)

        timerJob = viewModelScope.launch {

            while (_data.value.isRunning) {
                delay(10)
                _data.value = _data.value.copy(seconds = _data.value.seconds + 1)

                if (_data.value.time == 60) {
                    _data.value = _data.value.copy(time = 0 )
                    _data.value = _data.value.copy(hour = 1)
                }

                if (_data.value.seconds == 100) {
                    _data.value = _data.value.copy(seconds = 0)
                    _data.value = _data.value.copy(time = _data.value.time + 1)
                }
            }
        }
    }

    private fun stopTimer() {
        _data.value = _data.value.copy(isRunning = false)
        timerJob?.cancel()
    }

    private fun resetTimer() {
        _data.value = _data.value.copy(time = 0, seconds = 0, isRunning = false)
        timerJob?.cancel()
    }
}