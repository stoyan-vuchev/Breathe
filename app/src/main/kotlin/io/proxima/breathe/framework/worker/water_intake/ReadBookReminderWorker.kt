package io.proxima.breathe.framework.worker.productivity

import android.Manifest
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.content.pm.ServiceInfo
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.ForegroundInfo
import androidx.work.WorkerParameters
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import io.proxima.breathe.R
import io.proxima.breathe.data.preferences.AppPreferences
import java.util.concurrent.TimeUnit
import kotlin.random.Random

@HiltWorker
class ReadBookReminderWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParameters: WorkerParameters
) : CoroutineWorker(context, workerParameters) {

    // Optionally inject preferences if needed
    // @Inject lateinit var appPreferences: AppPreferences

    override suspend fun getForegroundInfo(): ForegroundInfo {
        return getForegroundInfo(applicationContext)
    }

    override suspend fun doWork(): Result {
        return try {
            if (ActivityCompat.checkSelfPermission(
                    applicationContext,
                    Manifest.permission.POST_NOTIFICATIONS
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                NotificationManagerCompat.from(applicationContext)
                    .notify(Random.nextInt(), createNotificationChannel(applicationContext))
            }
            Result.success()
        } catch (e: Exception) {
            if (runAttemptCount < 10) Result.retry() else Result.failure()
        }
    }

    companion object {
        @Suppress("Deprecation")
        private fun getForegroundInfo(context: Context): ForegroundInfo {
            val notification = foregroundServiceNotification(context)
            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
                ForegroundInfo(1, notification, ServiceInfo.FOREGROUND_SERVICE_TYPE_SHORT_SERVICE)
            } else {
                ForegroundInfo(1, notification, ServiceInfo.FOREGROUND_SERVICE_TYPE_NONE)
            }
        }

        private fun foregroundServiceNotification(context: Context): Notification {
            val channelId = "read_book_reminder_foreground_channel_id"
            val channelName = "Read Book Reminder Foreground Service"
            val builder = NotificationCompat.Builder(context, channelId)
                .setSmallIcon(R.drawable.book) // Replace with your drawable
                .setContentTitle(context.getString(R.string.read_book_reminder_title))
                .setContentText(context.getString(R.string.read_book_reminder_text))
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setOngoing(true)
                .setAutoCancel(true)
            val notificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            val channel = NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_HIGH)
            notificationManager.createNotificationChannel(channel)
            return builder.build()
        }

        private fun createNotificationChannel(context: Context): Notification {
            val channelId = "read_book_reminder_channel_id"
            val channelName = context.getString(R.string.read_book_reminder_channel_name)
            val builder = NotificationCompat.Builder(context, channelId)
                .setSmallIcon(R.drawable.book) // Replace with your drawable
                .setContentTitle(context.getString(R.string.read_book_reminder_title))
                .setContentText(context.getString(R.string.read_book_reminder_text))
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setOngoing(true)
                .setAutoCancel(true)
            val notificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            val channel = NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_HIGH)
            notificationManager.createNotificationChannel(channel)
            return builder.build()
        }
    }
}
