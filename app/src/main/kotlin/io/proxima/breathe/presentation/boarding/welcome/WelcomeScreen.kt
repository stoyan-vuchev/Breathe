package io.proxima.breathe.presentation.boarding.welcome

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.proxima.breathe.R
import io.proxima.breathe.core.ui.BASIC_ANIMATION_DURATION
import io.proxima.breathe.core.ui.theme.LocalColors
import io.proxima.breathe.core.ui.components.button.UniqueButton
import io.proxima.breathe.core.ui.theme.BreathTheme
import com.stoyanvuchev.systemuibarstweaker.LocalSystemUIBarsTweaker

@Composable
fun WelcomeScreen(
    screenState: WelcomeScreenState,
    onUIAction: (WelcomeScreenUIAction) -> Unit
) = CompositionLocalProvider(LocalColors provides screenState.segment.colors) {

    val tweaker = LocalSystemUIBarsTweaker.current

    BackHandler(
        enabled = screenState.segment !is WelcomeScreenSegment.Welcome,
        onBack = { onUIAction(WelcomeScreenUIAction.Back(screenState.segment)) }
    )

    DisposableEffect(screenState.segment, tweaker) {
        tweaker.tweakNavigationBarStyle(
            navigationBarStyle = tweaker.navigationBarStyle.copy(
                darkIcons = screenState.segment !is WelcomeScreenSegment.TrackSleep
                        && screenState.segment !is WelcomeScreenSegment.Soundscape
                        && screenState.segment !is WelcomeScreenSegment.Privacy
            )
        )
        onDispose {}
    }

    val primaryBgColor by animateColorAsState(
        targetValue = if (screenState.segment is WelcomeScreenSegment.Welcome) {
            BreathTheme.colors.background
        } else {
            BreathTheme.colors.backgroundGradientStart
        },
        label = "",
        animationSpec = tween(durationMillis = BASIC_ANIMATION_DURATION)
    )

    val secondaryBgColor by animateColorAsState(
        targetValue = if (screenState.segment is WelcomeScreenSegment.Welcome) {
            BreathTheme.colors.background
        } else {
            BreathTheme.colors.backgroundGradientEnd
        },
        label = "",
        animationSpec = tween(durationMillis = BASIC_ANIMATION_DURATION)
    )

    val background by rememberUpdatedState(
        Brush.verticalGradient(
            colors = listOf(
                primaryBgColor,
                secondaryBgColor
            )
        )
    )

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
                .padding(insetsPadding)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.weight(1f))

            Image(
                painter = painterResource(id = screenState.segment.icon),
                contentDescription = null
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
                text = stringResource(id = screenState.segment.title),
                style = BreathTheme.typography.headlineLarge,
                textAlign = TextAlign.Center
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
                text = stringResource(id = screenState.segment.description),
                style = BreathTheme.typography.bodyLarge,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.weight(1f))

            AnimatedContent(
                targetState = screenState.segment is WelcomeScreenSegment.Welcome,
                label = ""
            ) { isVisible ->

                Column {

                    if (isVisible) {

                        Text(
                            text = "and more",
                            style = BreathTheme.typography.bodyLarge,
                            color = BreathTheme.colors.text
                        )

                        Spacer(modifier = Modifier.height(32.dp))

                    }

                }

            }

            UniqueButton(
                onClick = {
                    onUIAction(WelcomeScreenUIAction.Next(screenState.segment))
                },
                content = {

                    Text(
                        modifier = Modifier.animateContentSize(
                            alignment = Alignment.Center
                        ),
                        text = when (screenState.segment) {
                            is WelcomeScreenSegment.Welcome -> "Get Started"
                            else -> "Next"
                        },
                        style = BreathTheme.typography.labelMedium
                    )

                },
                enabled = true,
                paddingValues = PaddingValues(
                    horizontal = 48.dp,
                    vertical = 12.dp
                )
            )

            AnimatedContent(
                targetState = screenState.segment is WelcomeScreenSegment.Welcome,
                label = ""
            ) { isVisible ->

                Column {

                    if (isVisible) {

                        Spacer(modifier = Modifier.height(32.dp))

                        Text(
                            text = "A project of CS",
                            style = BreathTheme.typography.labelSmall.copy(
                                fontFamily = FontFamily(Font(R.font.roboto_mono_light))
                            ),
                            color = BreathTheme.colors.text.copy(.67f)
                        )

                        Spacer(modifier = Modifier.height(48.dp))

                    } else {

                        Spacer(modifier = Modifier.height(64.dp))

                    }

                }

            }

        }

    }

}

@Preview
@Composable
private fun WelcomeScreenPreview() = BreathTheme {
    WelcomeScreen(
        screenState = WelcomeScreenState(),
        onUIAction = {}
    )
}