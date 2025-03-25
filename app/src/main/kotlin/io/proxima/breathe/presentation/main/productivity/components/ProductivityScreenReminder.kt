package io.proxima.breathe.presentation.main.productivity.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import io.proxima.breathe.core.ui.components.rememberBreathRipple
import io.proxima.breathe.core.ui.theme.BreathTheme
import io.proxima.breathe.presentation.main.productivity.ProductivityScreenUIAction
import sv.lib.squircleshape.SquircleShape

@Composable
fun ProductivityScreenReminder(
    modifier: Modifier = Modifier,
    state: ProductivityScreenReminderState,
    shape: Shape = SquircleShape(24.dp),
    id: String,
    icon: androidx.compose.ui.graphics.painter.Painter,
    label: String,
    description: String,
    onUIAction: (ProductivityScreenUIAction) -> Unit
) {
    Column(
        modifier = modifier
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberBreathRipple(),
                onClick = { onUIAction(ProductivityScreenUIAction.SetReminderEnabled(id, !state.enabled)) }
            )
            .background(BreathTheme.colors.card.copy(alpha = 0.3f), shape)
            .padding(16.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                modifier = Modifier.size(32.dp),
                painter = icon,
                contentDescription = null,
                tint = Color.Black
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(text = label, style = BreathTheme.typography.titleMedium, color = Color.Black)
                Text(text = description, style = BreathTheme.typography.bodyMedium, color = Color.Black)
            }
            Switch(
                checked = state.enabled,
                colors = SwitchDefaults.colors(
                    uncheckedBorderColor = BreathTheme.colors.text.copy(alpha = 0.75f),
                    uncheckedThumbColor = BreathTheme.colors.text,
                    uncheckedTrackColor = BreathTheme.colors.card,
                    checkedTrackColor = Color.Black,
                    checkedThumbColor = BreathTheme.colors.background,
                ),
                onCheckedChange = { enabled ->
                    onUIAction(ProductivityScreenUIAction.SetReminderEnabled(id, enabled))
                }
            )
        }
    }
}
