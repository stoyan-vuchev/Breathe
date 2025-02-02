package io.proxima.breathe.presentation.boarding

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import io.proxima.breathe.R
import io.proxima.breathe.core.ui.theme.BreathTheme

@Composable
fun SplashElement(
    modifier: Modifier = Modifier
) {
    // Set system bars (status and nav) to black.
    val systemUiController = rememberSystemUiController()
    LaunchedEffect(Unit) {
        systemUiController.setSystemBarsColor(color = Color.Black)
    }

    // Load the Lottie composition from the raw resource.
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.splash_anim))
    // Animate the composition progress.
    val progress by animateLottieCompositionAsState(
        composition = composition,
        useCompositionFrameRate = false,
        iterations = 1,
        ignoreSystemAnimatorScale = true
    )

    // Use a Box that fills the entire screen with a black background.
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .then(modifier)
    ) {
        LottieAnimation(
            modifier = Modifier.fillMaxSize(),
            composition = composition,
            progress = { progress }
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun SplashElementPreview() = BreathTheme {
    SplashElement()
}
