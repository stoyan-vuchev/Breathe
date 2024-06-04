package choehaualen.breath.core.ui.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.IndicationNodeFactory
import androidx.compose.foundation.interaction.HoverInteraction
import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.geometry.center
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.ContentDrawScope
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.node.DelegatableNode
import androidx.compose.ui.node.DrawModifierNode
import choehaualen.breath.core.ui.theme.BreathTheme
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@Composable
fun rememberBreathRipple(color: Color = BreathTheme.colors.primarySoul): BreathRipple {
    val colorState by rememberUpdatedState(color)
    return remember { BreathRipple(colorState) }
}

@Stable
class BreathRipple(
    private val color: Color
) : IndicationNodeFactory {

    override fun create(
        interactionSource: InteractionSource
    ): DelegatableNode = ScaleIndicationNode(
        interactionSource = interactionSource,
        color = color
    )

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || other !is BreathRipple) return false
        if (color != other.color) return false
        return true
    }

    override fun hashCode(): Int = color.hashCode()

    private class ScaleIndicationNode(
        private val interactionSource: InteractionSource,
        private val color: Color
    ) : Modifier.Node(), DrawModifierNode {

        var drawSize = Size.Zero
        var currentPressPositionX = Animatable(drawSize.center.x)
        var currentPressPositionY = Animatable(drawSize.center.y)

        var alpha = Animatable(0f)
        var alphaFactor = Animatable(.1f)
        val animatedScalePercent = Animatable(1f)

        private suspend fun animateToPressed(
            pressPosition: Offset
        ) = coroutineScope {
            launch { currentPressPositionX.animateTo(pressPosition.x) }
            launch { currentPressPositionY.animateTo(pressPosition.y) }
            launch {
                alpha.animateTo(
                    .2f,
                    spring(
                        dampingRatio = Spring.DampingRatioNoBouncy,
                        stiffness = Spring.StiffnessLow
                    )
                )
            }
            launch {
                alphaFactor.animateTo(
                    .67f,
                    spring(
                        dampingRatio = Spring.DampingRatioNoBouncy,
                        stiffness = Spring.StiffnessLow
                    )
                )
            }
            launch {
                animatedScalePercent.animateTo(
                    .925f,
                    spring(
                        dampingRatio = Spring.DampingRatioMediumBouncy,
                        stiffness = Spring.StiffnessMediumLow
                    )
                )
            }
        }

        private suspend fun animateToHovered() = coroutineScope {
            launch { currentPressPositionX.animateTo(drawSize.center.x) }
            launch { currentPressPositionY.animateTo(drawSize.center.y) }
            launch {
                alpha.animateTo(
                    .075f,
                    spring(
                        dampingRatio = Spring.DampingRatioNoBouncy,
                        stiffness = Spring.StiffnessLow
                    )
                )
            }
            launch {
                alphaFactor.animateTo(
                    .67f,
                    spring(
                        dampingRatio = Spring.DampingRatioNoBouncy,
                        stiffness = Spring.StiffnessLow
                    )
                )
            }
            launch {
                animatedScalePercent.animateTo(
                    1.025f,
                    spring(
                        dampingRatio = Spring.DampingRatioMediumBouncy,
                        stiffness = Spring.StiffnessMediumLow
                    )
                )
            }
        }

        private suspend fun animateToResting() = coroutineScope {
            launch {
                alpha.animateTo(
                    0f,
                    spring(
                        dampingRatio = Spring.DampingRatioNoBouncy,
                        stiffness = Spring.StiffnessLow
                    )
                )
            }
            launch {
                alphaFactor.animateTo(
                    .1f,
                    spring(
                        dampingRatio = Spring.DampingRatioNoBouncy,
                        stiffness = Spring.StiffnessLow
                    )
                )
            }
            launch {
                animatedScalePercent.animateTo(
                    1f,
                    spring(
                        dampingRatio = Spring.DampingRatioMediumBouncy,
                        stiffness = Spring.StiffnessMediumLow
                    )
                )
            }
        }

        override fun onAttach() {
            coroutineScope.launch {
                interactionSource.interactions.collectLatest { interaction ->
                    when (interaction) {
                        is PressInteraction.Press -> animateToPressed(interaction.pressPosition)
                        is PressInteraction.Release -> animateToResting()
                        is PressInteraction.Cancel -> animateToResting()
                        is HoverInteraction.Exit -> animateToResting()
                        is HoverInteraction.Enter -> animateToHovered()
                    }
                }
            }
        }

        override fun ContentDrawScope.draw() {
            drawSize = lazy { this.size }.value
            scale(scale = animatedScalePercent.value) {
                this@draw.drawContent()
                drawCircle(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            color.copy(alpha.value),
                            color.copy(0f)
                        ),
                        center = Offset(
                            currentPressPositionX.value,
                            currentPressPositionY.value
                        ),
                        radius = this.size.maxDimension * alphaFactor.value
                    ),
                    radius = this.size.maxDimension,
                    center = Offset(
                        currentPressPositionX.value,
                        currentPressPositionY.value
                    )
                )
            }
        }
    }

}