package io.proxima.breathe.core.ui.components.button

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Indication
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import io.proxima.breathe.core.ui.theme.swapColor
import io.proxima.breathe.core.ui.components.rememberBreathRipple
import io.proxima.breathe.core.ui.theme.BreathTheme
import io.proxima.breathe.core.ui.BASIC_ANIMATION_DURATION
import sv.lib.squircleshape.SquircleShape

/**
 * A unique custom clickable UI component for initiating actions.
 *
 * @param modifier Apply further customization.
 * @param backgroundColor The background color of the button.
 * @param contentColor The color of the button content e.g. text.
 * @param shape The shape of the button.
 * @param enabled Whether the button is enabled or not.
 * @param onClick A callback to initiate an action when the button is clicked.
 * @param onClickLabel Add an optional short description of the button action.
 * @param indication An optional indication of the clicks drawn on the screen.
 * @param interactionSource An optional interaction source coming outside of button.
 * @param paddingValues An optional padding values.
 * @param borderColor An optional border color; if provided, an outline will be drawn.
 * @param borderWidth The width of the border if [borderColor] is provided.
 * @param content The content of the button e.g. [Text].
 */
@Composable
fun UniqueButton(
    modifier: Modifier = Modifier,
    backgroundColor: Color = BreathTheme.colors.swapColor(BreathTheme.colors.background),
    contentColor: Color = BreathTheme.colors.swapColor(Color.Black),
    shape: Shape = UniqueButtonDefaults.shape,
    enabled: Boolean = true,
    onClick: () -> Unit,
    onClickLabel: String? = null,
    indication: Indication? = rememberBreathRipple(contentColor),
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    paddingValues: PaddingValues = UniqueButtonDefaults.paddingValues,
    borderColor: Color = Color.White,
    borderWidth: Dp = 1.dp,
    content: @Composable () -> Unit
) = CompositionLocalProvider(
    LocalContentColor provides contentColor,
    content = {

        val alpha by animateAlpha(enabled)
        val scale by animateScale(enabled)

        Box(
            modifier = Modifier.uniqueButtonModifier(
                onClick = onClick,
                alpha = alpha,
                scale = scale,
                shape = shape,
                enabled = enabled,
                onClickLabel = onClickLabel,
                indication = indication,
                interactionSource = interactionSource,
                backgroundColor = backgroundColor,
                paddingValues = paddingValues,
                otherModifier = modifier,
                borderColor = borderColor,
                borderWidth = borderWidth
            ),
            contentAlignment = Alignment.Center,
            content = { content() }
        )
    }
)

/**
 * A simplified modifier function for simplifying the code inside the [UniqueButton].
 */
private fun Modifier.uniqueButtonModifier(
    onClick: () -> Unit,
    alpha: Float,
    scale: Float,
    shape: Shape,
    enabled: Boolean,
    onClickLabel: String?,
    indication: Indication?,
    interactionSource: MutableInteractionSource,
    backgroundColor: Color,
    paddingValues: PaddingValues,
    otherModifier: Modifier,
    borderColor: Color? = null,
    borderWidth: Dp = 1.dp
): Modifier = this
    .semantics { contentDescription = onClickLabel ?: "" }
    .alpha(alpha)
    .clickable(
        interactionSource = interactionSource,
        indication = indication,
        enabled = enabled,
        role = Role.Button,
        onClick = onClick
    )
    .scale(scale)
    .clip(shape)
    // Add border if a border color is provided.
    .then(
        if (borderColor != null) Modifier.border(borderWidth, borderColor, shape) else Modifier
    )
    .background(Color.White.copy(alpha = 0.3f))
    .then(otherModifier)
    .then(Modifier.padding(paddingValues))

@Composable
private fun animateAlpha(enabled: Boolean) = animateFloatAsState(
    targetValue = if (enabled) 1f else .33f,
    label = "UniqueButton: Alpha",
    animationSpec = tween(durationMillis = BASIC_ANIMATION_DURATION)
)

@Composable
private fun animateScale(enabled: Boolean) = animateFloatAsState(
    targetValue = if (enabled) 1f else .9f,
    label = "UniqueButton: Scale",
    animationSpec = tween(durationMillis = BASIC_ANIMATION_DURATION)
)

/**
 * A collection of default and other values/properties of a [UniqueButton].
 */
object UniqueButtonDefaults {

    /**
     * The default shape of a [UniqueButton].
     */
    val shape: Shape
        get() = SquircleShape(cornerSmoothing = .0f)

    /**
     * The default padding values of a [UniqueButton].
     */
    val paddingValues: PaddingValues
        get() = PaddingValues(
            horizontal = 22.dp,
            vertical = 10.dp
        )

}

@Preview(showBackground = true)
@Composable
private fun UniqueButtonPreview() = BreathTheme {

    Box(
        modifier = Modifier
            .background(Color.White.copy(alpha = 0.5f))
            .padding(32.dp),
        contentAlignment = Alignment.Center
    ) {

        var clicked by remember { mutableStateOf(false) }

        UniqueButton(
            onClick = { clicked = !clicked },
            // Here we set a custom border color.
            borderColor = Color.White,
            content = {

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {

                    AnimatedContent(
                        targetState = clicked,
                        label = "UniqueButtonAnim"
                    ) {

                        if (it) {
                            Icon(
                                modifier = Modifier
                                    .padding(end = 16.dp)
                                    .size(16.dp),
                                imageVector = Icons.Rounded.Check,
                                contentDescription = null
                            )
                        }

                    }

                    Text( text = "Enabled Button",
                        color = Color.White)
                }
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun DisabledUniqueButtonPreview() = BreathTheme {

    Box(
        modifier = Modifier
            .background(BreathTheme.colors.background)
            .padding(32.dp),
        contentAlignment = Alignment.Center
    ) {

        UniqueButton(
            enabled = false,
            onClick = {},
            content = { Text(text = "Disabled Button") }
        )
    }
}
