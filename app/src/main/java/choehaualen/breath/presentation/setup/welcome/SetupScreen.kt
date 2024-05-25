package choehaualen.breath.presentation.setup.welcome

import androidx.activity.compose.BackHandler
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import choehaualen.breath.core.ui.colors.LocalColors
import choehaualen.breath.core.ui.components.button.UniqueButton
import choehaualen.breath.core.ui.theme.BreathTheme
import choehaualen.breath.presentation.setup.BASIC_ANIMATION_DURATION
import com.stoyanvuchev.systemuibarstweaker.LocalSystemUIBarsTweaker
import com.stoyanvuchev.systemuibarstweaker.ProvideSystemUIBarsTweaker

@Composable
fun SetupScreen(
    currentSegment: SetupScreenSegment,
    onChangeSegment: (
        currentSegment: SetupScreenSegment,
        isDirectionNext: Boolean
    ) -> Unit
) {

    /// Let's fix the UI here and make it as it should be


    /// I'm only writing down the changes so far, so we don't miss anything
    // when committing.

    BackHandler(
        enabled = currentSegment != SetupScreenSegment.TrackAndImproveSleep,
        onBack = { onChangeSegment(currentSegment, false) }
    )

    val tweaker = LocalSystemUIBarsTweaker.current
    DisposableEffect(currentSegment, tweaker) {
        tweaker.tweakSystemBarsStyle(
            statusBarStyle = tweaker.statusBarStyle.copy(
                darkIcons = true
            ),
            navigationBarStyle = tweaker.navigationBarStyle.copy(
                darkIcons = currentSegment !is SetupScreenSegment.Soundscape,
                enforceContrast = false
            )
        )
        onDispose {}
    }

    CompositionLocalProvider(
        LocalColors provides currentSegment.colors
    ) {

        val primaryBgColor by animateColorAsState(
            targetValue = BreathTheme.colors.backgroundGradientStart,
            label = "",
            animationSpec = tween(durationMillis = BASIC_ANIMATION_DURATION)
        )

        val secondaryBgColor by animateColorAsState(
            targetValue = BreathTheme.colors.backgroundGradientEnd,
            label = "",
            animationSpec = tween(durationMillis = BASIC_ANIMATION_DURATION)
        )

        val background = Brush.verticalGradient(
            colors = listOf(
                primaryBgColor,
                secondaryBgColor
            )
        )

        Scaffold(
            modifier = Modifier
                .fillMaxSize()
                .background(brush = background),
            containerColor = Color.Unspecified,
            contentColor = BreathTheme.colors.text
        ) { insetsPadding ->

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(insetsPadding)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Spacer(modifier = Modifier.weight(1f))

                // I'm not sure it it's similar,it came pretty well, only need to
                //change the reader description to roboto(BOld) oke, spacing is perfect

                // okay

                // The names should be with lowercase letters,
                // Not sure if this is good approach
                // like having it at the center, note sure abt that but on osme screen it comes
                //from bottom to top, and on some it pops from top, yup


                Icon(
                    modifier = Modifier.animateContentSize(
                        alignment = Alignment.BottomCenter,
                        animationSpec = spring(
                            dampingRatio = Spring.DampingRatioNoBouncy,
                            stiffness = Spring.StiffnessMediumLow
                        )
                    ),
                    painter = painterResource(id = currentSegment.icon),
                    contentDescription = null,
                    tint = BreathTheme.colors.text
                )

                Spacer(modifier = Modifier.height(32.dp))

                Text(
                    modifier = Modifier
                        .padding(horizontal = 32.dp)
                        .animateContentSize(
                            alignment = Alignment.BottomCenter,
                            animationSpec = spring(
                                dampingRatio = Spring.DampingRatioNoBouncy,
                                stiffness = Spring.StiffnessMediumLow
                            )
                        ),
                    text = currentSegment.title,
                    style = BreathTheme.typography.headlineLarge,
                    textAlign = TextAlign.Center
                )
// bro bounce on text will be distracting to some users
                // Yes, didn't thought of it
                // We've got rid of almost 90% of Material Theme :D
                // Only the components are left ...

                // Like the custom ones

                // Not sure why it gives an exception :*), font path is correct right
                // It should be
                // Finally, it was compile error? yes./how abt revealing from bottom to top
                // oke, so far im so impressed witht this flexibility
                // If we were using xml, good luck implementing animations xd.true

                // I think we might use navigation, since it will become complicated
                // combining all the screens in one
                // like having those as one in segments
                // and the breath welcome screen as a separate
                // but aint wecome page the part of the onboarding screens?
                // yes, but having a lot of states in a single screen will decrease the
                // performance. let me show something.

                Spacer(modifier = Modifier.height(32.dp))

                Text(
                    modifier = Modifier
                        .padding(horizontal = 32.dp)
                        .animateContentSize(
                            alignment = Alignment.BottomCenter,
                            animationSpec = spring(
                                dampingRatio = Spring.DampingRatioNoBouncy,
                                stiffness = Spring.StiffnessMediumLow
                            )
                        ),
                    text = currentSegment.description,
                    style = BreathTheme.typography.bodyLarge,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(100.dp))
                Spacer(modifier = Modifier.weight(1f))

                UniqueButton(
                    onClick = { onChangeSegment(currentSegment, true) },
                    content = {

                        Text(
                            text = if (currentSegment !is SetupScreenSegment.Privacy)
                                "Next" else "Back",
                            style = BreathTheme.typography.labelMedium
                        )

                    },
                    enabled = true,
                    paddingValues = PaddingValues(
                        horizontal = 72.dp,
                        vertical = 12.dp
                    )
                )

                Spacer(modifier = Modifier.height(72.dp)) // is it too low?,no on figma file its above 65dp
                //but on this it looks too low maybe due to screen dpi, i think 72 is a sweet spot
                // , y
                // Rip lowdpi users xd

                // wait ...
                // The first screen will be visible only on back press?
                // intro screen should show after the breath welcome page,
                // Let's add it. also the above text is in the middle of the screen
                //i mean here

            }

        }

    }

}

@Preview(showBackground = true)
@Composable
private fun SetupScreenPreview() = ProvideSystemUIBarsTweaker {

    SetupScreen(
        currentSegment = SetupScreenSegment.TrackAndImproveSleep,
        onChangeSegment = { _, _ -> }
    )

}