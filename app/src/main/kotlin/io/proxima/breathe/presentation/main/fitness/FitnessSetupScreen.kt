package io.proxima.breathe.presentation.fitness

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import sv.lib.squircleshape.SquircleShape
import io.proxima.breathe.core.ui.theme.BreathTheme
import io.proxima.breathe.core.ui.components.button.UniqueButton
import io.proxima.breathe.R


@Composable
fun FitnessSetupScreen(
    screenState: FitnessScreenState,
    onSubmit: (Float, Float) -> Unit,
    onNavigateResult: () -> Unit
) {
    // Local state for input fields.
    var heightInput by remember { mutableStateOf("") }
    var weightInput by remember { mutableStateOf("") }
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    Box(modifier = Modifier.fillMaxSize()) {
        // Background image filling the whole screen.
        Image(
            painter = painterResource(id = R.drawable.backgroundfakeblur), // Replace with your actual background drawable
            contentDescription = "Fitness Background",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        // Centered content
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TextField(
                modifier = Modifier
                    .padding(horizontal = 32.dp)
                    .clip(SquircleShape())
                    .background(BreathTheme.colors.card.copy(alpha = 0.2f))
                    .padding(horizontal = 6.dp)
                    .animateContentSize(),
                value = heightInput,
                onValueChange = { heightInput = it },
                label = { Text("Height (cm)",
                    color = Color.White) },
                colors = TextFieldDefaults.colors(
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    cursorColor = Color.White,
                    errorIndicatorColor = Color.Red,
                    errorContainerColor = Color.Transparent
                ),
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done,
                    keyboardType = KeyboardType.Number
                ),
                keyboardActions = KeyboardActions(
                    onAny = {
                        focusManager.clearFocus()
                        keyboardController?.hide()
                    }
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            TextField(
                modifier = Modifier
                    .padding(horizontal = 32.dp)
                    .clip(SquircleShape())
                    .background(BreathTheme.colors.card.copy(alpha = 0.2f))
                    .padding(horizontal = 6.dp)
                    .animateContentSize(),
                value = weightInput,
                onValueChange = { weightInput = it },
                label = { Text("Weight (kg)",
                    color = Color.White
                ) },
                colors = TextFieldDefaults.colors(
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    cursorColor = Color.White,
                    errorIndicatorColor = Color.Red,
                    errorContainerColor = Color.Transparent
                ),
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done,
                    keyboardType = KeyboardType.Number
                ),
                keyboardActions = KeyboardActions(
                    onAny = {
                        focusManager.clearFocus()
                        keyboardController?.hide()
                    }
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            UniqueButton(
                onClick = {
                    val height = heightInput.toFloatOrNull() ?: 0f
                    val weight = weightInput.toFloatOrNull() ?: 0f
                    if (height > 0 && weight > 0) {
                        onSubmit(height, weight)
                        onNavigateResult()
                    }
                },
                modifier = Modifier.padding(top = 0.dp)

            ) {
                Text("Submit",
                    color = Color.White)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FitnessSetupScreenPreview() {
    val dummyState = FitnessScreenState(
        height = 0f,
        weight = 0f,
        bmi = 0f,
        bmiCategory = "",
        isSetupCompleted = false
    )
    FitnessSetupScreen(
        screenState = dummyState,
        onSubmit = { _, _ -> },
        onNavigateResult = {}
    )
}
