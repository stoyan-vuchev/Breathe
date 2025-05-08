package io.duckcat.d.presentation.study

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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.duckcat.d.R
import io.duckcat.d.core.ui.components.button.UniqueButton
import io.duckcat.d.core.ui.theme.BreathTheme
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
    // Input fields for the current subject entry
    var subjectName by remember { mutableStateOf("") }

    // Priority: "Low" -> 1, "Med" -> 2, "High" -> 3
    val priorityOptions = listOf("Low", "Med", "High")
    var selectedPriority by remember { mutableStateOf("") }

    // Exam date
    var examDate by remember { mutableStateOf<Long?>(null) }
    var examDateText by remember { mutableStateOf("") }

    // For date formatting
    val context = LocalContext.current
    val dateFormat = remember { SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()) }

    // Local list to accumulate subjects for this setup session
    val subjectsToAdd = remember { mutableStateListOf<Triple<String, Int, Long>>() }

    Box(modifier = Modifier.fillMaxSize()) {
        // Background image
        Image(
            painter = painterResource(id = R.drawable.study_bg),
            contentDescription = "Study Setup Background",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        // Main content column
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp, vertical = 5.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Title
            Spacer(modifier = Modifier.height(56.dp))
            Row(
                modifier = Modifier.fillMaxWidth().padding(24.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "Add subjects and detail",
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
                            .height(2.dp)
                            .background(Color.White.copy(alpha = 0.5f))
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Forget the stress of covering every subject, just add them here, and we'll guide your study. \n\n (We'll notify you if your focus subject updates.).",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = Color.White.copy(alpha = 0.8f),
                            fontSize = 18.sp
                        ),
                        modifier = Modifier.align(Alignment.Start)
                    )
                }
            }
            Spacer(modifier = Modifier.height(30.dp))

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
            Spacer(modifier = Modifier.height(14.dp))

            // Priority selection: Low, Med, High
            Column(modifier = Modifier.fillMaxWidth(0.9f)) {
                Text(text = "  Difficulty", color = Color.White,textAlign = TextAlign.Center)
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    priorityOptions.forEach { level ->
                        Box(
                            modifier = Modifier
                                .size(60.dp)
                                .clip(CircleShape)
                                .background(
                                    if (selectedPriority == level) Color.Blue
                                    else BreathTheme.colors.card.copy(alpha = 0.3f)
                                )
                                .clickable {
                                    selectedPriority = level
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            Text(text = level, color = Color.White)
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(20.dp))

            // Exam Date input using DatePickerDialog
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .clip(SquircleShape())
                    .background(BreathTheme.colors.card.copy(alpha = 0.2f))
                    .clickable {
                        val calendar = Calendar.getInstance()
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

            // Optionally display the accumulated subjects
            if (subjectsToAdd.isNotEmpty()) {
                Column(modifier = Modifier.fillMaxWidth()) {
                    subjectsToAdd.forEach { (name, prio, examTime) ->
                        val priorityText = when (prio) {
                            1 -> "Low"
                            2 -> "Med"
                            3 -> "High"
                            else -> prio.toString()
                        }
                        Text(
                            text = "$name, Difficulty: $priorityText, Exam: ${dateFormat.format(Date(examTime))}",
                            color = Color.White
                        )
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }

            // Row for "Add Subject" and "Done" buttons
            Row(
                modifier = Modifier.fillMaxWidth(0.9f).padding(28.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                UniqueButton(onClick = {
                    val examTimestamp = examDate ?: 0L
                    val priorityValue = when (selectedPriority) {
                        "Low" -> 1
                        "Med" -> 2
                        "High" -> 3
                        else -> 0
                    }
                    // Validate
                    if (subjectName.isNotBlank() && priorityValue in 1..3 && examTimestamp > 0 && subjectsToAdd.size < 10) {
                        subjectsToAdd.add(Triple(subjectName, priorityValue, examTimestamp))
                        // Clear fields for the next input
                        subjectName = ""
                        selectedPriority = ""
                        examDate = null
                        examDateText = ""
                    }
                }) {
                    Text(text = "Add Subject", color = Color.White)
                }

                UniqueButton(onClick = {
                    // If there's unsaved input, add it first
                    val examTimestamp = examDate ?: 0L
                    val priorityValue = when (selectedPriority) {
                        "Low" -> 1
                        "Med" -> 2
                        "High" -> 3
                        else -> 0
                    }
                    if (subjectName.isNotBlank() && priorityValue in 1..3 && examTimestamp > 0 && subjectsToAdd.size < 10) {
                        subjectsToAdd.add(Triple(subjectName, priorityValue, examTimestamp))
                        subjectName = ""
                        selectedPriority = ""
                        examDate = null
                        examDateText = ""
                    }
                    // Save all subjects
                    subjectsToAdd.forEach { (name, prio, examTime) ->
                        onAddSubject(name, prio, examTime)
                    }
                    subjectsToAdd.clear()
                    onDone()
                }) {
                    Text(text = "Done", color = Color.White)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun StudyPlanerSetupScreenPreview() {
    BreathTheme {
        StudyPlanerSetupScreen(
            onAddSubject = { _, _, _ -> },
            onDone = { }
        )
    }
}
