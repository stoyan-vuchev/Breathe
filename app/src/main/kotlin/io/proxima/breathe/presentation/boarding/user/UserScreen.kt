package io.proxima.breathe.presentation.boarding.user

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TimeInput
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.proxima.breathe.R
import io.proxima.breathe.core.etc.UiString.Companion.asComposeString
import io.proxima.breathe.core.ui.components.button.UniqueButton
import io.proxima.breathe.core.ui.theme.BreathTheme
import com.stoyanvuchev.systemuibarstweaker.LocalSystemUIBarsTweaker

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserScreen(
    screenState: UserScreenState,
    onUIAction: (UserScreenUIAction) -> Unit
) {

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

    val tweaker = LocalSystemUIBarsTweaker.current
    DisposableEffect(key1 = tweaker) {
        tweaker.tweakSystemBarsStyle(
            statusBarStyle = tweaker.statusBarStyle.copy(darkIcons = true),
            navigationBarStyle = tweaker.navigationBarStyle.copy(darkIcons = true)
        )
        onDispose {}
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(background),
        containerColor = Color.Unspecified,
        contentColor = BreathTheme.colors.text
    ) { insetsPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(insetsPadding),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.weight(1f))

            Icon(
                modifier = Modifier.size(48.dp),
                painter = painterResource(id = screenState.currentSegment.icon),
                contentDescription = null,
                tint = BreathTheme.colors.text
            )

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                modifier = Modifier
                    .padding(horizontal = 32.dp)
                    .animateContentSize(
                        alignment = Alignment.BottomCenter,
                        animationSpec = spring(
                            dampingRatio = Spring.DampingRatioNoBouncy,
                            stiffness = Spring.StiffnessMediumLow
                        )
                    ),
                text = if (screenState.currentSegment is UserScreenSegment.Greet) {
                    stringResource(
                        id = R.string.user_screen_greet_title,
                        String.format("%s", screenState.username ?: "")
                    )
                } else screenState.currentSegment.title.asComposeString(),
                style = BreathTheme.typography.headlineLarge,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(48.dp))

            Text(
                modifier = Modifier
                    .padding(horizontal = 32.dp)
                    .animateContentSize(
                        alignment = Alignment.BottomCenter,
                        animationSpec = spring(
                            dampingRatio = Spring.DampingRatioNoBouncy,
                            stiffness = Spring.StiffnessMediumLow
                        )
                    ),
                text = screenState.currentSegment.description.asComposeString(),
                style = BreathTheme.typography.bodyLarge,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.weight(.5f))

            AnimatedContent(
                targetState = screenState.currentSegment is UserScreenSegment.Username,
                label = ""
            ) {

                if (it) {

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        TextField(
                            value = screenState.usernameText,
                            onValueChange = { newText ->
                                onUIAction(UserScreenUIAction.SetUsernameText(newText))
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
                            isError = screenState.usernameValidationResult
                                    !is UsernameValidationResult.ValidUsername
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        Text(
                            modifier = Modifier
                                .padding(horizontal = 64.dp)
                                .animateContentSize(
                                    alignment = Alignment.BottomCenter,
                                    animationSpec = spring(
                                        dampingRatio = Spring.DampingRatioNoBouncy,
                                        stiffness = Spring.StiffnessMediumLow
                                    )
                                ),
                            text = screenState
                                .usernameValidationResult
                                .errorMessage?.let { msg ->
                                    stringResource(id = msg)
                                } ?: "",
                            style = BreathTheme.typography.bodyMedium,
                            color = Color.Red,
                            textAlign = TextAlign.Center
                        )

                    }

                }

            }

            val usualBedtimeInputState = rememberTimePickerState(
                initialHour = screenState.bedtimeHour,
                initialMinute = screenState.bedtimeMinute,
            )

            LaunchedEffect(
                key1 = usualBedtimeInputState.hour,
                key2 = usualBedtimeInputState.minute
            ) {
                onUIAction(
                    UserScreenUIAction.SetBedtime(
                        hour = usualBedtimeInputState.hour,
                        minute = usualBedtimeInputState.minute
                    )
                )
            }

            val usualWakeUpTimeInputState = rememberTimePickerState(
                initialHour = screenState.wakeUpHour,
                initialMinute = screenState.wakeUpMinute,
            )

            LaunchedEffect(
                key1 = usualWakeUpTimeInputState.hour,
                key2 = usualWakeUpTimeInputState.minute
            ) {
                onUIAction(
                    UserScreenUIAction.SetWakeUp(
                        hour = usualWakeUpTimeInputState.hour,
                        minute = usualWakeUpTimeInputState.minute
                    )
                )
            }

            AnimatedContent(
                modifier = Modifier.fillMaxWidth(),
                targetState = screenState.currentSegment,
                label = ""
            ) { currentSegment ->

                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    when (currentSegment) {

                        is UserScreenSegment.UsualBedTime -> TimeInput(usualBedtimeInputState)

                        is UserScreenSegment.UsualWakeUpTime -> {

                            TimeInput(state = usualWakeUpTimeInputState)

                            Spacer(modifier = Modifier.height(16.dp))

                            Text(
                                modifier = Modifier
                                    .padding(horizontal = 64.dp)
                                    .animateContentSize(
                                        alignment = Alignment.BottomCenter,
                                        animationSpec = spring(
                                            dampingRatio = Spring.DampingRatioNoBouncy,
                                            stiffness = Spring.StiffnessMediumLow
                                        )
                                    ),
                                text = screenState
                                    .sleepValidationResult
                                    .errorMessage?.let { msg ->
                                        stringResource(id = msg)
                                    } ?: "",
                                style = BreathTheme.typography.bodyMedium,
                                color = Color.Red,
                                textAlign = TextAlign.Center
                            )

                        }

                        else -> Unit

                    }

                }

            }

            Spacer(modifier = Modifier.weight(1f))

            UniqueButton(
                onClick = remember(screenState.currentSegment) {
                    {
                        onUIAction(
                            when (screenState.currentSegment) {
                                is UserScreenSegment.Username -> UserScreenUIAction.GoToBedtimeSegment
                                is UserScreenSegment.UsualBedTime -> UserScreenUIAction.GoToWakeUpSegment
                                is UserScreenSegment.UsualWakeUpTime -> UserScreenUIAction.GoToGreetSegment
                                is UserScreenSegment.Greet -> UserScreenUIAction.GetStarted
                            }
                        )
                    }
                },
                content = {

                    Text(
                        modifier = Modifier.animateContentSize(
                            alignment = Alignment.Center,
                            animationSpec = spring(
                                dampingRatio = Spring.DampingRatioNoBouncy,
                                stiffness = Spring.StiffnessMediumLow
                            )
                        ),
                        text = if (screenState.currentSegment == UserScreenSegment.Greet)
                            "Get Started!" else "Next",
                        style = BreathTheme.typography.labelLarge
                    )

                },
                paddingValues = PaddingValues(
                    horizontal = 48.dp,
                    vertical = 12.dp
                )
            )

            Spacer(modifier = Modifier.height(64.dp))

        }

    }

}

@Preview(showBackground = true)
@Composable
private fun UserScreenPreview() = BreathTheme {
    UserScreen(
        screenState = UserScreenState(),
        onUIAction = {}
    )
}