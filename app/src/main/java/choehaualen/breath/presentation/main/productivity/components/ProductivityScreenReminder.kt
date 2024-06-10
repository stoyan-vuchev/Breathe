package choehaualen.breath.presentation.main.productivity.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.rounded.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import choehaualen.breath.R
import choehaualen.breath.core.ui.colors.SkyBlueColors
import choehaualen.breath.core.ui.components.rememberBreathRipple
import choehaualen.breath.core.ui.theme.BreathTheme
import choehaualen.breath.presentation.main.productivity.ProductivityReminderInterval
import choehaualen.breath.presentation.main.productivity.ProductivityReminderInterval.Companion.stepDown
import choehaualen.breath.presentation.main.productivity.ProductivityReminderInterval.Companion.stepUp
import sv.lib.squircleshape.SquircleShape

@Composable
fun ProductivityScreenReminder(
    modifier: Modifier = Modifier,
    shape: Shape = SquircleShape(24.dp),
    state: ProductivityScreenReminderState,
    icon: Painter,
    label: String,
    description: String,
    minInterval: ProductivityReminderInterval = ProductivityReminderInterval.FortyFiveMinutes,
    maxInterval: ProductivityReminderInterval = ProductivityReminderInterval.SixtyMinutes,
    onUIAction: (ProductivityScreenReminderUIAction) -> Unit
) {

    Column(
        modifier = modifier
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberBreathRipple(),
                onClick = {
                    onUIAction(ProductivityScreenReminderUIAction.SetEnabled(!state.enabled))
                }
            )
            .background(
                color = BreathTheme.colors.card,
                shape = shape
            )
            .padding(24.dp)
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Icon(
                modifier = Modifier.size(32.dp),
                painter = icon,
                contentDescription = null,
                tint = BreathTheme.colors.text
            )

            Spacer(modifier = Modifier.width(20.dp))

            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {

                Text(
                    text = label,
                    style = BreathTheme.typography.titleMedium,
                    color = BreathTheme.colors.text
                )

                Text(
                    text = description,
                    style = BreathTheme.typography.bodyMedium,
                    color = BreathTheme.colors.text.copy(.86f)
                )

            }

            Spacer(modifier = Modifier.width(24.dp))

            Switch(
                checked = state.enabled,
                colors = SwitchDefaults.colors(
                    uncheckedBorderColor = BreathTheme.colors.text.copy(.75f),
                    uncheckedThumbColor = BreathTheme.colors.text,
                    uncheckedTrackColor = BreathTheme.colors.card,
                    checkedTrackColor = BreathTheme.colors.secondarySoul,
                    checkedThumbColor = BreathTheme.colors.background,
                ),
                onCheckedChange = { enabled ->
                    onUIAction(ProductivityScreenReminderUIAction.SetEnabled(enabled))
                }
            )

        }

        if (state.interval != null) {

            Spacer(modifier = Modifier.height(32.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End
            ) {

                Text(
                    text = "Time interval:",
                    style = BreathTheme.typography.bodyLarge,
                    color = BreathTheme.colors.text
                )

                Spacer(modifier = Modifier.width(16.dp))

                Box(
                    modifier = Modifier
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = rememberBreathRipple(),
                            onClick = {
                                onUIAction(
                                    ProductivityScreenReminderUIAction.SetInterval(
                                        state.interval.stepDown(minInterval)
                                    )
                                )
                            }
                        )
                        .size(32.dp)
                        .background(
                            color = BreathTheme.colors.text.copy(.16f),
                            shape = CircleShape
                        ),
                    contentAlignment = Alignment.Center
                ) {

                    Icon(
                        imageVector = Icons.AutoMirrored.Rounded.KeyboardArrowLeft,
                        contentDescription = "Set lower interval"
                    )

                }

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = state.interval.text,
                    style = BreathTheme.typography.bodyLarge,
                    color = BreathTheme.colors.text
                )

                Spacer(modifier = Modifier.width(8.dp))

                Box(
                    modifier = Modifier
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = rememberBreathRipple(),
                            onClick = {
                                onUIAction(
                                    ProductivityScreenReminderUIAction.SetInterval(
                                        state.interval.stepUp(maxInterval)
                                    )
                                )
                            }
                        )
                        .size(32.dp)
                        .background(
                            color = BreathTheme.colors.text.copy(.16f),
                            shape = CircleShape
                        ),
                    contentAlignment = Alignment.Center
                ) {

                    Icon(
                        imageVector = Icons.AutoMirrored.Rounded.KeyboardArrowRight,
                        contentDescription = "Set lower interval"
                    )

                }

            }

        }

    }

}

@Preview
@Composable
private fun ProductivityScreenReminderPreview() = BreathTheme(SkyBlueColors) {

    var state by remember {
        mutableStateOf(
            ProductivityScreenReminderState(
                interval = ProductivityReminderInterval.FortyFiveMinutes
            )
        )
    }

    val onUIAction = remember<(ProductivityScreenReminderUIAction) -> Unit> {
        { uiAction ->

            state = when (uiAction) {

                is ProductivityScreenReminderUIAction.SetEnabled -> {
                    state.copy(enabled = uiAction.enabled)
                }

                is ProductivityScreenReminderUIAction.SetInterval -> {
                    state.copy(interval = uiAction.interval)
                }

            }

        }
    }

    ProductivityScreenReminder(
        modifier = Modifier.fillMaxWidth(),
        state = state,
        icon = painterResource(id = R.drawable.water_glass),
        label = "Water intake",
        description = "Reminds you to drink water\nevery interval to stay hydrated",
        onUIAction = onUIAction
    )

}