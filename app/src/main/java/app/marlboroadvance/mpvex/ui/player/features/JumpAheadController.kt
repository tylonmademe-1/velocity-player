package app.marlboroadvance.mpvex.ui.player.features

import `is`.xyz.mpv.MPVLib

class JumpAheadController {

    data class Timestamp(val label: String, val positionMs: Long)

    private val timestamps = mutableListOf<Timestamp>()

    fun setFromChapters(chapters: List<dev.vivvvek.seeker.Segment>) {
        timestamps.clear()
        chapters.forEach { segment ->
            timestamps.add(Timestamp(segment.name, (segment.start * 1000).toLong()))
        }
    }

    fun addTimestamp(label: String, positionMs: Long) {
        timestamps.add(Timestamp(label, positionMs))
    }

    fun clear() = timestamps.clear()

    fun getAll(): List<Timestamp> = timestamps.toList()

    fun jumpTo(label: String) {
        val ts = timestamps.firstOrNull { it.label == label } ?: return
        MPVLib.setPropertyInt("time-pos", (ts.positionMs / 1000).toInt())
    }

    fun jumpToNext() {
        val currentMs = (MPVLib.getPropertyDouble("time-pos") ?: 0.0) * 1000
        val next = timestamps.firstOrNull { it.positionMs > currentMs } ?: return
        MPVLib.setPropertyInt("time-pos", (next.positionMs / 1000).toInt())
    }

    fun jumpToPrevious() {
        val currentMs = (MPVLib.getPropertyDouble("time-pos") ?: 0.0) * 1000
        val prev = timestamps.lastOrNull { it.positionMs < currentMs - 2000 } ?: return
        MPVLib.setPropertyInt("time-pos", (prev.positionMs / 1000).toInt())
    }
}
