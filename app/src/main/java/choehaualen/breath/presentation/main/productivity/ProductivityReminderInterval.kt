package choehaualen.breath.presentation.main.productivity

import androidx.compose.runtime.Immutable
import kotlin.time.Duration.Companion.minutes

@Immutable
enum class ProductivityReminderInterval(
    val inMilliseconds: Long,
    val text: String
) {

    FortyFiveMinutes(
        inMilliseconds = 45.minutes.inWholeMilliseconds,
        text = "45m"
    ),

    SixtyMinutes(
        inMilliseconds = 60.minutes.inWholeMilliseconds,
        text = "60m"
    ),

    SeventyFiveMinutes(
        inMilliseconds = 75.minutes.inWholeMilliseconds,
        text = "75m"
    ),

    NinetyMinutes(
        inMilliseconds = 90.minutes.inWholeMilliseconds,
        text = "90m"
    );

    companion object {

        fun ProductivityReminderInterval.stepDown(
            minInterval: ProductivityReminderInterval
        ) = if (this != minInterval) when (this) {
            FortyFiveMinutes -> FortyFiveMinutes
            SixtyMinutes -> FortyFiveMinutes
            SeventyFiveMinutes -> SixtyMinutes
            NinetyMinutes -> SeventyFiveMinutes
        } else this

        fun ProductivityReminderInterval.stepUp(
            maxInterval: ProductivityReminderInterval
        ) = if (this != maxInterval) when (this) {
            NinetyMinutes -> NinetyMinutes
            SeventyFiveMinutes -> NinetyMinutes
            SixtyMinutes -> SeventyFiveMinutes
            FortyFiveMinutes -> SixtyMinutes
        } else maxInterval

    }

}