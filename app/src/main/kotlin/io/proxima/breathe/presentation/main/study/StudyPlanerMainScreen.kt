package io.proxima.breathe.presentation.study

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.proxima.breathe.R
import io.proxima.breathe.core.ui.theme.BreathTheme
import io.proxima.breathe.data.local.entity.StudySubjectEntity
import java.text.SimpleDateFormat
import java.util.*

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
            painter = painterResource(id = R.drawable.figmafakeblur),
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
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onEdit(subject) }
            .padding(4.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = BreathTheme.colors.card.copy(alpha = 0.3f))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = subject.subjectName, style = BreathTheme.typography.titleMedium, color = Color.White)
            Text(text = "Priority: ${subject.priority}", color = Color.White)
            val dateStr = dateFormat.format(Date(subject.examDate))
            Text(text = "Exam Date: $dateStr", color = Color.White)
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
            onEditSubject = {}
        )
    }
}