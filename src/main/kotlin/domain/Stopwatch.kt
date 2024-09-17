package domain

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*

class Stopwatch {

    private val _formattedTimeString = MutableStateFlow(formattedTime(0L))
    val formattedTimeString = _formattedTimeString.asStateFlow()

    private var coroutineScope = CoroutineScope(Dispatchers.Default)
    private var isActive = false

    private var timeMillis = 0L
    private var lastTimestamp = 0L

    fun start() {
        if (isActive) return

        coroutineScope.launch {
            lastTimestamp = System.currentTimeMillis()
            this@Stopwatch.isActive = true
            while (this@Stopwatch.isActive) {
                delay(10)
                timeMillis += System.currentTimeMillis() - lastTimestamp
                lastTimestamp = System.currentTimeMillis()
                _formattedTimeString.update { formattedTime(timeMillis) }
            }
        }
    }

    fun pause() {
        isActive = false
    }

    fun reset() {
        coroutineScope.cancel()
        coroutineScope = CoroutineScope(Dispatchers.Default)
        timeMillis = 0L
        lastTimestamp = 0L
        _formattedTimeString.update { formattedTime(0L) }
        isActive = false
    }

    private fun formattedTime(timeMillis: Long): String {
        val localDateTime = LocalDateTime.ofInstant(
            Instant.ofEpochMilli(timeMillis),
            ZoneId.systemDefault()
        )
        val formatter = DateTimeFormatter.ofPattern(
            "mm:ss:SSS",
            Locale.getDefault()
        )
        return localDateTime.format(formatter)
    }

}