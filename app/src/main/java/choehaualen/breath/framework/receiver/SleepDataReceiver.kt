package choehaualen.breath.framework.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import choehaualen.breath.data.local.entity.SleepEntity
import choehaualen.breath.data.manager.SleepManager
import com.google.android.gms.location.SleepSegmentEvent
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class SleepDataReceiver : BroadcastReceiver(), KoinComponent {

    private val sleepManager by inject<SleepManager>()

    @OptIn(DelicateCoroutinesApi::class)
    override fun onReceive(context: Context?, intent: Intent?) {

        intent?.let { safeIntent ->

            if (SleepSegmentEvent.hasEvents(safeIntent)) {

                val scope = GlobalScope
                val sleepData = SleepSegmentEvent.extractEvents(safeIntent)
                    .map {

                        val sleepStartTimestamp = it.startTimeMillis
                        val sleepEndTimestamp = it.endTimeMillis
                        val totalSleepDuration = sleepEndTimestamp - sleepStartTimestamp

                        SleepEntity(
                            sleepStartTimestamp = sleepStartTimestamp,
                            sleepEndTimestamp = sleepEndTimestamp,
                            totalSleepDuration = totalSleepDuration
                        )

                    }

                sleepData.forEach { entity ->
                    scope.launch { sleepManager.saveSleepData(sleepEntity = entity) }
                        .invokeOnCompletion { scope.cancel() }

                }

            }

        }

    }

}