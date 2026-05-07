package app.marlboroadvance.mpvex.ui.player.features

import `is`.xyz.mpv.MPVLib
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SleepTimerManager(private val scope: CoroutineScope) {

    private var timerJob: Job? = null

    private val _remainingSeconds = MutableStateFlow(0)
    val remainingSeconds: StateFlow<Int> = _remainingSeconds.asStateFlow()

    private val _isActive = MutableStateFlow(false)
    val isActive: StateFlow<Boolean> = _isActive.asStateFlow()

    var onTimerFinished: (() -> Unit)? = null

    fun start(durationMinutes: Int) {
        cancel()
        val totalSeconds = durationMinutes * 60
        _remainingSeconds.value = totalSeconds
        _isActive.value = true

        timerJob = scope.launch {
            for (remaining in totalSeconds downTo 0) {
                _remainingSeconds.value = remaining
                if (remaining == 0) break
                delay(1000L)
            }
            MPVLib.setPropertyBoolean("pause", true)
            _isActive.value = false
            onTimerFinished?.invoke()
        }
    }

    fun cancel() {
        timerJob?.cancel()
        timerJob = null
        _remainingSeconds.value = 0
        _isActive.value = false
    }

    fun formatRemaining(): String {
        val s = _remainingSeconds.value
        val m = s / 60
        val sec = s % 60
        return "%d:%02d".format(m, sec)
    }
}
