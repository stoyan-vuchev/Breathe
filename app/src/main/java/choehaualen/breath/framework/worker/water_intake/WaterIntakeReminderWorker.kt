package choehaualen.breath.framework.worker.water_intake

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
import choehaualen.breath.R
import choehaualen.breath.data.preferences.AppPreferences
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import java.time.Instant
import java.time.ZoneId
import java.time.ZonedDateTime
import javax.inject.Inject
import kotlin.random.Random

@HiltWorker
class WaterIntakeReminderWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParameters: WorkerParameters
) : CoroutineWorker(context, workerParameters) {

    @Inject
    lateinit var appPreferences: AppPreferences

    override suspend fun getForegroundInfo(): ForegroundInfo {
        return getForegroundInfo(applicationContext)
    }

    override suspend fun doWork(): Result {
        return try {
            if (ActivityCompat.checkSelfPermission(
                    applicationContext,
                    Manifest.permission.POST_NOTIFICATIONS
                ) == PackageManager.PERMISSION_GRANTED
                && isWithinAwakeTime()
            ) {
                NotificationManagerCompat
                    .from(applicationContext)
                    .notify(Random.nextInt(), createNotificationChannel(applicationContext))
            }
            Result.success()
        } catch (e: Exception) {
            if (runAttemptCount < 10) Result.retry()
            else Result.failure()
        }
    }

    private suspend fun isWithinAwakeTime(): Boolean {

        val now = Instant.ofEpochMilli(System.currentTimeMillis())
        val bedTime = appPreferences.getUsualBedtime()
        val wakeUpTime = appPreferences.getUsualWakeUpTime()

        val currentTime = ZonedDateTime.ofInstant(now, ZoneId.systemDefault())
        val currentHour = currentTime.hour
        val currentMinute = currentTime.minute

        return if (bedTime != null && wakeUpTime != null) {
            currentHour < bedTime.first
                    && currentMinute < bedTime.second
                    && currentHour > wakeUpTime.first
                    && currentMinute > wakeUpTime.second
        } else false

    }

    companion object {

        @Suppress("Deprecation")
        private fun getForegroundInfo(context: Context): ForegroundInfo {

            val foregroundServiceNotification = {

                val channelId = "water_intake_reminder_foreground_channel_id"
                val channelName = "Water intake reminder foreground service"

                val notificationBuilder = NotificationCompat.Builder(context, channelId)
                    .setSmallIcon(R.drawable.water_glass)
                    .setContentText(context.resources.getString(R.string.water_intake_reminder_text))
                    .setContentTitle(context.resources.getString(R.string.water_intake_reminder_title))
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setOngoing(true)
                    .setAutoCancel(true)

                val notificationManager =
                    context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

                val channel = NotificationChannel(
                    channelId,
                    channelName,
                    NotificationManager.IMPORTANCE_HIGH
                )

                notificationManager.createNotificationChannel(channel)
                notificationBuilder.build()

            }

            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
                ForegroundInfo(
                    1,
                    foregroundServiceNotification(),
                    ServiceInfo.FOREGROUND_SERVICE_TYPE_SHORT_SERVICE
                )
            } else ForegroundInfo(
                1,
                foregroundServiceNotification(),
                ServiceInfo.FOREGROUND_SERVICE_TYPE_NONE
            )
        }

        private fun createNotificationChannel(context: Context): Notification {

            val channelId = "water_intake_reminder_channel_id"
            val channelName =
                context.resources.getString(R.string.water_intake_reminder_channel_name)

            val notificationBuilder = NotificationCompat.Builder(context, channelId)
                .setSmallIcon(R.drawable.water_glass)
                .setContentText(context.resources.getString(R.string.water_intake_reminder_text))
                .setContentTitle(context.resources.getString(R.string.water_intake_reminder_title))
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setOngoing(true)
                .setAutoCancel(true)

            val notificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            val channel = NotificationChannel(
                channelId,
                channelName,
                NotificationManager.IMPORTANCE_HIGH
            )

            notificationManager.createNotificationChannel(channel)
            return notificationBuilder.build()

        }


    }

}