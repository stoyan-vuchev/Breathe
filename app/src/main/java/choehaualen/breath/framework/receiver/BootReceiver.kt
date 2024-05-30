package choehaualen.breath.framework.receiver

import android.Manifest
import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import choehaualen.breath.framework.receiver.SleepDataReceiver.Companion.createSleepReceiverPendingIntent
import com.google.android.gms.location.ActivityRecognition
import com.google.android.gms.location.SleepSegmentRequest

class BootReceiver : BroadcastReceiver() {

    @SuppressLint("UnsafeProtectedBroadcastReceiver")
    override fun onReceive(context: Context?, intent: Intent?) {
        if (context != null) {
            if (
                ActivityCompat.checkSelfPermission(
                    context, Manifest.permission.ACTIVITY_RECOGNITION
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                ActivityRecognition
                    .getClient(context)
                    .requestSleepSegmentUpdates(
                        createSleepReceiverPendingIntent(context),
                        SleepSegmentRequest.getDefaultSleepSegmentRequest()
                    )
            }
        }
    }

}