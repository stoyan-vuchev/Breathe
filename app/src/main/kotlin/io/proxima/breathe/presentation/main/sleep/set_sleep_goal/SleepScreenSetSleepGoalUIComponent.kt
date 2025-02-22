package io.proxima.breathe.presentation.main.sleep.set_sleep_goal

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActionScope
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.core.text.isDigitsOnly
import io.proxima.breathe.core.etc.UiString
import io.proxima.breathe.core.etc.UiString.Companion.asComposeString
import io.proxima.breathe.core.ui.theme.ProvideBreathColors
import io.proxima.breathe.core.ui.theme.SleepColors
import io.proxima.breathe.core.ui.theme.BreathTheme
import sv.lib.squircleshape.SquircleShape

@Composable
fun SleepScreenSetSleepGoalUIComponent(
    state: SleepScreenSetSleepGoalUIComponentState,
    onUIAction: (SleepScreenSetSleepGoalUIComponentUIAction) -> Unit
) = Column {

    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
            .clip(SquircleShape(24.dp))
            .background(BreathTheme.colors.card),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {

        Spacer(modifier = Modifier.height(32.dp))

        Text(text = "Enter your sleep goal")

        Spacer(modifier = Modifier.height(32.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally)
        ) {

            HourMinuteTextField(
                value = state.sleepTimeHours,
                onValueChange = {
                    onUIAction(
                        SleepScreenSetSleepGoalUIComponentUIAction.SetSleepHours(it)
                    )
                },
                enabled = state.enabled,
                imeAction = ImeAction.Next,
                onImeAction = { focusManager.moveFocus(FocusDirection.Next) }
            )

            Text(text = "H")

            Text(text = ":")

            HourMinuteTextField(
                value = state.sleepTimeMinutes,
                onValueChange = {
                    onUIAction(
                        SleepScreenSetSleepGoalUIComponentUIAction.SetSleepMinutes(it)
                    )
                },
                enabled = state.enabled,
                imeAction = ImeAction.Done,
                onImeAction = {
                    keyboardController?.hide()
                    focusManager.clearFocus()
                }
            )

            Text(text = "M")

        }

        Spacer(modifier = Modifier.height(32.dp))

        AnimatedContent(
            modifier = Modifier.fillMaxWidth(),
            targetState = state.error != UiString.Empty,
            label = ""
        ) { isError ->

            if (isError) {

                Column(modifier = Modifier.fillMaxWidth()) {

                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        text = state.error.asComposeString(),
                        style = BreathTheme.typography.bodyMedium,
                        color = Color.Red,
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(32.dp))

                }

            }

        }

    }

    AnimatedContent(
        targetState = state.isSupportAlertShown,
        label = ""
    ) { isAlertShown ->

        if (isAlertShown) {

            Row(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth()
                    .clip(SquircleShape(24.dp))
                    .background(BreathTheme.colors.card)
                    .padding(horizontal = 24.dp, vertical = 18.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(24.dp)
            ) {

                Icon(
                    modifier = Modifier.size(24.dp),
                    imageVector = Icons.Rounded.Info,
                    contentDescription = null,
                    tint = Color.Red
                )

                Text(
                    text = "Automated sleep time detection only works on supported Android 11+ devices.",
                    style = BreathTheme.typography.bodyMedium
                )

            }

        }

    }

    Spacer(modifier = Modifier.height(16.dp))

}

@Composable
private fun HourMinuteTextField(
    value: String,
    onValueChange: (String) -> Unit,
    maxLength: Int = 2,
    enabled: Boolean,
    imeAction: ImeAction,
    onImeAction: KeyboardActionScope.() -> Unit
) = BasicTextField(
    modifier = Modifier
        .width(64.dp)
        .clip(SquircleShape())
        .background(BreathTheme.colors.primarySoul.copy(.33f))
        .border(
            width = Dp.Hairline,
            color = BreathTheme.colors.primarySoul,
            shape = SquircleShape()
        )
        .padding(
            horizontal = 16.dp,
            vertical = 8.dp
        ),
    value = value,
    onValueChange = { newValue ->

        if (newValue.length <= maxLength && newValue.isDigitsOnly()) {
            onValueChange(newValue)
        }

    },
    enabled = enabled,
    keyboardActions = KeyboardActions(onAny = onImeAction),
    keyboardOptions = KeyboardOptions(
        keyboardType = KeyboardType.Number,
        imeAction = imeAction
    ),
    textStyle = BreathTheme.typography.labelLarge.copy(
        color = BreathTheme.colors.text,
        textAlign = TextAlign.Center
    ),
    cursorBrush = SolidColor(BreathTheme.colors.text)
)

@Preview
@Composable
private fun SleepScreenSetSleepGoalUIComponentPreview() = BreathTheme {
    ProvideBreathColors(SleepColors) {
        SleepScreenSetSleepGoalUIComponent(
            state = SleepScreenSetSleepGoalUIComponentState(),
            onUIAction = {}
        )
    }
}