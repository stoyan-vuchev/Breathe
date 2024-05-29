package choehaualen.breath.framework.receiver

import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import com.google.android.gms.location.ActivityRecognition
import com.google.android.gms.location.SleepSegmentRequest

class BootReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        if (context != null) {
            ActivityRecognition
                .getClient(context)
                .requestSleepSegmentUpdates(
                    createSleepReceiverPendingIntent(context),
                    SleepSegmentRequest.getDefaultSleepSegmentRequest()
                )
        }
    }

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

}