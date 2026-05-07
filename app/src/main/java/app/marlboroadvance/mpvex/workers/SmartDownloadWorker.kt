package app.marlboroadvance.mpvex.workers

import android.content.Context
import android.os.Environment
import androidx.work.Constraints
import androidx.work.Data
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequest
import androidx.work.Worker
import androidx.work.WorkerParameters
import androidx.work.WorkManager
import java.io.File
import java.net.HttpURLConnection
import java.net.URL

class SmartDownloadWorker(
    context: Context,
    params: WorkerParameters,
) : Worker(context, params) {

    companion object {
        const val KEY_URL = "download_url"
        const val KEY_TITLE = "download_title"
        const val KEY_WIFI_ONLY = "wifi_only"

        fun enqueue(context: Context, url: String, title: String, wifiOnly: Boolean = true) {
            val data = Data.Builder()
                .putString(KEY_URL, url)
                .putString(KEY_TITLE, title)
                .putBoolean(KEY_WIFI_ONLY, wifiOnly)
                .build()

            val constraints = Constraints.Builder()
                .setRequiredNetworkType(
                    if (wifiOnly) NetworkType.UNMETERED else NetworkType.CONNECTED
                )
                .setRequiresStorageNotLow(true)
                .build()

            val request = OneTimeWorkRequest.Builder(SmartDownloadWorker::class.java)
                .setInputData(data)
                .setConstraints(constraints)
                .addTag("velocity_download")
                .build()

            WorkManager.getInstance(context).enqueue(request)
        }
    }

    override fun doWork(): Result {
        val url = inputData.getString(KEY_URL) ?: return Result.failure()
        val title = inputData.getString(KEY_TITLE) ?: url.substringAfterLast("/")

        return try {
            val destDir = File(
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES),
                "Velocity"
            ).also { it.mkdirs() }

            val ext = url.substringAfterLast(".").substringBefore("?").take(5)
            val fileName = "${title.take(60)}.${if (ext.isBlank()) "mp4" else ext}"
            val destFile = File(destDir, fileName)

            if (destFile.exists()) return Result.success()

            val connection = URL(url).openConnection() as HttpURLConnection
            connection.connectTimeout = 15_000
            connection.readTimeout = 30_000
            connection.connect()

            if (connection.responseCode != HttpURLConnection.HTTP_OK) {
                return Result.retry()
            }

            connection.inputStream.use { input ->
                destFile.outputStream().use { output ->
                    input.copyTo(output, bufferSize = 8192)
                }
            }

            Result.success()
        } catch (e: Exception) {
            Result.retry()
        }
    }
}
