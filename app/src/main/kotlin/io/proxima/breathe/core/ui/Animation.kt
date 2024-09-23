package io.proxima.breathe.core.ui

import androidx.compose.foundation.pager.PagerState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.util.lerp
import kotlin.math.absoluteValue

// The default animation duration.
const val BASIC_ANIMATION_DURATION = 360

fun Modifier.carouselTransition(
    itemPage: Int,
    pagerState: PagerState,
    minAlpha: Float = .5f,
    minScale: Float = .85f
) = composed {

    val pageOffset by remember {
        derivedStateOf {
            ((pagerState.currentPage - itemPage).toFloat() + pagerState.currentPageOffsetFraction)
                .absoluteValue
        }
    }

    val alpha by remember {
        derivedStateOf {
            lerp(
                start = minAlpha,
                stop = 1f,
                fraction = 1f - pageOffset.coerceIn(0f, 1f)
            )
        }
    }

    val scale by remember {
        derivedStateOf {
            lerp(
                start = minScale,
                stop = 1f,
                fraction = 1f - pageOffset.coerceIn(0f, 1f)
            )
        }
    }

    graphicsLayer {
        this.alpha = alpha
        this.scaleX = scale
        this.scaleY = scale
    }

}