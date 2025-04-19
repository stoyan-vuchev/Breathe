package io.duckcat.d.presentation.main.fitness

import android.widget.Toast
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.duckcat.d.R
import io.duckcat.d.core.ui.components.button.UniqueButton
import io.duckcat.d.core.ui.theme.BreathTheme
import io.duckcat.d.presentation.fitness.FitnessScreenState
import sv.lib.squircleshape.SquircleShape


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
    val context = LocalContext.current // Retrieves the current context in Compose

    Box(modifier = Modifier.fillMaxSize()) {
        // Background image filling the whole screen.
        Image(
            painter = painterResource(id = R.drawable.fitness_bg), // Replace with your actual background drawable
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
            Column(
                modifier = Modifier
                    .padding(27.dp)
            ) {
                Text(
                    text = "Fitness Assist",
                    style = MaterialTheme.typography.titleLarge.copy(
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 27.sp
                    ),
                    modifier = Modifier.align(Alignment.Start)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .background(Color.White.copy(alpha = 0.5f))
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Enter your Height and Weight to calculate your Fitness Status",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = Color.White.copy(alpha = 0.8f),
                        fontSize = 17.sp
                    ),
                    modifier = Modifier.align(Alignment.Start)
                )
            }
            Spacer(modifier = Modifier.height(50.dp))

            TextField(
                modifier = Modifier
                    .padding(horizontal = 32.dp)
                    .clip(SquircleShape())
                    .background(BreathTheme.colors.card.copy(alpha = 0.2f))
                    .padding(horizontal = 6.dp)
                    .animateContentSize(),
                value = heightInput,
                onValueChange = { heightInput = it },
                label = { Text("Height (m)",
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
                    if ( weight >= 14 && height >= 0.54 ) {
                        onSubmit(height, weight)
                    } else {
                        Toast.makeText(
                            context, // Pass your context here
                            "Please enter valid height and weight",
                            Toast.LENGTH_SHORT // Duration of the toast
                        ).show()
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
