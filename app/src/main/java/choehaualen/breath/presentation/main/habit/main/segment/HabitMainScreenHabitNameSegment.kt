package choehaualen.breath.presentation.main.habit.main.segment

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.KeyboardArrowLeft
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import choehaualen.breath.core.ui.colors.backgroundBrush
import choehaualen.breath.core.ui.components.button.UniqueButton
import choehaualen.breath.core.ui.components.topbar.small_topbar.SmallTopBar
import choehaualen.breath.core.ui.theme.BreathTheme
import choehaualen.breath.presentation.main.habit.main.HabitMainScreenState
import choehaualen.breath.presentation.main.habit.main.HabitMainScreenUIAction

@Composable
fun HabitMainScreenHabitNameSegment(
    screenState: HabitMainScreenState,
    onUIAction: (HabitMainScreenUIAction) -> Unit
) {

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(BreathTheme.colors.backgroundBrush()),
        containerColor = Color.Unspecified,
        contentColor = BreathTheme.colors.text,
        topBar = {

            SmallTopBar(
                titleText = "",
                backgroundColor = Color.Transparent,
                contentColor = BreathTheme.colors.text,
                navigationIcon = {

                    IconButton(
                        onClick = { onUIAction(HabitMainScreenUIAction.NavigateUp) }
                    ) {

                        Icon(
                            modifier = Modifier.size(32.dp),
                            imageVector = Icons.AutoMirrored.Rounded.KeyboardArrowLeft,
                            contentDescription = "Navigate Up."
                        )

                    }

                }
            )

        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.weight(1f))

            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "Enter the habit you want to\ncontrol and to stay on the\nzone.",
                textAlign = TextAlign.Center,
                color = BreathTheme.colors.text,
                style = BreathTheme.typography.titleLarge
            )

            Spacer(modifier = Modifier.weight(1f))

            val keyboardController = LocalSoftwareKeyboardController.current
            val focusManager = LocalFocusManager.current

            TextField(
                value = screenState.name,
                onValueChange = { newText ->
                    onUIAction(HabitMainScreenUIAction.SetHabitName(newText))
                },
                colors = TextFieldDefaults.colors(
                    focusedTextColor = BreathTheme.colors.text,
                    unfocusedTextColor = BreathTheme.colors.text,
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    focusedIndicatorColor = BreathTheme.colors.text,
                    unfocusedIndicatorColor = BreathTheme.colors.text,
                    cursorColor = BreathTheme.colors.text,
                    errorIndicatorColor = Color.Red,
                    errorContainerColor = Color.Transparent
                ),
                placeholder = {

                    Text(
                        text = "e.g. Quit Smoking",
                        style = BreathTheme.typography.bodyLarge
                    )

                },
                textStyle = BreathTheme.typography.bodyLarge,
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onAny = {
                        focusManager.clearFocus()
                        keyboardController?.hide()
                    }
                )
            )

            Spacer(modifier = Modifier.weight(1f))

            UniqueButton(
                onClick = { /*TODO*/ }
            ) {

                Text(
                    modifier = Modifier.padding(horizontal = 24.dp),
                    text = "Next"
                )

            }

            Spacer(modifier = Modifier.height(56.dp))

        }

    }

}