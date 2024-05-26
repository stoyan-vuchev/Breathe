package choehaualen.breath.presentation.user

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import choehaualen.breath.core.ui.components.button.UniqueButton
import choehaualen.breath.core.ui.theme.BreathTheme
import com.stoyanvuchev.systemuibarstweaker.LocalSystemUIBarsTweaker

@Composable
fun UserScreen(
    screenState: UserScreenState,
    onUIAction: (UserScreenUIAction) -> Unit
) {

    val background by rememberUpdatedState(
        Brush.verticalGradient(
            colors = listOf(
                BreathTheme.colors.backgroundGradientStart,
                BreathTheme.colors.backgroundGradientEnd
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
                text = screenState.username?.let { "Hey $it! 👋🏼" }
                    ?: "Your name is unique",
                style = BreathTheme.typography.headlineLarge
            )

            Spacer(modifier = Modifier.height(16.dp))

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
                text = screenState.username?.let { "We're glad to see you here!" }
                    ?: "So, what's your name?",
                style = BreathTheme.typography.bodyLarge
            )

            Spacer(modifier = Modifier.weight(1f))

            AnimatedContent(
                targetState = screenState.username == null,
                label = ""
            ) {

                if (it) {

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        TextField(
                            value = screenState.nameText,
                            onValueChange = { newText ->
                                onUIAction(UserScreenUIAction.SetNameText(newText))
                            },
                            colors = TextFieldDefaults.colors(
                                focusedTextColor = BreathTheme.colors.text,
                                unfocusedTextColor = BreathTheme.colors.text,
                                focusedContainerColor = Color.Transparent,
                                unfocusedContainerColor = Color.Transparent,
                                focusedIndicatorColor = BreathTheme.colors.text,
                                unfocusedIndicatorColor = BreathTheme.colors.text,
                                cursorColor = BreathTheme.colors.text
                            )
                        )

                    }

                }

            }

            Spacer(modifier = Modifier.weight(1f))

            UniqueButton(
                onClick = { onUIAction(UserScreenUIAction.Next) },
                content = {

                    Text(
                        text = "Next",
                        style = BreathTheme.typography.labelLarge
                    )

                }
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