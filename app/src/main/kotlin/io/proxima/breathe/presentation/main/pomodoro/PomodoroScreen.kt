package io.proxima.breathe.presentation.main.pomodoro

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import io.proxima.breathe.core.ui.components.button.UniqueButton
import io.proxima.breathe.R
import io.proxima.breathe.core.ui.theme.BreathDefaultColors
import io.proxima.breathe.core.ui.theme.BreathTheme
import sv.lib.squircleshape.SquircleShape
import androidx.compose.ui.unit.sp



@Composable
fun PomodoroScreen(
    onUIAction: (PomodoroScreenUIAction) -> Unit,
    viewModel: PomodoroViewModel = viewModel()
) {
    val timer by viewModel.currentTimer.collectAsState()
    val isFocusSession by viewModel.isFocusSession.collectAsState()
    val pomodoroCount by viewModel.pomodoroCount.collectAsState()
    val taskTitle by viewModel.taskTitle.collectAsState()
    val isRunning by viewModel.isTimerRunning.collectAsState()
    val isPaused by viewModel.isTimerPaused.collectAsState()

    Box(modifier = Modifier.fillMaxSize()) {
        // Background image fills entire screen.
        Image(
            painter = painterResource(id = R.drawable.pomodoro_screen),
            contentDescription = "Pomodoro Background",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        // Foreground content
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Back button using UniqueButton.
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { onUIAction(PomodoroScreenUIAction.NavigateUp) }) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Navigate Up"
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))

            // Custom task input field.
            TaskTextField(
                taskTitle = taskTitle,
                onTaskTitleChanged = viewModel::onTaskTitleChanged
            )
            Spacer(modifier = Modifier.height(16.dp))

            // Background Box wrapping session text and buttons.
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
                    .clip(SquircleShape(40.dp))
                    .background(BreathDefaultColors.background.copy(alpha = 0.2f))
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Display session type text.
                    Text(
                        text = if (isFocusSession) "Focus Time" else "Break Time",
                        style = MaterialTheme.typography.bodyLarge.copy(
                            color = Color.White,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold
                        )
                    )
                    // Display timer.
                    Text(
                        text = String.format("%02d:%02d", timer / 60, timer % 60),
                        style = BreathTheme.typography.labelLarge.copy(
                            color = Color.White,
                            fontSize = 30.sp
                        ),
                        modifier = Modifier.padding(vertical = 16.dp)
                    )
                    // Display pomodoro count.
                    Text(
                        text = "Completed Pomodoros: $pomodoroCount",
                        style = BreathTheme.typography.bodyMedium.copy(
                            color = Color.White,
                            fontSize = 20.sp
                        )
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    // Conditional UniqueButtons based on timer state.


                            when {
                                !isRunning && !isPaused -> {
                                    UniqueButton(onClick = { viewModel.startTimer() }) {
                                        Text(
                                            "Start",
                                            style = BreathTheme.typography.bodySmall.copy(fontSize = 15.sp),
                                            color = Color.Black
                                        )
                                    }
                                }
                                isRunning -> {
                                    Row {
                                        UniqueButton(onClick = { viewModel.pauseTimer() }) {
                                            Text(
                                                "Pause",
                                                style = BreathTheme.typography.bodySmall.copy(fontSize = 15.sp),
                                                color = Color.Black
                                            )
                                        }
                                        Spacer(modifier = Modifier.width(12.dp))
                                        UniqueButton(onClick = { viewModel.stopSession() }) {
                                            Text(
                                                "Stop",
                                                style = BreathTheme.typography.bodySmall.copy(fontSize = 15.sp),
                                                color = Color.Black
                                            )
                                        }
                                    }
                                }
                                isPaused -> {
                                    Row {
                                        UniqueButton(onClick = { viewModel.startTimer() }) {
                                            Text(
                                                "Resume",
                                                style = BreathTheme.typography.bodySmall.copy(fontSize = 15.sp),
                                                color = Color.Black
                                            )
                                        }
                                        Spacer(modifier = Modifier.width(12.dp))
                                        UniqueButton(onClick = { viewModel.stopSession() }) {
                                            Text(
                                                "Stop",
                                                style = BreathTheme.typography.bodySmall.copy(fontSize = 15.sp),
                                                color = Color.Black
                                            )
                                        }
                                    }
                                }
                            }



                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PomodoroScreenPreview() {
    val context = LocalContext.current
    PomodoroScreen(
        onUIAction = {},
        viewModel = PomodoroViewModel(context)
    )
}
