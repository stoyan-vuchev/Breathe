package choehaualen.breath.presentation.setup.welcome

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
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
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import choehaualen.breath.R
import choehaualen.breath.core.ui.components.button.UniqueButton
import choehaualen.breath.core.ui.theme.BreathTheme
import com.stoyanvuchev.systemuibarstweaker.LocalSystemUIBarsTweaker

@Composable
fun WelcomeScreen(
    onNavigateToSetup: () -> Unit
) {

    val tweaker = LocalSystemUIBarsTweaker.current
    DisposableEffect(tweaker) {
        tweaker.tweakSystemBarsStyle(
            statusBarStyle = tweaker.statusBarStyle.copy(
                darkIcons = true
            ),
            navigationBarStyle = tweaker.navigationBarStyle.copy(
                darkIcons = true
            )
        )
        onDispose {}
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = BreathTheme.colors.background,
        contentColor = BreathTheme.colors.text
    ) { insetsPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(insetsPadding)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.height(200.dp))

            Image(
                painter = painterResource(id = R.drawable.splash_logo),
                contentDescription = "Breath Logo"
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
                text = stringResource(id = R.string.app_name),
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
                text = "An app to keep your stress away",
                style = BreathTheme.typography.labelLarge,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(100.dp))
            Spacer(modifier = Modifier.weight(1f))

            Text(
                text = "and more",
                style = BreathTheme.typography.labelMedium,
                color = BreathTheme.colors.text
            )

            Spacer(modifier = Modifier.height(32.dp))

            UniqueButton(
                onClick = onNavigateToSetup,
                content = {

                    Text(
                        text = "Get Started",
                        style = BreathTheme.typography.labelMedium
                    )

                },
                enabled = true,
                paddingValues = PaddingValues(
                    horizontal = 48.dp,
                    vertical = 12.dp
                )
            )

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = "By team Proxima",
                style = BreathTheme.typography.labelSmall.copy(
                    fontFamily = FontFamily(Font(R.font.roboto_mono_light))
                ),
                color = BreathTheme.colors.text.copy(.67f)
            )

            Spacer(modifier = Modifier.height(48.dp))

        }

    }

}

@Preview
@Composable
private fun WelcomeScreenPreview() = BreathTheme {

    WelcomeScreen(
        onNavigateToSetup = {}
    )

}