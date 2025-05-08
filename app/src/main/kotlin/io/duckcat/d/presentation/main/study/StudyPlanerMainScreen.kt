package io.duckcat.d.presentation.study


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.duckcat.d.R
import io.duckcat.d.core.ui.theme.BreathTheme
import io.duckcat.d.data.local.entity.StudySubjectEntity
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun StudyPlanerMainScreen(
    subjects: List<StudySubjectEntity>,
    currentFocusSubject: StudySubjectEntity?,
    onEditSubject: (StudySubjectEntity) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier) {
        // Background Image
        Image(
            painter = painterResource(id = R.drawable.study_bg),
            contentDescription = "Study Planner Background",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(120.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            // Top Header Row
            Spacer(modifier = Modifier.height(68.dp))
            Row(
                modifier = Modifier.fillMaxWidth().padding(24.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "Effective Learning",
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
                        text = "concentrate on the subjects at hand in the present moment.",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = Color.White.copy(alpha = 0.8f),
                            fontSize = 17.sp
                        ),
                        modifier = Modifier.align(Alignment.Start)
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))
            Box(
                modifier = Modifier
                    .height(2.dp)
                    .background(Color.White.copy(alpha = 0.4f))
                    .padding(78.dp)
            )
            Spacer(modifier = Modifier.height(12.dp))


            // Display current focus subject text
//            Box(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .height(100.dp) // Adjust height as needed
//                    .background(Color.Black),
//                contentAlignment = Alignment.Center // Ensures the content is centered within the Box
//            ) {
                Text(
                    text = "Focus on: ${currentFocusSubject?.subjectName ?: "None"}",
                    style = BreathTheme.typography.headlineSmall,
                    color = Color.White,
                    textAlign = TextAlign.Center // Ensures the text itself is centered
                )

            Spacer(modifier = Modifier.height(12.dp))
            Box(
                modifier = Modifier
                    .height(2.dp)
                    .background(Color.White.copy(alpha = 0.4f))
                    .padding(78.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
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
    var showDialog by remember { androidx.compose.runtime.mutableStateOf(false) }

    if (showDialog) {
        // For brevity, we assume your edit dialog code remains the same.
        // (See your previous implementation for the AlertDialog.)
    }
    androidx.compose.material3.Card(
        shape = RoundedCornerShape(20.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
            .clickable { showDialog = true },
        elevation = androidx.compose.material3.CardDefaults.cardElevation(defaultElevation = 0.dp),
        colors = androidx.compose.material3.CardDefaults.cardColors(containerColor = BreathTheme.colors.card.copy(alpha = 0.3f))
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
                val priorityText = when(subject.priority) {
                    1 -> "Low"
                    2 -> "Med"
                    3 -> "High"
                    else -> subject.priority.toString()
                }
                Text(text = "Difficulty: $priorityText", color = Color.White)
                val dateStr = dateFormat.format(Date(subject.examDate))
                Text(text = "Exam Date: $dateStr", color = Color.White)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun StudyPlanerMainScreenPreview() {
    val dummySubjects = listOf(
        StudySubjectEntity(subjectName = "Mathematics", priority = 3, examDate = System.currentTimeMillis() + 86400000L),
        StudySubjectEntity(subjectName = "History", priority = 2, examDate = System.currentTimeMillis() + 172800000L)
    )
    BreathTheme {
        StudyPlanerMainScreen(
            subjects = dummySubjects,
            currentFocusSubject = dummySubjects.firstOrNull(),
            onEditSubject = { }
        )
    }
}
