package choehaualen.breath.core.ui.components.button

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import choehaualen.breath.core.ui.colors.swapColor
import choehaualen.breath.core.ui.theme.BreathTheme

@Composable
fun UniqueButton(
    modifier: Modifier = Modifier,
    backgroundColor: Color = BreathTheme.colors.swapColor(BreathTheme.colors.background),
    contentColor: Color = BreathTheme.colors.swapColor(BreathTheme.colors.text),
    shape: Shape = RoundedCornerShape(50),
    enabled: Boolean = true, // enabled by default.
    onClick: () -> Unit,
    content: @Composable RowScope.() -> Unit
) {

    Box(
        modifier = Modifier
            .clip(shape)
            .background(color = backgroundColor)
            .then(modifier),
        contentAlignment = Alignment.Center,
        content = {

            Row( // because of the drag mode :*) unexpected yuh, thought there was a bug
                modifier = Modifier
                    .height(48.dp)
                    .clickable(
                        enabled = enabled,
                        onClick = onClick
                    )
                    .padding(
                        horizontal = 24.dp,
                        vertical = 12.dp
                    ), // Voiala AWESOME!
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp) // this allows further customization
            ) {

                CompositionLocalProvider(
                    LocalContentColor provides contentColor,
                    content = { content() }
                )

            }

        }
    )

}

@Preview(showBackground = true)
@Composable
private fun UniqueButtonPreview() {

    Box(
        modifier = Modifier.padding(32.dp),
        contentAlignment = Alignment.Center
    ) {

        var clicked by remember { mutableStateOf(false) } // btw to rename sth double click on it, then hit shift + f6 to change it everywhere where it's being used.
        // not sure why it's not updating

        // I'll try sth else.

        UniqueButton(
            modifier = Modifier,
            onClick = { clicked = !clicked },
            content = {

                AnimatedContent(
                    targetState = clicked
                ) {

                    if (it) {
                        Icon(modifier = Modifier.size(20.dp),
                            imageVector = Icons.Rounded.Check, contentDescription = null)
                    }

                    // It's kinda choppy, smooth! yass
                    // oke let's move on :) I wanna experience the animation on the emulator :DD do!!Okeee

                }

                Text(text = "Unique Button")

            }
        )

    }

}