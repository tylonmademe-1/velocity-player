package app.marlboroadvance.mpvex.ui.player.ambient

import android.graphics.Bitmap
import android.graphics.Color
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.palette.graphics.Palette
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class AmbientModeManager(private val glowView: View) {

    private val executor: ExecutorService = Executors.newSingleThreadExecutor()
    private val mainHandler = Handler(Looper.getMainLooper())
    private var isRunning = false
    private var sampleTask: Runnable? = null

    companion object {
        private const val SAMPLE_INTERVAL_MS = 500L // safe for 120Hz — no frame impact
        private const val EDGE_SAMPLE_SIZE = 32     // tiny bitmap for speed
    }

    fun start(videoSurface: View) {
        if (isRunning) return
        isRunning = true
        sampleTask = object : Runnable {
            override fun run() {
                if (!isRunning) return
                sampleAndApply(videoSurface)
                mainHandler.postDelayed(this, SAMPLE_INTERVAL_MS)
            }
        }
        mainHandler.post(sampleTask!!)
    }

    fun stop() {
        isRunning = false
        sampleTask?.let { mainHandler.removeCallbacks(it) }
        sampleTask = null
        // Reset glow
        mainHandler.post { glowView.animate().alpha(0f).setDuration(400).start() }
    }

    private fun sampleAndApply(videoSurface: View) {
        executor.execute {
            try {
                if (videoSurface.width <= 0 || videoSurface.height <= 0) return@execute

                val full = Bitmap.createBitmap(
                    videoSurface.width, videoSurface.height, Bitmap.Config.ARGB_8888
                )
                val canvas = android.graphics.Canvas(full)
                videoSurface.draw(canvas)

                val small = Bitmap.createScaledBitmap(full, EDGE_SAMPLE_SIZE, EDGE_SAMPLE_SIZE, false)
                full.recycle()

                val palette = Palette.from(small).generate()
                small.recycle()

                val dominant = palette.getDominantColor(Color.BLACK)
                val vibrant = palette.getVibrantColor(dominant)

                mainHandler.post { applyGlow(vibrant) }
            } catch (_: Exception) {
                // Surface not ready — skip frame silently
            }
        }
    }

    private fun applyGlow(color: Int) {
        val hsv = FloatArray(3)
        Color.colorToHSV(color, hsv)
        hsv[1] = hsv[1].coerceAtMost(0.65f) // cap saturation
        hsv[2] = hsv[2].coerceAtMost(0.45f) // cap brightness
        val softColor = Color.HSVToColor(160, hsv) // ~63% alpha

        glowView.alpha = 1f
        glowView.setBackgroundColor(softColor)
        glowView.animate()
            .alpha(1f)
            .setDuration(400)
            .start()
    }

    fun release() {
        stop()
        executor.shutdown()
    }
}
