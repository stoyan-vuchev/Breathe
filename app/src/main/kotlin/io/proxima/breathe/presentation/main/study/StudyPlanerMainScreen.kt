package io.proxima.breathe.presentation.study

import android.app.DatePickerDialog
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.proxima.breathe.R
import io.proxima.breathe.core.ui.theme.BreathTheme
import io.proxima.breathe.data.local.entity.StudySubjectEntity
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

@Composable
fun StudyPlanerMainScreen(
    subjects: List<StudySubjectEntity>,
    currentFocusSubject: StudySubjectEntity?,
    onEditSubject: (StudySubjectEntity) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier.fillMaxSize()) {
        // Background Image
        Image(
            painter = painterResource(id = R.drawable.effective_bg),
            contentDescription = "Study Planner Background",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Text(
                text = "Current Focus: ${currentFocusSubject?.subjectName ?: "None"}",
                style = BreathTheme.typography.headlineSmall,
                color = Color.White
            )
            Spacer(modifier = Modifier.height(16.dp))
            LazyColumn {
                items(subjects) { subject ->
                    StudySubjectItem(subject = subject, onEdit = onEditSubject)
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }
}

@Composable
fun StudySubjectItem(subject: StudySubjectEntity, onEdit: (StudySubjectEntity) -> Unit) {
    val dateFormat = remember { SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()) }
    // Local state to control the edit dialog visibility.
    var showDialog by remember { mutableStateOf(false) }

    // Edit Dialog using Android's DatePickerDialog for date selection
    if (showDialog) {
        var editedSubjectName by remember { mutableStateOf(subject.subjectName) }
        var editedPriorityText by remember { mutableStateOf(subject.priority.toString()) }
        var editedExamDate by remember { mutableStateOf(subject.examDate) }
        var editedExamDateText by remember { mutableStateOf(dateFormat.format(Date(subject.examDate))) }
        val context = LocalContext.current

        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Edit Subject") },
            text = {
                Column {
                    TextField(
                        value = editedSubjectName,
                        onValueChange = { editedSubjectName = it },
                        label = { Text("Subject Name") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    TextField(
                        value = editedPriorityText,
                        onValueChange = { editedPriorityText = it },
                        label = { Text("Priority") },
                        modifier = Modifier.fillMaxWidth(),
                        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
                    )
                    // Date Picker for Exam Date
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                            .clip(MaterialTheme.shapes.small)
                            .background(BreathTheme.colors.card.copy(alpha = 0.2f))
                            .clickable {
                                val calendar = Calendar.getInstance().apply { timeInMillis = editedExamDate }
                                DatePickerDialog(
                                    context,
                                    { _, year, month, dayOfMonth ->
                                        calendar.set(year, month, dayOfMonth)
                                        editedExamDate = calendar.timeInMillis
                                        editedExamDateText = dateFormat.format(Date(calendar.timeInMillis))
                                    },
                                    calendar.get(Calendar.YEAR),
                                    calendar.get(Calendar.MONTH),
                                    calendar.get(Calendar.DAY_OF_MONTH)
                                ).show()
                            }
                            .padding(16.dp),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        Text(text = editedExamDateText, color = Color.White)
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = {
                    val editedPriority = editedPriorityText.toIntOrNull() ?: subject.priority
                    val updatedSubject = subject.copy(
                        subjectName = editedSubjectName,
                        priority = editedPriority,
                        examDate = editedExamDate
                    )
                    onEdit(updatedSubject)
                    showDialog = false
                }) {
                    Text("Save")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }

    // The card showing subject details with a clickable edit action.
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
            .clickable { showDialog = true },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = BreathTheme.colors.card.copy(alpha = 0.3f))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = subject.subjectName,
                    style = BreathTheme.typography.titleMedium,
                    color = Color.White
                )
                Text(text = "Priority: ${subject.priority}", color = Color.White)
                val dateStr = dateFormat.format(Date(subject.examDate))
                Text(text = "Exam Date: $dateStr", color = Color.White)
            }
            IconButton(
                onClick = { showDialog = true },
                modifier = Modifier.size(45.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.edit_pensil_enhanced),
                    contentDescription = "Edit Subject"
                )
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun StudyPlanerMainScreenPreview() {
    // Create dummy data for preview
    val dummySubjects = listOf(
        StudySubjectEntity(subjectName = "Math", priority = 10, examDate = System.currentTimeMillis() + 86400000L),
        StudySubjectEntity(subjectName = "History", priority = 8, examDate = System.currentTimeMillis() + 172800000L)
    )
    BreathTheme {
        StudyPlanerMainScreen(
            subjects = dummySubjects,
            currentFocusSubject = dummySubjects.firstOrNull(),
            onEditSubject = { updatedSubject ->
                // For preview purposes, print or log the updated subject.
                println("Updated subject: ${updatedSubject.subjectName}")
            }
        )
    }
}
