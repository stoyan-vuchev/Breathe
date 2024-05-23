package choehaualen.breath.presentation.setup

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import choehaualen.breath.core.ui.colors.LocalColors
import choehaualen.breath.core.ui.components.button.UniqueButton
import choehaualen.breath.core.ui.theme.BreathTheme
import com.stoyanvuchev.systemuibarstweaker.LocalSystemUIBarsTweaker

@Composable
fun SetupScreen(
    currentSegment: SetupScreenSegment,
    onChangeSegment: (
        currentSegment: SetupScreenSegment,
        isDirectionNext: Boolean
    ) -> Unit
) {

    BackHandler(
        enabled = currentSegment != SetupScreenSegment.TrackAndImproveSleep,
        onBack = { onChangeSegment(currentSegment, false) }
    )

    val tweaker = LocalSystemUIBarsTweaker.current
    DisposableEffect(currentSegment, tweaker) {
        tweaker.tweakStatusBarStyle(
            statusBarStyle = tweaker.statusBarStyle.copy(
                darkIcons = true
            )
        )
        onDispose {}
    }

    CompositionLocalProvider(
        LocalColors provides currentSegment.colors
    ) {

        val primaryBgColor by animateColorAsState(
            targetValue = BreathTheme.colors.backgroundGradientStart,
            label = "",
            animationSpec = tween(durationMillis = BASIC_ANIMATION_DURATION)
        )

        val secondaryBgColor by animateColorAsState(
            targetValue = BreathTheme.colors.backgroundGradientEnd,
            label = "",
            animationSpec = tween(durationMillis = BASIC_ANIMATION_DURATION)
        )

        val background = Brush.verticalGradient(
            colors = listOf(
                primaryBgColor,
                secondaryBgColor
            )
        )

        Scaffold(
            modifier = Modifier
                .fillMaxSize()
                .background(brush = background),
            containerColor = Color.Unspecified,
            contentColor = BreathTheme.colors.text
        ) { insetsPadding ->

            AnimatedContent(
                modifier = Modifier
                    .fillMaxSize(),
                targetState = currentSegment,
                label = "",
                transitionSpec = {

                    fadeIn(
                        animationSpec = tween(durationMillis = BASIC_ANIMATION_DURATION)
                    ) togetherWith fadeOut(
                        animationSpec = tween(durationMillis = BASIC_ANIMATION_DURATION)
                    )

                }
            ) { segment ->

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(insetsPadding)
                        .verticalScroll(rememberScrollState()),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Spacer(modifier = Modifier.height(200.dp))

                    Text(
                        modifier = Modifier.padding(horizontal = 32.dp),
                        text = segment.title,
                        style = BreathTheme.typography.headlineLarge,
                        textAlign = TextAlign.Center
                    )

                    // We've got rid of almost 90% of Material Theme :D
                    // Only the components are left ...

                    // Like the custom ones

                    Spacer(modifier = Modifier.height(32.dp))

                    Text(
                        modifier = Modifier.padding(horizontal = 32.dp),
                        text = segment.description,
                        style = BreathTheme.typography.bodyLarge,
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.weight(1f))

                    UniqueButton(
                        onClick = { onChangeSegment(segment, true) },
                        content = {

                            Text(
                                text = if (currentSegment !is SetupScreenSegment.Privacy)
                                    "Next" else "Back"
                            )

                        },
                        enabled = true
                    )

                    Spacer(modifier = Modifier.height(100.dp))

                }

            }

        }

    }

}