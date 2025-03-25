package io.proxima.breathe.presentation.study

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction

import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import sv.lib.squircleshape.SquircleShape
import io.proxima.breathe.R
import io.proxima.breathe.core.ui.components.button.UniqueButton
import io.proxima.breathe.core.ui.theme.BreathTheme
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

// ---------- StudyPlanerSetupScreen ----------
@Composable
fun StudyPlanerSetupScreen(
    onAddSubject: (subjectName: String, priority: Int, examDate: Long) -> Unit,
    onDone: () -> Unit
) {
    var subjectName by remember { mutableStateOf("") }
    var priorityText by remember { mutableStateOf("") }
    var examDateText by remember { mutableStateOf("") } // Expecting format "yyyy-MM-dd"
    // Use a proper date format (note: "yyyy-MM-dd" is case-sensitive)
    val dateFormat = remember { SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()) }

    // Full-screen container with background image
    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.backgroundfakeblur), // your background image
            contentDescription = "Study Setup Background",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
        // Centered column for inputs and buttons
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 30.dp, vertical = 18.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Add Subject Details",
                style = BreathTheme.typography.headlineSmall,
                color = Color.White
            )
            // Spacer between title and inputs
            androidx.compose.foundation.layout.Spacer(modifier = Modifier.height(35.dp))
            // Subject Name input
            TextField(
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .clip(SquircleShape())
                    .background(BreathTheme.colors.card.copy(alpha = 0.2f))
                    .padding(horizontal = 6.dp)
                    .animateContentSize(),
                value = subjectName,
                onValueChange = { subjectName = it },
                label = { Text("Subject", color = Color.White) },
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
                    keyboardType = androidx.compose.ui.text.input.KeyboardType.Text
                )
            )
            androidx.compose.foundation.layout.Spacer(modifier = Modifier.height(8.dp))
            // Priority input
            TextField(
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .clip(SquircleShape())
                    .background(BreathTheme.colors.card.copy(alpha = 0.2f))
                    .padding(horizontal = 6.dp)
                    .animateContentSize(),
                value = priorityText,
                onValueChange = { priorityText = it },
                label = { Text("Difficulty (1-10)", color = Color.White) },
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
                    keyboardType = androidx.compose.ui.text.input.KeyboardType.Number
                )
            )
            androidx.compose.foundation.layout.Spacer(modifier = Modifier.height(8.dp))
            // Exam Date input
            TextField(
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .clip(SquircleShape())
                    .background(BreathTheme.colors.card.copy(alpha = 0.2f))
                    .padding(horizontal = 6.dp)
                    .animateContentSize(),
                value = examDateText,
                onValueChange = { examDateText = it },
                label = { Text("Exam Date (yyyy-MM-dd)", color = Color.White) },
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
                    keyboardType = androidx.compose.ui.text.input.KeyboardType.Number
                )
            )
            androidx.compose.foundation.layout.Spacer(modifier = Modifier.height(16.dp))
            // Row for Add Subject and Done buttons
            Row(
                modifier = Modifier.fillMaxWidth(0.9f),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                UniqueButton(onClick = {
                    val priority = priorityText.toIntOrNull() ?: 0
                    val examDate: Long = try {
                        dateFormat.parse(examDateText)?.time ?: 0L
                    } catch (e: Exception) {
                        0L
                    }
                    if (subjectName.isNotBlank() && priority > 0 && examDate > 0) {
                        onAddSubject(subjectName, priority, examDate)
                        subjectName = ""
                        priorityText = ""
                        examDateText = ""
                    }
                }) {
                    Text(text = "Add Subject", color = Color.White)
                }
                UniqueButton(onClick = onDone) {
                    Text(text = "Done", color = Color.White)
                }
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun StudyPlanerSetupScreenPreview() {
    BreathTheme {
        StudyPlanerSetupScreen(
            onAddSubject = { _, _, _ -> },
            onDone = { }
        )
    }
}



