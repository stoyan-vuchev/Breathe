package choehaualen.breath.presentation.main.productivity

import androidx.compose.runtime.Stable

@Stable
data class ProductivityScreenState(
    val remindersList: List<ProductivityReminder> = emptyList()
)