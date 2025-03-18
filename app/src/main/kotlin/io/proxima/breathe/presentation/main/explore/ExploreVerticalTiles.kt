package io.proxima.breathe.presentation.main.explore

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.HazeStyle

import dev.chrisbanes.haze.haze
import dev.chrisbanes.haze.hazeChild

import io.proxima.breathe.R

@Composable
fun ExploreVerticalTiles(
    header: String,
    description: String,
    imageRes: Int,
    progress: Float, // 0f = no blur, 1f = maximum blur at the top
    cornerRadius: Dp = 30.dp,
    maxBlur: Dp = 20.dp, // maximum blur value
    onClick: () -> Unit
) {
    // Create haze state for the overlay.
    val hazeState = remember { HazeState() }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(180.dp)
            .clip(RoundedCornerShape(cornerRadius))
            .clickable { onClick() }


    ) {
        // Draw the sharp background image.
        Image(
            painter = painterResource(id = imageRes),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        // Blurred overlay layer using hazeChild with a vertical gradient mask.
        Box(
            modifier = Modifier
                .matchParentSize()
                .haze(hazeState)
                .hazeChild(
                    state = hazeState,
                    style = HazeStyle(
                        blurRadius = (maxBlur.value * progress).dp,
                        tint = Color.Black.copy(alpha = 0.3f * progress),
                    )
                )

        )
        // Foreground text layer: drawn on top of the blurred overlay.
        Column(
            modifier = Modifier
                .matchParentSize()
                .padding(top = 16.dp, start = 16.dp)
                .zIndex(2f),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = header,
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.White
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = description,
                fontSize = 14.sp,
                color = Color.White.copy(alpha = 0.9f)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ExploreVerticalTilesPreview() {
    ExploreVerticalTiles(
        header = "Effective Learning",
        description = "Organize your study schedule for maximum retention.",
        imageRes = R.drawable.stressback, // Replace with a valid drawable resource
        progress = 0.5f, // Example progress value
        onClick = {} // No-op click action for preview
    )
}

