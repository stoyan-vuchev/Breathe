package io.proxima.breathe.receiver

import android.content.Context
import android.content.Intent
import android.os.Parcel
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import assertk.assertThat
import assertk.assertions.isEqualTo
import io.proxima.breathe.data.local.entity.SleepEntity
import io.proxima.breathe.data.manager.SleepManager
import com.google.android.gms.location.SleepSegmentEvent
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import io.proxima.breathe.framework.receiver.SleepDataReceiver
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject
import kotlin.time.Duration.Companion.hours

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class SleepDataReceiverTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var sleepManager: SleepManager

    @Inject
    lateinit var ioDispatcher: TestDispatcher

    @Before
    fun setUp() {
        hiltRule.inject()
    }

    @Test
    fun receive_sleep_segment() = runTest(ioDispatcher) {

        val context = ApplicationProvider.getApplicationContext<Context>().applicationContext
        val intent = Intent(context, SleepDataReceiver::class.java)

        val startTime = System.currentTimeMillis() - 8.hours.inWholeMilliseconds
        val endTime = startTime + 8.hours.inWholeMilliseconds

        val status = SleepSegmentEvent.STATUS_SUCCESSFUL
        val sleepSegmentEvent = SleepSegmentEvent(startTime, endTime, status, 0, 0)

        val parcel = Parcel.obtain()
        sleepSegmentEvent.writeToParcel(parcel, 0)
        parcel.setDataPosition(0)

        val parcelableEvents = arrayOfNulls<SleepSegmentEvent>(1)
        parcelableEvents[0] = SleepSegmentEvent.CREATOR.createFromParcel(parcel)
        parcel.recycle()

        // Add the parcelable data to the intent extras
        intent.putExtra(
            "com.google.android.gms.location.SleepSegmentEvent",
            parcelableEvents
        )

        // Send the broadcast
        val receiver = SleepDataReceiver().also {
            SleepDataReceiver.Companion.registerForSleepUpdates(
                context
            )
        }

        receiver.onReceive(context, intent)

        // Wait for coroutines to complete
        delay(2000)

        val actual = sleepManager.readSleepData().data!!
        val expected = listOf(
            SleepEntity(
                sleepStartTimestamp = sleepSegmentEvent.startTimeMillis,
                sleepEndTimestamp = sleepSegmentEvent.endTimeMillis,
                totalSleepDuration = sleepSegmentEvent.segmentDurationMillis
            )
        )

        assertThat(actual).isEqualTo(expected)

    }

}