package io.proxima.breathe.presentation.main.productivity

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
    )
    // Add other intervals if needed.
}
