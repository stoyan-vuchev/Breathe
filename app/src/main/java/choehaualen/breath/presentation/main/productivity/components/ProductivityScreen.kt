package choehaualen.breath.presentation.main.productivity.components

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.KeyboardArrowLeft
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import choehaualen.breath.R
import choehaualen.breath.core.etc.transformFraction
import choehaualen.breath.core.ui.colors.SkyBlueColors
import choehaualen.breath.core.ui.components.topbar.TopBarDefaults
import choehaualen.breath.core.ui.components.topbar.basic_topbar.BasicTopBar
import choehaualen.breath.core.ui.theme.BreathTheme
import choehaualen.breath.presentation.main.productivity.ProductivityReminders
import choehaualen.breath.presentation.main.productivity.ProductivityScreenState
import choehaualen.breath.presentation.main.productivity.ProductivityScreenUIAction
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.HazeStyle
import dev.chrisbanes.haze.haze
import dev.chrisbanes.haze.hazeChild
import sv.lib.squircleshape.SquircleShape

@Composable
fun ProductivityScreen(
    screenState: ProductivityScreenState,
    onUIAction: (ProductivityScreenUIAction) -> Unit
) {

    // Working? i guess its now working find also the latency seems nice.
    // i've noticed that :)

    // So what should we do now. I don't feel confident in writing
    // the sleep logic _._, i mean we can try to connect the value.
    // throught the converter, and the get data button we can add that to this screen
    // so the process will be easy.
    // we already did the converter if i remember correctly
    //so, sleep result from api could be connected to this variable.
    // but did we find which function gives the result?
    // We did, but we need to clamp the timestamps to a specific range (the sleep duration)
    // and somehow calculate the total sleep duration. Which must be done when retrieving the
    // sleep data itself. Because the API emits timestamps for a small time duration
    // like a minute or few hours. Which ain't helpful at all XD
    // bro take the variable which gets the info, then timestamp convertion can
    // give the result right? also it will be like in hours 'only'.

    // let me write what I just brainstormed.

    // for each sleep segment {
    // val duration = end - start
    // val total = for each duration . inWholeHours
    // so we basically map that and add each segment duration to get a total sleep time
    // right? yes! Got it. We'll need to make some refactoring tho.

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

    val background by rememberUpdatedState(
        Brush.verticalGradient(
            colors = listOf(
                BreathTheme.colors.backgroundGradientStart.copy(gradientAlpha),
                BreathTheme.colors.backgroundGradientEnd.copy(gradientAlpha)
            )
        )
    )

    val scrollBehavior = TopBarDefaults.exitUntilCollapsedScrollBehavior()

    val bgAlpha by remember(scrollBehavior.state.collapsedFraction) {
        derivedStateOf { scrollBehavior.state.collapsedFraction }
    }

    val topBarBgAlpha by remember(bgAlpha) { // this ensures smooth color transition
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

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(background)
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        containerColor = BreathTheme.colors.background.copy(bgAlpha),
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

                    IconButton(
                        onClick = { onUIAction(ProductivityScreenUIAction.NavigateUp) }
                    ) {

                        Icon(
                            modifier = Modifier.size(32.dp),
                            imageVector = Icons.AutoMirrored.Rounded.KeyboardArrowLeft,
                            contentDescription = "Navigate back to Home."
                        )

                    }

                },
                backgroundColor = BreathTheme.colors.background.copy(topBarBgAlpha)
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
            shape = SquircleShape(
                topStart = 24.dp,
                topEnd = 24.dp,
                bottomEnd = 8.dp,
                bottomStart = 8.dp,
            ),
            id = ProductivityReminders.WATER_INTAKE,
            icon = painterResource(id = R.drawable.water_glass),
            label = "Water intake",
            description = "Reminds you to drink water\n" +
                    "every interval to stay hydrated",
            onUIAction = onUIAction
        )

    }

    item(key = "read_book_reminder") {

        Spacer(modifier = Modifier.height(4.dp))

        ProductivityScreenReminder(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth(),
            state = ProductivityScreenReminderState(),
            shape = SquircleShape(8.dp),
            id = ProductivityReminders.READ_BOOK,
            icon = painterResource(id = R.drawable.book),
            label = "Read a Book",
            description = "Reading daily enhances brain strength " +
                    "and cognitive abilities.\n" +
                    "Aim for at least 34 minutes\n" +
                    "of reading each day.",
//                    "It's recommended that you should " +
//                    "read a book for at least 34min a day",
            onUIAction = onUIAction
        )

    }

    item(key = "basic_workout_reminder") {

        Spacer(modifier = Modifier.height(4.dp))

        ProductivityScreenReminder(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth(),
            state = ProductivityScreenReminderState(),
            shape = SquircleShape(8.dp),
            id = ProductivityReminders.BASIC_WORKOUT,
            icon = painterResource(id = R.drawable.activity),
            label = "Basic Workout",
            description = "For a basic workout, include " +
                    "running, lunges, and stretches, " +
                    "aim for a minimum of 45 minutes.",
            onUIAction = onUIAction
        )

    }

    item(key = "touch_grass_reminder") {

        Spacer(modifier = Modifier.height(4.dp))

        ProductivityScreenReminder(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth(),
            state = ProductivityScreenReminderState(),
            shape = SquircleShape(
                topStart = 8.dp,
                topEnd = 8.dp,
                bottomEnd = 24.dp,
                bottomStart = 24.dp,
            ),
            id = ProductivityReminders.TOUCH_GRASS,
            icon = painterResource(id = R.drawable.touch_grass),
            label = "Touch Grass",
            description = "Breathe recommends short breaks " +
                    "for nature interaction during long " +
                    "work periods to boost mood, oxygen levels, " +
                    "leading to better alertness and less fatigue.",
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