package choehaualen.breath.presentation.main.habit.checkpoint

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import choehaualen.breath.R
import choehaualen.breath.core.ui.colors.ZoneColors
import choehaualen.breath.core.ui.colors.backgroundBrush
import choehaualen.breath.core.ui.components.button.UniqueButton
import choehaualen.breath.core.ui.components.topbar.small_topbar.SmallTopBar
import choehaualen.breath.core.ui.theme.BreathTheme

@Composable
fun HabitCheckpointScreen(
    screenState: HabitCheckpointScreenState,
    onUIAction: (HabitCheckpointUIAction) -> Unit
) {

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(BreathTheme.colors.backgroundBrush()),
        containerColor = Color.Unspecified,
        contentColor = BreathTheme.colors.text,
        topBar = {

            SmallTopBar(
                titleText = "Habit Control",
                backgroundColor = Color.Unspecified
            )

        }
    ) { insetsPadding ->

        AnimatedContent(
            modifier = Modifier
                .fillMaxSize()
                .padding(insetsPadding),
            targetState = screenState.currentSegment,
            label = ""
        ) { currentSegment ->

            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Spacer(modifier = Modifier.weight(1f))

                val titleText by rememberUpdatedState(
                    when (currentSegment) {
                        is HabitCheckpointScreenSegment.Checkpoint -> "Did you stay on the ZONE?"
                        is HabitCheckpointScreenSegment.Pass -> "Well done, keep going!"
                        is HabitCheckpointScreenSegment.Fail -> "Remember!"
                    }
                )

                if (currentSegment is HabitCheckpointScreenSegment.Pass) {

                    Icon(
                        modifier = Modifier.size(64.dp),
                        painter = painterResource(id = R.drawable.amuzing_face),
                        contentDescription = null
                    )

                    Spacer(modifier = Modifier.height(32.dp))

                }

                Text(
                    text = titleText,
                    style = BreathTheme.typography.titleLarge,
                    textAlign = TextAlign.Center
                )

                if (currentSegment is HabitCheckpointScreenSegment.Fail) {

                    Spacer(modifier = Modifier.height(32.dp))

                    Text(
                        text = "\"${screenState.habitQuote}\"",
                        style = BreathTheme.typography.titleSmall,
                        textAlign = TextAlign.Center
                    )

                }

                Spacer(modifier = Modifier.weight(1f))

                when (currentSegment) {

                    is HabitCheckpointScreenSegment.Checkpoint -> Row(
                        modifier = Modifier.fillMaxWidth(.72f),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {

                        UniqueButton(
                            onClick = { onUIAction(HabitCheckpointUIAction.Pass) }
                        ) {

                            Text(
                                modifier = Modifier.padding(horizontal = 20.dp),
                                text = "YES!"
                            )

                        }

                        UniqueButton(
                            onClick = { onUIAction(HabitCheckpointUIAction.Fail) }
                        ) {

                            Text(
                                modifier = Modifier.padding(horizontal = 24.dp),
                                text = "No"
                            )

                        }

                    }

                    is HabitCheckpointScreenSegment.Pass -> UniqueButton(
                        onClick = { onUIAction(HabitCheckpointUIAction.Continue) }
                    ) {

                        Text(
                            modifier = Modifier.padding(horizontal = 24.dp),
                            text = "Continue"
                        )

                    }

                    is HabitCheckpointScreenSegment.Fail -> UniqueButton(
                        onClick = { onUIAction(HabitCheckpointUIAction.Stay) }
                    ) {

                        Text(
                            modifier = Modifier.padding(horizontal = 24.dp),
                            text = "Stay"
                        )

                    }

                }

                Spacer(modifier = Modifier.height(56.dp))

            }

        }

    }

}

@Preview(showSystemUi = true)
@Composable
private fun HabitCheckpointScreenPreviewCheckpoint() = BreathTheme(colors = ZoneColors) {
    HabitCheckpointScreen(
        screenState = HabitCheckpointScreenState(),
        onUIAction = {}
    )
}

@Preview(showSystemUi = true)
@Composable
private fun HabitCheckpointScreenPreviewPass() = BreathTheme(colors = ZoneColors) {
    HabitCheckpointScreen(
        screenState = HabitCheckpointScreenState(
            currentSegment = HabitCheckpointScreenSegment.Pass
        ),
        onUIAction = {}
    )
}

@Preview(showSystemUi = true)
@Composable
private fun HabitCheckpointScreenPreviewFail() = BreathTheme(colors = ZoneColors) {
    HabitCheckpointScreen(
        screenState = HabitCheckpointScreenState(
            currentSegment = HabitCheckpointScreenSegment.Fail,
            habitQuote = "I wanna live!"
        ),
        onUIAction = {}
    )
}