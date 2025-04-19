package io.duckcat.d.presentation.main.pomodoro

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import io.duckcat.d.core.ui.theme.BreathTheme
import sv.lib.squircleshape.SquircleShape

@Composable
fun TaskTextField(
    taskTitle: String,
    onTaskTitleChanged: (String) -> Unit
) {
    TextField(
        modifier = Modifier
            .padding(horizontal = 32.dp)
            .clip(SquircleShape(cornerSmoothing = .0f))
            .background(BreathTheme.colors.card.copy(alpha = 0.5f))
            .padding(horizontal = 6.dp)
            .animateContentSize(),
        value = taskTitle,
        onValueChange = onTaskTitleChanged,
        label = { Text("Enter Task", color = BreathTheme.colors.text) },
        colors = TextFieldDefaults.colors(
            focusedTextColor = BreathTheme.colors.text,
            unfocusedTextColor = BreathTheme.colors.text,
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            cursorColor = BreathTheme.colors.text,
            errorIndicatorColor = Color.Red,
            errorContainerColor = Color.Transparent,
        ),
        textStyle = BreathTheme.typography.bodyLarge,
        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = ImeAction.Done,
            keyboardType = KeyboardType.Text
        ),
        keyboardActions = KeyboardActions(
            onDone = {
                // Optionally clear focus or hide keyboard here.
            }
        ),
        singleLine = true,
        maxLines = 1
    )
}
