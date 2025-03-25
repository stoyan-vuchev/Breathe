package io.proxima.breathe.presentation.main.productivity.components

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.KeyboardArrowLeft
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
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
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.Spring
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.ContextCompat

@Composable
fun ProductivityScreen(
    screenState: ProductivityScreenState,
    onUIAction: (ProductivityScreenUIAction) -> Unit
) {
    // For notifications permission check (if needed)
    CheckForNotificationPermission()

    var isScreenShown by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) { isScreenShown = true }

    val gradientAlpha by animateFloatAsState(
        targetValue = if (isScreenShown) 1f else 0f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioNoBouncy,
            stiffness = Spring.StiffnessVeryLow
        )
    )

    val scrollBehavior = TopBarDefaults.exitUntilCollapsedScrollBehavior()
    val bgAlpha by remember(scrollBehavior.state.collapsedFraction) {
        derivedStateOf { scrollBehavior.state.collapsedFraction }
    }
    val topBarBgAlpha by remember(bgAlpha) {
        derivedStateOf {
            transformFraction(bgAlpha, 0.8f, 1f, 0f, 0.5f)
        }
    }
    val hazeState = remember { HazeState() }

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.productivity_background),
            contentDescription = "Background",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        Scaffold(
            modifier = Modifier
                .fillMaxSize()
                .nestedScroll(scrollBehavior.nestedScrollConnection),
            containerColor = Color.Transparent,
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
                    titleText = "Productivity Reminders",
                    scrollBehavior = scrollBehavior,
                    navigationIcon = {
                        IconButton(onClick = { onUIAction(ProductivityScreenUIAction.NavigateUp) }) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Rounded.KeyboardArrowLeft,
                                contentDescription = "Back",
                                modifier = Modifier.size(32.dp)
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
                contentPadding = insetsPadding
            ) {
                reminderItems(screenState, onUIAction)
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
            state = ProductivityScreenReminderState(enabled = screenState.isWaterIntakeReminderEnabled),
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
            state = ProductivityScreenReminderState(enabled = screenState.isReadBookReminderEnabled),
            shape = SquircleShape(24.dp),
            id = ProductivityReminders.READ_BOOK,
            icon = painterResource(id = R.drawable.book),
            label = "Read a Book",
            description = "Enhance cognitive abilities. Aim for 34 minutes of reading.",
            onUIAction = onUIAction
        )
    }
    item(key = "basic_workout_reminder") {
        Spacer(modifier = Modifier.height(16.dp))
        ProductivityScreenReminder(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth(),
            state = ProductivityScreenReminderState(enabled = screenState.isBasicWorkoutReminderEnabled),
            shape = SquircleShape(24.dp),
            id = ProductivityReminders.BASIC_WORKOUT,
            icon = painterResource(id = R.drawable.activity),
            label = "Basic Workout",
            description = "Do stretches, walking or jogging. Aim for 45 minutes.",
            onUIAction = onUIAction
        )
    }
    item(key = "touch_grass_reminder") {
        Spacer(modifier = Modifier.height(16.dp))
        ProductivityScreenReminder(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth(),
            state = ProductivityScreenReminderState(enabled = screenState.isTouchGrassReminderEnabled),
            shape = SquircleShape(24.dp),
            id = ProductivityReminders.TOUCH_GRASS,
            icon = painterResource(id = R.drawable.moon),
            label = "Touch Grass",
            description = "Take a break for nature interaction.",
            onUIAction = onUIAction
        )
    }
    item(key = "bottom_spacer") {
        Spacer(modifier = Modifier.height(512.dp))
    }
}

@Composable
private fun CheckForNotificationPermission() {
    // Implementation for checking notification permission...'
    val context = LocalContext.current

    val notificationPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { /* You can handle the result if needed */ }
    )

    LaunchedEffect(Unit) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            val permissionCheck = ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            )
            if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
                notificationPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProductivityScreenPreview() {
    BreathTheme(SkyBlueColors) {
        ProductivityScreen(
            screenState = ProductivityScreenState(
                isWaterIntakeReminderEnabled = true,
                isReadBookReminderEnabled = true,
                isBasicWorkoutReminderEnabled = false,
                isTouchGrassReminderEnabled = true
            ),
            onUIAction = {} // No-op for preview
        )
    }
}


