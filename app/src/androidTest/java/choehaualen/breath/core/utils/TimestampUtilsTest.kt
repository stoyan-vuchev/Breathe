package choehaualen.breath.core.utils

import androidx.test.ext.junit.runners.AndroidJUnit4
import assertk.assertThat
import assertk.assertions.isEqualTo
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.junit.runner.RunWith
import kotlin.math.exp
import kotlin.time.Duration.Companion.hours

@RunWith(AndroidJUnit4::class)
class TimestampUtilsTest {

    @Test
    fun find_hours_difference() = runTest {

        val nowInMillis = System.currentTimeMillis()
        val eightHoursFromNowInMillis = nowInMillis + 8.hours.inWholeMilliseconds

        val expected = 8
        val actual = TimestampUtils.findHoursDifference(
            start = nowInMillis,
            end = eightHoursFromNowInMillis
        )

        assertThat(actual).isEqualTo(expected)

    }

}