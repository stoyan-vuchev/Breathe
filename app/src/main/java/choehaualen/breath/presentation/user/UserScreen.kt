package choehaualen.breath.presentation.user

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import choehaualen.breath.core.ui.theme.BreathTheme
import sv.lib.squircleshape.SquircleShape

@Composable
fun UserScreen(
    modifier: Modifier = Modifier,
    user: String?,
    name: String,
    onSetNameText: (String) -> Unit,
    onNext: () -> Unit,
    onFlush: () -> Unit
) {

    // It gets kinda complex, but let's swap UserEntity with a string

    BackHandler(
        enabled = user != null,
        onBack = onFlush
    )

    AnimatedContent(
        targetState = user,
        label = ""
    ) {

        if (it == null) {

            Column(
                modifier = modifier,
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Spacer(modifier = Modifier.height(200.dp))

                Text(
                    text = "Your name is unique",
                    style = MaterialTheme.typography.headlineMedium
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "So, what's your name?",
                    style = MaterialTheme.typography.bodyLarge
                )

                Spacer(modifier = Modifier.weight(1f))

                TextField(
                    value = name,
                    onValueChange = onSetNameText,
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

                Spacer(modifier = Modifier.weight(1f))

                Button(
                    onClick = onNext,
                    content = { Text(text = "Next") },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = BreathTheme.colors.text,
                        contentColor = BreathTheme.colors.background
                    ),
                    shape = SquircleShape()
                )

                Spacer(modifier = Modifier.height(100.dp))

            }

        } else {

            Column(
                modifier = modifier,
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Spacer(modifier = Modifier.height(200.dp))

                Text(
                    text = "Hey $it! 👋🏼",
                    style = MaterialTheme.typography.headlineMedium
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "We're glad to see you here!",
                    style = MaterialTheme.typography.bodyLarge
                )

                Spacer(modifier = Modifier.weight(1f))

                Button(
                    onClick = onFlush,
                    content = { Text(text = "Flush") },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = BreathTheme.colors.text,
                        contentColor = BreathTheme.colors.background
                    ),
                    shape = SquircleShape()
                )

                Spacer(modifier = Modifier.height(100.dp))

                // Each screen has it's own separated place.
                // So what do you want to do first?
                //i just try to do the main screen layout and some setup screen layout like the first time welcome screen
                // got it;
            }

        }

    }

}

@Preview(showBackground = true)
@Composable
private fun UserScreenPreview() {

    UserScreen(
        modifier = Modifier.fillMaxSize(),
        user = null,
        name = "",
        onSetNameText = {},
        onNext = {},
        onFlush = {}
    )

}