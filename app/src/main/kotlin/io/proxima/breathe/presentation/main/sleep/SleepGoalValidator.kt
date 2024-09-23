package io.proxima.breathe.presentation.main.sleep

import io.proxima.breathe.core.etc.UiString
import kotlin.time.Duration
import kotlin.time.Duration.Companion.hours

object SleepGoalValidator {

    private val MIN_SLEEP_DURATION = 6.hours.inWholeMilliseconds
    private val MAX_SLEEP_DURATION = 9.hours.inWholeMilliseconds

    fun validate(
        hours: String,
        minutes: String
    ): SleepGoalValidatorResult {

        val duration = convertHoursAndMinutesStringToMillis(hours, minutes)

        return when {
            duration < MIN_SLEEP_DURATION -> SleepGoalValidatorResult.ShortDuration
            duration > MAX_SLEEP_DURATION -> SleepGoalValidatorResult.LongDuration
            else -> SleepGoalValidatorResult.Valid
        }

    }

}

fun convertHoursAndMinutesStringToMillis(
    hours: String,
    minutes: String
): Long {
    fun String.trimZero() = (if (this.length > 1 && startsWith('0')) drop(1) else this).toInt()
    val hoursInt = hours.trimZero()
    val minutesInt = minutes.trimZero()
    return Duration.parse("${hoursInt}h ${minutesInt}m").inWholeMilliseconds
}

sealed class SleepGoalValidatorResult(
    val error: UiString
) {

    data object ShortDuration : SleepGoalValidatorResult(
        error = UiString.BasicString(
            "The sleep goal duration must be at least 6h"
        )
    )

    data object LongDuration : SleepGoalValidatorResult(
        error = UiString.BasicString(
            "The sleep goal duration must be up to 9h"
        )
    )

    data object Valid : SleepGoalValidatorResult(UiString.Empty)

}