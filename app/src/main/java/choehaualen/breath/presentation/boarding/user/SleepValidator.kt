package choehaualen.breath.presentation.boarding.user

import androidx.annotation.StringRes
import choehaualen.breath.R
import kotlin.time.Duration
import kotlin.time.Duration.Companion.hours
import kotlin.time.Duration.Companion.minutes

object SleepValidator {

    private val MIN_SLEEP_DURATION: Duration = 6.hours
    private val MAX_SLEEP_DURATION: Duration = 10.hours

    fun validateSleepDuration(
        bedtimeHour: Int,
        bedtimeMinute: Int,
        wakeUpHour: Int,
        wakeUpMinute: Int
    ): SleepValidationResult {

        val bedtime = bedtimeHour.hours + bedtimeMinute.minutes
        val wakeUp = wakeUpHour.hours + wakeUpMinute.minutes

        val sleepDuration = if (wakeUp > bedtime) wakeUp - bedtime
        else (24.hours - bedtime) + wakeUp

        return when {
            sleepDuration < MIN_SLEEP_DURATION -> SleepValidationResult.ShortSleepDuration
            sleepDuration > MAX_SLEEP_DURATION -> SleepValidationResult.LongSleepDuration
            else -> SleepValidationResult.ValidSleepDuration
        }

    }

}

sealed class SleepValidationResult(
    @StringRes val errorMessage: Int?
) {

    data object ShortSleepDuration : SleepValidationResult(
        errorMessage = R.string.sleep_validation_short_duration
    )

    data object LongSleepDuration : SleepValidationResult(
        errorMessage = R.string.sleep_validation_long_duration
    )

    data object ValidSleepDuration : SleepValidationResult(
        errorMessage = null
    )

}