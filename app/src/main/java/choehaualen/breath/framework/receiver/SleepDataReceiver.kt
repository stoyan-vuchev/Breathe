package choehaualen.breath.framework.receiver

import android.Manifest
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat
import choehaualen.breath.data.local.entity.SleepEntity
import choehaualen.breath.data.manager.SleepManager
import com.google.android.gms.location.ActivityRecognition
import com.google.android.gms.location.SleepSegmentEvent
import com.google.android.gms.location.SleepSegmentRequest
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class SleepDataReceiver : BroadcastReceiver() {

    @Inject
    lateinit var sleepManager: SleepManager

    override fun onReceive(context: Context?, intent: Intent?) {

        intent?.let { safeIntent ->

            if (SleepSegmentEvent.hasEvents(safeIntent)) {

                val scope = CoroutineScope(Dispatchers.Default)
                val sleepData = SleepSegmentEvent.extractEvents(safeIntent)
                    .map { receivedSegmentEvent ->

                        val sleepStartTimestamp = receivedSegmentEvent.startTimeMillis
                        val sleepEndTimestamp = receivedSegmentEvent.endTimeMillis
                        val totalSleepDuration = (sleepEndTimestamp - sleepStartTimestamp)

                        SleepEntity(
                            sleepStartTimestamp = sleepStartTimestamp,
                            sleepEndTimestamp = sleepEndTimestamp,
                            totalSleepDuration = totalSleepDuration
                        )

                    }

                scope.launch { sleepManager.saveSleepData(sleepData) }

            }

        }

    }

    companion object {

        fun registerForSleepUpdates(context: Context?) = context?.let {
            if (ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.ACTIVITY_RECOGNITION
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                ActivityRecognition
                    .getClient(context)
                    .requestSleepSegmentUpdates(
                        createSleepReceiverPendingIntent(context),
                        SleepSegmentRequest.getDefaultSleepSegmentRequest()
                    )
            }
        } ?: Unit

        private fun createSleepReceiverPendingIntent(context: Context): PendingIntent {

            // Prepare intent flags
            val flags = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                PendingIntent.FLAG_CANCEL_CURRENT or PendingIntent.FLAG_MUTABLE
            } else {
                PendingIntent.FLAG_CANCEL_CURRENT
            }

            // Create the PendingIntent
            return PendingIntent.getBroadcast(
                context,
                0,
                Intent(context, SleepDataReceiver::class.java),
                flags
            )

        }

        fun stopSleepReceiver(context: Context): PendingIntent {
            return PendingIntent.getBroadcast(
                context,
                0,
                Intent(context, SleepDataReceiver::class.java),
                PendingIntent.FLAG_CANCEL_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
        }

    }

}