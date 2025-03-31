package io.proxima.breathe.presentation.study

import android.app.DatePickerDialog
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.proxima.breathe.R
import io.proxima.breathe.core.ui.components.button.UniqueButton
import io.proxima.breathe.core.ui.theme.BreathTheme
import sv.lib.squircleshape.SquircleShape
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

@Composable
fun StudyPlanerSetupScreen(
    onAddSubject: (subjectName: String, priority: Int, examDate: Long) -> Unit,
    onDone: () -> Unit
) {
    var subjectName by remember { mutableStateOf("") }
    var priorityText by remember { mutableStateOf("") }
    // Instead of examDateText being edited manually, we store a timestamp and a formatted string.
    var examDate by remember { mutableStateOf<Long?>(null) }
    var examDateText by remember { mutableStateOf("") }
    val context = LocalContext.current
    val dateFormat = remember { SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()) }

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.effective_bg),
            contentDescription = "Study Setup Background",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
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
            Spacer(modifier = Modifier.height(35.dp))
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
                    cursorColor = Color.White
                ),
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done
                )
            )
            Spacer(modifier = Modifier.height(8.dp))
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
                    cursorColor = Color.White
                ),
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done,
                    keyboardType = androidx.compose.ui.text.input.KeyboardType.Number
                )
            )
            Spacer(modifier = Modifier.height(8.dp))
            // Exam Date input: Instead of a TextField, use a clickable Box.
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .clip(SquircleShape())
                    .background(BreathTheme.colors.card.copy(alpha = 0.2f))
                    .clickable {
                        val calendar = Calendar.getInstance()
                        // If a date is already selected, show it
                        examDate?.let { calendar.timeInMillis = it }
                        DatePickerDialog(
                            context,
                            { _, year, month, dayOfMonth ->
                                calendar.set(year, month, dayOfMonth)
                                examDate = calendar.timeInMillis
                                examDateText = dateFormat.format(Date(calendar.timeInMillis))
                            },
                            calendar.get(Calendar.YEAR),
                            calendar.get(Calendar.MONTH),
                            calendar.get(Calendar.DAY_OF_MONTH)
                        ).show()
                    }
                    .padding(16.dp),
                contentAlignment = Alignment.CenterStart
            ) {
                Text(
                    text = if (examDateText.isEmpty()) "Select Exam Date" else examDateText,
                    color = Color.White
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(0.9f),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                UniqueButton(onClick = {
                    val priority = priorityText.toIntOrNull() ?: 0
                    val examTimestamp = examDate ?: 0L
                    if (subjectName.isNotBlank() && priority > 0 && examTimestamp > 0) {
                        onAddSubject(subjectName, priority, examTimestamp)
                        subjectName = ""
                        priorityText = ""
                        examDate = null
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
