package io.proxima.breathe.presentation.boarding

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import io.proxima.breathe.R
import io.proxima.breathe.core.ui.theme.BreathTheme
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition

@Composable
fun SplashElement(
    modifier: Modifier = Modifier
) {

    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.splash_anim))
    val progress by animateLottieCompositionAsState(
        composition = composition,
        useCompositionFrameRate = false,
        iterations = 1,
        ignoreSystemAnimatorScale = true
    )

    LottieAnimation(
        modifier = modifier,
        composition = composition,
        progress = { progress }
    )

}

@Preview
@Composable
private fun SplashElementPreview() = BreathTheme {
    SplashElement()
}