package io.duckcat.d.presentation.boarding.user

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Immutable
import io.duckcat.d.R
import io.duckcat.d.core.etc.UiString

@Immutable
sealed class UserScreenSegment(
    val title: UiString,
    val description: UiString,
    @DrawableRes val icon: Int
) {

    data object Username : UserScreenSegment(
        title = UiString.StringResource(R.string.user_screen_set_username_title),
        description = UiString.StringResource(R.string.user_screen_set_username_description),
        icon = R.drawable.username
    )

    data object UsualWakeUpTime : UserScreenSegment(
        title = UiString.StringResource(R.string.user_screen_set_wakeup_time_title),
        description = UiString.StringResource(R.string.user_screen_set_wakeup_time_description),
        icon = R.drawable.wake_up
    )

    data object UsualBedTime : UserScreenSegment(
        title = UiString.StringResource(R.string.user_screen_set_bedtime_title),
        description = UiString.StringResource(R.string.user_screen_set_bedtime_description),
        icon = R.drawable.bed
    )

    data object Greet : UserScreenSegment(
        title = UiString.StringResource(R.string.user_screen_greet_title),
        description = UiString.StringResource(R.string.user_screen_greet_description),
        icon = R.drawable.trans_logo
    )


}