package io.proxima.breathe.presentation.main.productivity

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.runtime.Stable

@Stable
data class ProductivityReminder(
    val id: String, // e.g. id = ProductivityReminders.WaterIntake
    @StringRes val name: Int,
    @DrawableRes val icon: Int,
    @StringRes val description: Int,
    val interval: Long?,
    val enabled: Boolean
)