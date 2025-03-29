package io.proxima.breathe.core.ui

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.EaseOutQuart
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.runtime.Stable

@Stable
object ScreenTransitions {

    val enterTransition: EnterTransition =
        scaleIn(
            initialScale = .75f,
            animationSpec = tween(
                durationMillis = 500, // Adjust as needed
                easing = FastOutSlowInEasing // Starts fast and slows down
            )
        ) + fadeIn(
            animationSpec = tween(
                durationMillis = 500,
                easing = FastOutSlowInEasing
            )
        )



    val exitTransition: ExitTransition =
        scaleOut(
            targetScale = 1.25f,
            animationSpec = tween(
                durationMillis = 500,
                easing = FastOutSlowInEasing
            )
        ) + fadeOut(
            animationSpec = tween(
                durationMillis = 500,
                easing = FastOutSlowInEasing
            )
        )



    val popEnterTransition: EnterTransition =
        scaleIn(
            initialScale = 1.25f,
            animationSpec = tween(easing = EaseOutQuart)
        ) + fadeIn(
            animationSpec = spring(
                dampingRatio = Spring.DampingRatioNoBouncy,
                stiffness = Spring.StiffnessMedium
            )
        )

    val popExitTransition: ExitTransition =
        scaleOut(
            targetScale = .75f,
            animationSpec = tween(easing = EaseOutQuart)
        ) + fadeOut(
            animationSpec = spring(
                dampingRatio = Spring.DampingRatioNoBouncy,
                stiffness = Spring.StiffnessHigh
            )
        )

}