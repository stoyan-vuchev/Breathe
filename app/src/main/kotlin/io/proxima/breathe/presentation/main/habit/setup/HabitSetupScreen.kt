package io.proxima.breathe.presentation.main.habit.setup

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.KeyboardArrowLeft
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.text.isDigitsOnly
import io.proxima.breathe.R
import io.proxima.breathe.core.ui.theme.backgroundBrush
import io.proxima.breathe.core.ui.components.button.UniqueButton
import io.proxima.breathe.core.ui.components.topbar.small_topbar.SmallTopBar
import io.proxima.breathe.core.ui.theme.BreathTheme
import kotlin.math.roundToInt

@Composable
fun HabitSetupScreen(
    screenState: HabitSetupScreenState,
    onUIAction: (HabitSetupScreenUIAction) -> Unit
) {

    BackHandler(
        enabled = screenState.currentSegment !is HabitSetupScreenSegment.HabitName,
        onBack = { onUIAction(HabitSetupScreenUIAction.NavigateUp) }
    )

    AnimatedVisibility(
        modifier = Modifier.fillMaxSize(),
        visible = screenState.currentSegment != null
    ) {

        Scaffold(
            modifier = Modifier
                .fillMaxSize()
                .background(BreathTheme.colors.backgroundBrush()),
            containerColor = Color.Unspecified,
            contentColor = BreathTheme.colors.text,
            topBar = {

                SmallTopBar(
                    titleText = "",
                    backgroundColor = Color.Transparent,
                    contentColor = BreathTheme.colors.text,
                    navigationIcon = {

                        IconButton(
                            onClick = { onUIAction(HabitSetupScreenUIAction.NavigateUp) }
                        ) {

                            Icon(
                                modifier = Modifier.size(32.dp),
                                imageVector = Icons.AutoMirrored.Rounded.KeyboardArrowLeft,
                                contentDescription = "Navigate Up."
                            )

                        }

                    }
                )

            }
        ) { innerPadding ->

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Spacer(modifier = Modifier.weight(.75f))

                AnimatedContent(
                    targetState = screenState.currentSegment,
                    label = ""
                ) { currentSegment ->

                    if (currentSegment is HabitSetupScreenSegment.HabitConfirmation) {
                        Icon(
                            modifier = Modifier
                                .padding(bottom = 64.dp)
                                .size(56.dp),
                            painter = painterResource(R.drawable.notification),
                            contentDescription = null,
                            tint = BreathTheme.colors.text
                        )
                        Spacer(Modifier.height(32.dp))
                    }

                }

                AnimatedContent(
                    targetState = screenState.currentSegment,
                    label = "",
                    transitionSpec = {

                        fadeIn(
                            animationSpec = spring(
                                dampingRatio = Spring.DampingRatioNoBouncy,
                                stiffness = Spring.StiffnessMedium
                            )
                        ) + slideInVertically(
                            animationSpec = spring(
                                dampingRatio = Spring.DampingRatioNoBouncy,
                                stiffness = Spring.StiffnessMediumLow
                            ),
                            initialOffsetY = { (it * .33f).roundToInt() }
                        ) togetherWith slideOutVertically(
                            animationSpec = spring(
                                dampingRatio = Spring.DampingRatioNoBouncy,
                                stiffness = Spring.StiffnessMediumLow
                            ),
                            targetOffsetY = { -(it * .33f).roundToInt() }
                        ) + fadeOut(
                            animationSpec = spring(
                                dampingRatio = Spring.DampingRatioNoBouncy,
                                stiffness = Spring.StiffnessMedium
                            )
                        )

                    }
                ) { currentSegment ->

                    val titleText by rememberUpdatedState(
                        when (currentSegment) {

                            is HabitSetupScreenSegment.HabitName -> {
                                "Enter the habit you want to\ncontrol and to stay on the\nzone."
                            }

                            is HabitSetupScreenSegment.HabitQuote -> {
                                "Enter a quote/reason\nwhy you want to stay\nquiting bad habits."
                            }

                            is HabitSetupScreenSegment.HabitDuration -> {
                                "Enter the days you want\nto stay free from the habit."
                            }

                            is HabitSetupScreenSegment.HabitConfirmation -> {
                                "You will receive notifications\n" +
                                        "starting from your usual\n" +
                                        "wake up till your usual\n" +
                                        "sleep time, to make sure\n" +
                                        "you stay on the zone."
                            }

                            else -> ""

                        }
                    )

                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = titleText,
                        textAlign = TextAlign.Center,
                        color = BreathTheme.colors.text,
                        style = BreathTheme.typography.titleLarge
                    )

                }

                Spacer(modifier = Modifier.weight(1f))

                val keyboardController = LocalSoftwareKeyboardController.current
                val focusManager = LocalFocusManager.current

                AnimatedContent(
                    targetState = screenState.currentSegment,
                    label = "",
                    transitionSpec = {

                        fadeIn(
                            animationSpec = spring(
                                dampingRatio = Spring.DampingRatioNoBouncy,
                                stiffness = Spring.StiffnessMedium
                            )
                        ) + slideInVertically(
                            animationSpec = spring(
                                dampingRatio = Spring.DampingRatioNoBouncy,
                                stiffness = Spring.StiffnessMediumLow
                            ),
                            initialOffsetY = { (it * .33f).roundToInt() }
                        ) togetherWith slideOutVertically(
                            animationSpec = spring(
                                dampingRatio = Spring.DampingRatioNoBouncy,
                                stiffness = Spring.StiffnessMediumLow
                            ),
                            targetOffsetY = { -(it * .33f).roundToInt() }
                        ) + fadeOut(
                            animationSpec = spring(
                                dampingRatio = Spring.DampingRatioNoBouncy,
                                stiffness = Spring.StiffnessMedium
                            )
                        )

                    }
                ) { currentSegment ->

                    when (currentSegment) {

                        is HabitSetupScreenSegment.HabitName -> TextField(
                            value = screenState.name,
                            onValueChange = { newText ->
                                onUIAction(
                                    HabitSetupScreenUIAction.SetFieldValue(
                                        currentSegment = currentSegment,
                                        newValue = newText
                                    )
                                )
                            },
                            colors = TextFieldDefaults.colors(
                                focusedTextColor = BreathTheme.colors.text,
                                unfocusedTextColor = BreathTheme.colors.text,
                                focusedContainerColor = Color.Transparent,
                                unfocusedContainerColor = Color.Transparent,
                                focusedIndicatorColor = BreathTheme.colors.text,
                                unfocusedIndicatorColor = BreathTheme.colors.text,
                                cursorColor = BreathTheme.colors.text,
                                errorIndicatorColor = Color.Red,
                                errorContainerColor = Color.Transparent
                            ),
                            placeholder = {

                                Text(
                                    text = "e.g. Quit Smoking",
                                    style = BreathTheme.typography.bodyLarge
                                )

                            },
                            textStyle = BreathTheme.typography.bodyLarge,
                            keyboardOptions = KeyboardOptions.Default.copy(
                                imeAction = ImeAction.Done,
                                keyboardType = KeyboardType.Text
                            ),
                            keyboardActions = KeyboardActions(
                                onAny = {
                                    focusManager.clearFocus()
                                    keyboardController?.hide()
                                }
                            )
                        )

                        is HabitSetupScreenSegment.HabitQuote -> TextField(
                            value = screenState.quote,
                            onValueChange = { newText ->
                                onUIAction(
                                    HabitSetupScreenUIAction.SetFieldValue(
                                        currentSegment = currentSegment,
                                        newValue = newText
                                    )
                                )
                            },
                            colors = TextFieldDefaults.colors(
                                focusedTextColor = BreathTheme.colors.text,
                                unfocusedTextColor = BreathTheme.colors.text,
                                focusedContainerColor = Color.Transparent,
                                unfocusedContainerColor = Color.Transparent,
                                focusedIndicatorColor = BreathTheme.colors.text,
                                unfocusedIndicatorColor = BreathTheme.colors.text,
                                cursorColor = BreathTheme.colors.text,
                                errorIndicatorColor = Color.Red,
                                errorContainerColor = Color.Transparent
                            ),
                            placeholder = {

                                Text(
                                    text = "e.g. Persistence is key.",
                                    style = BreathTheme.typography.bodyLarge
                                )

                            },
                            textStyle = BreathTheme.typography.bodyLarge,
                            keyboardOptions = KeyboardOptions.Default.copy(
                                imeAction = ImeAction.Done,
                                keyboardType = KeyboardType.Text
                            ),
                            keyboardActions = KeyboardActions(
                                onAny = {
                                    focusManager.clearFocus()
                                    keyboardController?.hide()
                                }
                            )
                        )

                        is HabitSetupScreenSegment.HabitDuration -> TextField(
                            value = screenState.goalDuration,
                            onValueChange = { newText ->
                                if (
                                    newText.isDigitsOnly()
                                    && !newText.startsWith("0")
                                    && newText.length <= 3
                                ) {
                                    onUIAction(
                                        HabitSetupScreenUIAction.SetFieldValue(
                                            currentSegment = currentSegment,
                                            newValue = newText
                                        )
                                    )
                                }
                            },
                            colors = TextFieldDefaults.colors(
                                focusedTextColor = BreathTheme.colors.text,
                                unfocusedTextColor = BreathTheme.colors.text,
                                focusedContainerColor = Color.Transparent,
                                unfocusedContainerColor = Color.Transparent,
                                focusedIndicatorColor = BreathTheme.colors.text,
                                unfocusedIndicatorColor = BreathTheme.colors.text,
                                cursorColor = BreathTheme.colors.text,
                                errorIndicatorColor = Color.Red,
                                errorContainerColor = Color.Transparent
                            ),
                            placeholder = {

                                Text(
                                    text = "e.g. 180",
                                    style = BreathTheme.typography.bodyLarge
                                )

                            },
                            textStyle = BreathTheme.typography.bodyLarge,
                            keyboardOptions = KeyboardOptions.Default.copy(
                                imeAction = ImeAction.Done,
                                keyboardType = KeyboardType.Number
                            ),
                            keyboardActions = KeyboardActions(
                                onAny = {
                                    focusManager.clearFocus()
                                    keyboardController?.hide()
                                }
                            ),
                            maxLines = 1,
                            singleLine = true
                        )

                        else -> {}

                    }

                }

                Spacer(modifier = Modifier.weight(1f))

                UniqueButton(
                    onClick = { onUIAction(HabitSetupScreenUIAction.Next) }
                ) {

                    Text(
                        modifier = Modifier
                            .padding(horizontal = 24.dp)
                            .animateContentSize(
                                alignment = Alignment.BottomCenter,
                                animationSpec = spring(
                                    dampingRatio = Spring.DampingRatioNoBouncy,
                                    stiffness = Spring.StiffnessMediumLow
                                )
                            ),
                        text = if (
                            screenState.currentSegment !is HabitSetupScreenSegment.HabitConfirmation
                        ) "Next" else "Start Controlling"
                    )

                }

                Spacer(modifier = Modifier.height(56.dp))

            }

        }

    }

}