package io.proxima.breathe.presentation.main.productivity.components

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.KeyboardArrowLeft
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.HazeStyle
import dev.chrisbanes.haze.haze
import dev.chrisbanes.haze.hazeChild
import io.proxima.breathe.R
import io.proxima.breathe.core.etc.transformFraction
import io.proxima.breathe.core.ui.components.topbar.TopBarDefaults
import io.proxima.breathe.core.ui.components.topbar.basic_topbar.BasicTopBar
import io.proxima.breathe.core.ui.theme.BreathTheme
import io.proxima.breathe.core.ui.theme.SkyBlueColors
import io.proxima.breathe.presentation.main.productivity.ProductivityReminders
import io.proxima.breathe.presentation.main.productivity.ProductivityScreenState
import io.proxima.breathe.presentation.main.productivity.ProductivityScreenUIAction
import sv.lib.squircleshape.SquircleShape
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.layout.ContentScale


@Composable
fun ProductivityScreen(
    screenState: ProductivityScreenState,
    onUIAction: (ProductivityScreenUIAction) -> Unit
) {

    CheckForNotificationPermission()

    var isScreenShown by rememberSaveable { mutableStateOf(false) }
    LaunchedEffect(Unit) { isScreenShown = true }

    val gradientAlpha by animateFloatAsState(
        targetValue = if (isScreenShown) 1f else 0f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioNoBouncy,
            stiffness = Spring.StiffnessVeryLow
        ),
        label = ""
    )

    val scrollBehavior = TopBarDefaults.exitUntilCollapsedScrollBehavior()

    val bgAlpha by remember(scrollBehavior.state.collapsedFraction) {
        derivedStateOf { scrollBehavior.state.collapsedFraction }
    }

    val topBarBgAlpha by remember(bgAlpha) {
        derivedStateOf {
            transformFraction(
                value = bgAlpha,
                startX = .8f,
                endX = 1f,
                startY = 0f,
                endY = .5f
            )
        }
    }

    val topBarTitle by remember(scrollBehavior.state.collapsedFraction) {
        derivedStateOf {
            if (scrollBehavior.state.collapsedFraction in .75f..1f)
                "Productivity Reminders" else "Productivity\nReminders"
        }
    }

    val hazeState = remember { HazeState() }

    Box(modifier = Modifier.fillMaxSize()) {

        // ✅ Background Image
        Image(
            painter = painterResource(id = R.drawable.productivity_background),  // Replace with your image
            contentDescription = "Productivity Background",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        Scaffold(
            modifier = Modifier
                .fillMaxSize()
                .nestedScroll(scrollBehavior.nestedScrollConnection),
            containerColor = Color.Transparent, // Transparent to show image background
            contentColor = BreathTheme.colors.text,
            topBar = {
                BasicTopBar(
                    modifier = Modifier.hazeChild(
                        state = hazeState,
                        style = HazeStyle(
                            tint = BreathTheme.colors.background.copy(topBarBgAlpha),
                            blurRadius = 20.dp
                        )
                    ),
                    titleText = topBarTitle,
                    scrollBehavior = scrollBehavior,
                    navigationIcon = {
                        IconButton(onClick = { onUIAction(ProductivityScreenUIAction.NavigateUp) }) {
                            Icon(
                                modifier = Modifier.size(32.dp),
                                imageVector = Icons.AutoMirrored.Rounded.KeyboardArrowLeft,
                                contentDescription = "Navigate back to Home."
                            )
                        }
                    },
                    backgroundColor = BreathTheme.colors.background.copy(topBarBgAlpha),
                    animateContent = true
                )
            },
            bottomBar = {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .hazeChild(hazeState)
                ) {
                    Spacer(modifier = Modifier.navigationBarsPadding())
                }
            }
        ) { insetsPadding ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .haze(hazeState),
                contentPadding = insetsPadding,
            ) {
                reminderItems(
                    screenState = screenState,
                    onUIAction = onUIAction
                )
            }
        }
    }
}

private fun LazyListScope.reminderItems(
    screenState: ProductivityScreenState,
    onUIAction: (ProductivityScreenUIAction) -> Unit
) {

    item(key = "water_intake_reminder") {
        Spacer(modifier = Modifier.height(16.dp))
        ProductivityScreenReminder(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth(),
            state = ProductivityScreenReminderState(
                enabled = screenState.isWaterIntakeReminderEnabled,
            ),
            shape = SquircleShape(24.dp),
            id = ProductivityReminders.WATER_INTAKE,
            icon = painterResource(id = R.drawable.water_glass),
            label = "Water intake",
            description = "Reminds you to drink water to stay hydrated",
            onUIAction = onUIAction
        )
    }

    item(key = "read_book_reminder") {
        Spacer(modifier = Modifier.height(16.dp))
        ProductivityScreenReminder(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth(),
            state = ProductivityScreenReminderState(
                enabled = screenState.isReadBookReminderEnabled
            ),
            shape = SquircleShape(24.dp),
            id = ProductivityReminders.READ_BOOK,
            icon = painterResource(id = R.drawable.book),
            label = "Read a Book",
            description = "Reading enhances cognitive abilities. Aim for 34 minutes of reading.",
            onUIAction = onUIAction
        )
    }

    item(key = "basic_workout_reminder") {
        Spacer(modifier = Modifier.height(16.dp))
        ProductivityScreenReminder(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth(),
            state = ProductivityScreenReminderState(
                enabled = screenState.isBasicWorkoutReminderEnabled
            ),
            shape = SquircleShape(24.dp),
            id = ProductivityReminders.BASIC_WORKOUT,
            icon = painterResource(id = R.drawable.activity),
            label = "Basic Workout",
            description = "Do stretches, walking or jogging. Aim for minimum of 45 minutes.",
            onUIAction = onUIAction
        )
    }

    item(key = "Sleep_reminder") {
        Spacer(modifier = Modifier.height(16.dp))
        ProductivityScreenReminder(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth(),
            state = ProductivityScreenReminderState(
                enabled = screenState.isTouchGrassReminderEnabled
            ),
            shape = SquircleShape(24.dp),
            id = ProductivityReminders.TOUCH_GRASS,
            icon = painterResource(id = R.drawable.moon),
            label = "Sleep Time",
            description = "Reminds you to sleep at the time you do.",
            onUIAction = onUIAction
        )
    }

    item(key = "bottom_spacer") {
        Spacer(modifier = Modifier.height(512.dp))
    }
}

@Preview(showBackground = true)
@Composable
private fun ProductivityScreenPreview() = BreathTheme(SkyBlueColors) {
    ProductivityScreen(
        screenState = ProductivityScreenState(),
        onUIAction = {}
    )
}

@Composable
private fun CheckForNotificationPermission() {
    val context = LocalContext.current
    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { _ -> }

    LaunchedEffect(Unit) {
        if (
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU &&
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            launcher.launch(Manifest.permission.POST_NOTIFICATIONS)
        }
    }
}
