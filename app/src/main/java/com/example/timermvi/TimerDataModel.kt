package com.example.timermvi

data class TimerDataModel(
    val hour: Int = 0,
    val time: Int = 0,
    val seconds: Int = 0,
    val isRunning: Boolean = false
)


sealed class TimerIntent {
    data object Start: TimerIntent()
    data object Stop: TimerIntent()
    data object Reset: TimerIntent()
}