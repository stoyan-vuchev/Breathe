package io.proxima.breathe.presentation.main.settings.about

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.KeyboardArrowLeft
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import io.proxima.breathe.BuildConfig
import io.proxima.breathe.R
import io.proxima.breathe.core.ui.components.button.UniqueButton
import io.proxima.breathe.core.ui.components.topbar.TopBarDefaults
import io.proxima.breathe.core.ui.components.topbar.basic_topbar.BasicTopBar
import io.proxima.breathe.core.ui.theme.BreathDefaultColors
import io.proxima.breathe.core.ui.theme.BreathTheme
import io.proxima.breathe.core.ui.theme.DreamyNightColors
import io.proxima.breathe.core.ui.theme.MangoColors
import io.proxima.breathe.core.ui.theme.MelonColors
import io.proxima.breathe.core.ui.theme.SkyBlueColors
import io.proxima.breathe.core.ui.theme.SleepColors
import io.proxima.breathe.core.ui.theme.ZoneColors
import sv.lib.squircleshape.SquircleShape

@Composable
fun SettingsAboutScreen(
    onUIAction: (SettingsAboutScreenUIAction) -> Unit
) {

    val scrollBehavior = TopBarDefaults.exitUntilCollapsedScrollBehavior()
    val infiniteTransition = rememberInfiniteTransition(label = "")

    val gradientStart by infiniteTransition.animateColor(
        initialValue = Color.Unspecified,
        targetValue = Color.Unspecified,
        animationSpec = infiniteRepeatable(
            animation = keyframes {
                BreathDefaultColors.backgroundGradientStart at 0
                SleepColors.backgroundGradientStart at 2000
                MelonColors.backgroundGradientStart at 4000
                DreamyNightColors.backgroundGradientStart at 6000
                SkyBlueColors.backgroundGradientStart at 8000
                ZoneColors.backgroundGradientStart at 10000
                MangoColors.backgroundGradientStart at 12000
                durationMillis = 12000
            },
            repeatMode = RepeatMode.Reverse
        ),
        label = ""
    )

    val gradientEnd by infiniteTransition.animateColor(
        initialValue = Color.Unspecified,
        targetValue = Color.Unspecified,
        animationSpec = infiniteRepeatable(
            animation = keyframes {
                BreathDefaultColors.backgroundGradientEnd at 0
                SleepColors.backgroundGradientEnd at 2000
                MelonColors.backgroundGradientEnd at 4000
                DreamyNightColors.backgroundGradientEnd at 6000
                SkyBlueColors.backgroundGradientEnd at 8000
                ZoneColors.backgroundGradientEnd at 10000
                MangoColors.backgroundGradientEnd at 12000
                durationMillis = 12000
            },
            repeatMode = RepeatMode.Reverse
        ),
        label = ""
    )

    val background by rememberUpdatedState(
        Brush.verticalGradient(
            colors = listOf(gradientStart, gradientEnd)
        )
    )

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(background)
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        containerColor = Color.Unspecified,
        contentColor = BreathTheme.colors.text,
        topBar = {

            BasicTopBar(
                titleText = "About",
                scrollBehavior = scrollBehavior,
                navigationIcon = {

                    IconButton(
                        onClick = { onUIAction(SettingsAboutScreenUIAction.NavigateUp) }
                    ) {

                        Icon(
                            modifier = Modifier.size(32.dp),
                            imageVector = Icons.AutoMirrored.Rounded.KeyboardArrowLeft,
                            contentDescription = "Navigate up to Settings."
                        )

                    }

                },
                backgroundColor = Color.Unspecified
            )

        }
    ) { insetsPadding ->

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = insetsPadding,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            item(
                key = "item_top_spacer",
                content = { Spacer(modifier = Modifier.height(24.dp)) }
            )

            item(
                key = "item_app_logo_name_card",
                content = {

                    Row(
                        modifier = Modifier
                            .padding(horizontal = 24.dp)
                            .fillMaxWidth()
                            .clip(SquircleShape(16.dp))
                            .background(BreathTheme.colors.background.copy(.9f))
                            .padding(32.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {

                        Image(
                            modifier = Modifier.size(48.dp),
                            painter = painterResource(id = R.drawable.splash_logo),
                            contentDescription = null
                        )

                        Spacer(modifier = Modifier.width(32.dp))

                        Text(
                            text = stringResource(id = R.string.app_name),
                            style = BreathTheme.typography.titleLarge,
                            color = BreathTheme.colors.text
                        )

                    }

                }
            )

            item(
                key = "item_app_version_card",
                content = {

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        modifier = Modifier
                            .padding(horizontal = 24.dp)
                            .fillMaxWidth()
                            .clip(SquircleShape(16.dp))
                            .background(BreathTheme.colors.background.copy(.67f))
                            .padding(20.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {

                        Text(
                            text = "Version ${BuildConfig.VERSION_NAME}",
                            style = BreathTheme.typography.labelLarge,
                            color = BreathTheme.colors.text
                        )

                    }

                }
            )

            item(
                key = "item_open_source_licenses",
                content = {

                    Spacer(modifier = Modifier.height(64.dp))

                    UniqueButton(
                        onClick = {}
                    ) {

                        Text(
                            text = "Open Source Licenses",
                            style = BreathTheme.typography.labelLarge
                        )

                    }

                }
            )

            item(
                key = "item_quote",
                content = {

                    Spacer(modifier = Modifier.height(64.dp))

                    Text(
                        text = "“The eternal void is filled\n" +
                                " with infinite possibilities.”\n\n" +
                                "- Laozi",
                        style = BreathTheme.typography.labelLarge,
                        color = BreathTheme.colors.text,
                        textAlign = TextAlign.End
                    )

                }
            )

            item(
                key = "item_credits",
                content = {

                    Spacer(modifier = Modifier.height(56.dp))

                    Text(
                        text = "A project of CS",
                        style = BreathTheme.typography.labelSmall.copy(
                            fontFamily = FontFamily(Font(R.font.roboto_mono_light))
                        ),
                        color = BreathTheme.colors.text.copy(.5f)
                    )

                    Spacer(modifier = Modifier.height(32.dp))

                }
            )

        }

    }

}