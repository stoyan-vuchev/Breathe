package io.proxima.breathe.presentation.main.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp
import io.proxima.breathe.core.ui.components.rememberBreathRipple
import io.proxima.breathe.core.ui.theme.BreathTheme
import sv.lib.squircleshape.SquircleShape

@Composable
fun SettingsScreenCategoryItem(
    modifier: Modifier = Modifier,
    shape: Shape = SquircleShape(24.dp),
    icon: Painter,
    label: String,
    textColor: Color = BreathTheme.colors.text,
    onClick: () -> Unit
) = Row(
    modifier = modifier
        .clip(shape)
        .background(BreathTheme.colors.card)
        .clickable(
            interactionSource = remember { MutableInteractionSource() },
            indication = rememberBreathRipple(),
            onClick = onClick
        )
        .then(Modifier.padding(16.dp)),
    verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = Arrangement.spacedBy(16.dp)
) {

    Icon(
        modifier = Modifier.size(20.dp),
        painter = icon,
        contentDescription = null,
        tint = BreathTheme.colors.text
    )

    Text(
        text = label,
        style = BreathTheme.typography.labelLarge,
        color = textColor
    )

}