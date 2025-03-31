package io.proxima.breathe.presentation.main.fitness

import android.widget.Toast
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.proxima.breathe.R
import io.proxima.breathe.core.ui.components.button.UniqueButton
import io.proxima.breathe.core.ui.theme.BreathTheme
import io.proxima.breathe.presentation.fitness.FitnessScreenState
import sv.lib.squircleshape.SquircleShape


@Composable
fun FitnessEditScreen(
    screenState: FitnessScreenState,
    onSubmit: (Float, Float) -> Unit,
    onNavigateBack: () -> Unit
) {
    var heightInput by remember { mutableStateOf(screenState.height.toString()) }
    var weightInput by remember { mutableStateOf(screenState.weight.toString()) }
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current
    val context = LocalContext.current // Retrieves the current context in Compose

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.fitness_bg),
            contentDescription = "Edit Background",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Update Fitness Data",
                style = MaterialTheme.typography.titleLarge.copy(
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 27.sp
                )
            )
            Spacer(modifier = Modifier.height(24.dp))
            // Height TextField
            TextField(
                modifier = Modifier
                    .padding(horizontal = 32.dp)
                    .clip(SquircleShape())
                    .background(BreathTheme.colors.card.copy(alpha = 0.2f))
                    .padding(horizontal = 6.dp)
                    .animateContentSize(),
                value = heightInput,
                onValueChange = { heightInput = it },
                label = { Text("Height (m)", color = Color.White) },
                colors = TextFieldDefaults.colors(
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    cursorColor = Color.White
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
            // Weight TextField
            TextField(
                modifier = Modifier
                    .padding(horizontal = 32.dp)
                    .clip(SquircleShape())
                    .background(BreathTheme.colors.card.copy(alpha = 0.2f))
                    .padding(horizontal = 6.dp)
                    .animateContentSize(),
                value = weightInput,
                onValueChange = { weightInput = it },
                label = { Text("Weight (kg)", color = Color.White) },
                colors = TextFieldDefaults.colors(
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    cursorColor = Color.White
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
            Spacer(modifier = Modifier.height(24.dp))
            UniqueButton(
                onClick = {
                    val height = heightInput.toFloatOrNull() ?: 0f
                    val weight = weightInput.toFloatOrNull() ?: 0f
                    if ( weight >= 14 && height >= 0.54 ) {
                        onSubmit(height, weight)
                        onNavigateBack()
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
                Text("Update", color = Color.White)
            }
        }
    }
}
