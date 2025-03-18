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

        Column(

            modifier = Modifier
                .fillMaxSize(),



            horizontalAlignment = Alignment.Start
        ){
            Spacer(modifier = Modifier.height(45.dp))
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
        }

        // Foreground content
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(45.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {


            Column(
                modifier = Modifier.fillMaxWidth()
            ) {


                Text(
                    text = "Focus Assist",
                    style = MaterialTheme.typography.titleLarge.copy(
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 33.sp
                    ),
                    modifier = Modifier.align(Alignment.Start)
                )

                Spacer(modifier = Modifier.height(8.dp))


                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 0.dp)
                        .height(1.dp)
                        .background(Color.White.copy(alpha = 0.5f))
                )

                Spacer(modifier = Modifier.height(8.dp))


                Text(
                    text = "Helps you focus on one task without feeling tired.",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = Color.White.copy(alpha = 0.8f),
                        fontSize = 17.sp
                    ),
                    modifier = Modifier.align(Alignment.Start)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))



            Spacer(modifier = Modifier.height(16.dp))


            Row(
                modifier = Modifier
                    .fillMaxWidth(),

                verticalAlignment = Alignment.CenterVertically

            ){
                Text(
                    text = "  Task",
                    style = BreathTheme.typography.bodyMedium.copy(
                        color = Color.White,
                        fontSize = 18.sp
                )
                )
            }
            Spacer(modifier = Modifier.height(9.dp))


            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(0.dp)
                    .clip(SquircleShape(40.dp))
                    .background(BreathDefaultColors.background.copy(alpha = 0.2f))
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Text(
                        text = if (isFocusSession) "Focus Time" else "Break Time",
                        style = MaterialTheme.typography.bodyLarge.copy(
                            color = Color.White,
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold
                        )
                    )

                    Text(
                        text = String.format("%02d m : %02d s", timer / 60, timer % 60),
                        style = BreathTheme.typography.labelLarge.copy(
                            color = Color.White,
                            fontSize = 30.sp
                        ),
                        modifier = Modifier.padding(vertical = 16.dp)
                    )

                    Text(
                        text = "Completed Pomodoros: $pomodoroCount",
                        style = BreathTheme.typography.bodyMedium.copy(
                            color = Color.White,
                            fontSize = 18.sp
                        )
                    )
                    Spacer(modifier = Modifier.height(16.dp))


                    when {
                        !isRunning && !isPaused -> {
                            UniqueButton(onClick = { viewModel.startTimer() }) {
                                Text(
                                    "Start Focusing",
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
                                        "End",
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
                                        "End",
                                        style = BreathTheme.typography.bodySmall.copy(fontSize = 15.sp),
                                        color = Color.Black
                                    )
                                }
                            }


                        }
                    }
                }
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                Spacer(modifier = Modifier.height(30.dp))
                Text(
                    text = "\"No matter how hard or how impossible it is, never lose sight of your goal.\"",
                    style = MaterialTheme.typography.bodyLarge.copy(
                        color = Color.White,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold
                    ),
                    modifier = Modifier.padding(horizontal = 16.dp),
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center
                )
                Spacer(modifier = Modifier.height(13.dp))
                Text(
                    text = "- Monkey D Luffy",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = Color.White.copy(alpha = 0.7f),
                        fontSize = 16.sp
                    ),
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center
                )
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
