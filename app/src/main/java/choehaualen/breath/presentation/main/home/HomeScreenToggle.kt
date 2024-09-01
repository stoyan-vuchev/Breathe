package choehaualen.breath.presentation.main.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import choehaualen.breath.core.ui.components.rememberBreathRipple
import choehaualen.breath.core.ui.theme.BreathTheme
import sv.lib.squircleshape.SquircleShape

@Composable
fun HomeScreenToggle(
    modifier: Modifier = Modifier,
    icon: Painter,
    label: String,
    boundlessIcon: Boolean = false,
    boundlessIconSize: DpSize = DpSize.Unspecified,
    background: Brush,
    onClick: () -> Unit
) {

    Column(
        modifier = modifier.clickable(
            interactionSource = remember { MutableInteractionSource() },
            indication = rememberBreathRipple(BreathTheme.colors.background),
            onClick = onClick
        ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {

        Box(
            modifier = Modifier
                .clip(SquircleShape(24.dp))
                .aspectRatio(1f)
                .fillMaxWidth()
                .background(background, SquircleShape(24.dp))
        ) {

            Image(
                modifier = Modifier
                    .align(Alignment.Center)
                    .then(
                        if (!boundlessIcon) Modifier.width(56.dp)
                        else Modifier
                            .defaultMinSize(minWidth = 74.dp)
                            .size(boundlessIconSize)
                    ),
                painter = icon,
                contentDescription = null
            )

        }

        Text(
            text = label,
            style = BreathTheme.typography.labelMedium,
            textAlign = TextAlign.Center,
            color = BreathTheme.colors.text,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )

    }

}