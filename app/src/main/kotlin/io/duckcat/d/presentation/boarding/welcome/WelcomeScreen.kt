package io.duckcat.d.presentation.boarding.welcome

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.stoyanvuchev.systemuibarstweaker.LocalSystemUIBarsTweaker
import io.duckcat.d.R
import io.duckcat.d.core.ui.components.button.UniqueButton
import io.duckcat.d.core.ui.theme.BreathDefaultColors
import io.duckcat.d.core.ui.theme.BreathTheme
import io.duckcat.d.core.ui.theme.LocalColors

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
                darkIcons = screenState.segment !is WelcomeScreenSegment.Soundscape
            )
        )
        onDispose {}
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = Color.Unspecified,
        contentColor = Color.White.copy(alpha = 0.9f)
    ) { insetsPadding ->

        // ✅ Background Image
        Image(
            painter = painterResource(id = R.drawable.figmafakeblur), // Replace with your image resource
            contentDescription = "Background",
            modifier = Modifier.fillMaxSize(),
            contentScale = androidx.compose.ui.layout.ContentScale.Crop
        )

        // ✅ Foreground Content
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

            Spacer(modifier = Modifier.height(20.dp))

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
                if (isVisible) {
                    Column(
                        modifier = Modifier.fillMaxWidth().padding(23.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        HorizontalDivider(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(20.dp)
                                .clip(RoundedCornerShape(50)),
                            color = BreathDefaultColors.background.copy(alpha = 4f)
                        )
                        Text(
                            text = "By clicking Explore you agree to stay productive and stay on the bright side of life",
                                    textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(15.dp))
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
                            is WelcomeScreenSegment.Welcome -> "Explore"
                            else -> "Next"
                        },
                        color = Color.White,
                        style = BreathTheme.typography.labelLarge
                    )
                },
                enabled = true,
                paddingValues = PaddingValues(horizontal = 48.dp, vertical = 12.dp)
            )

            AnimatedContent(
                targetState = screenState.segment is WelcomeScreenSegment.Welcome,
                label = ""
            ) { isVisible ->
                Column {
                    if (isVisible) {
                        Spacer(modifier = Modifier.height(32.dp))
                        Text(
                            text = "",
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
