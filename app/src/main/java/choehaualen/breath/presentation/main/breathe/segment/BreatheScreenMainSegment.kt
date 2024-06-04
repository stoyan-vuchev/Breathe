package choehaualen.breath.presentation.main.breathe.segment

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.KeyboardArrowLeft
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import choehaualen.breath.R
import choehaualen.breath.core.etc.transformFraction
import choehaualen.breath.core.ui.components.rememberBreathRipple
import choehaualen.breath.core.ui.components.topbar.TopBarDefaults
import choehaualen.breath.core.ui.components.topbar.basic_topbar.BasicTopBar
import choehaualen.breath.core.ui.theme.BreathTheme
import choehaualen.breath.presentation.main.breathe.BreatheScreenUIAction
import sv.lib.squircleshape.SquircleShape

@Composable
fun BreatheScreenMainSegment(
    onUIAction: (BreatheScreenUIAction) -> Unit
) {

    val scrollBehavior = TopBarDefaults.exitUntilCollapsedScrollBehavior()

    val bgAlpha by remember(scrollBehavior.state.collapsedFraction) {
        derivedStateOf { scrollBehavior.state.collapsedFraction }
    }

    val topBarBgAlpha by remember(bgAlpha) { // this ensures smooth color transition
        derivedStateOf {
            transformFraction(
                value = 1f - bgAlpha,
                startX = .8f,
                endX = 1f,
                startY = 0f,
                endY = 0f
            )
        }
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(BreathTheme.colors.background.copy(bgAlpha))
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        containerColor = Color.Unspecified,
        contentColor = BreathTheme.colors.text,
        topBar = {

            BasicTopBar(
                titleText = "Breathe",
                scrollBehavior = scrollBehavior,
                navigationIcon = {

                    IconButton(onClick = { onUIAction(BreatheScreenUIAction.NavigateUp) }) {

                        Icon(
                            modifier = Modifier.size(32.dp),
                            imageVector = Icons.AutoMirrored.Rounded.KeyboardArrowLeft,
                            contentDescription = "Navigate up to Home"
                        )

                    }

                },
                backgroundColor = BreathTheme.colors.background.copy(topBarBgAlpha)
            )

        }
    ) { insetsPadding ->

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = insetsPadding,
            verticalArrangement = Arrangement.spacedBy(32.dp)
        ) {

            breatheSegmentsListItem(onUIAction = onUIAction)

            item(key = "suggestion_item") {

                Spacer(modifier = Modifier.height(64.dp))

                HorizontalDivider(
                    modifier = Modifier
                        .padding(horizontal = 72.dp)
                        .fillMaxWidth(),
                    color = BreathTheme.colors.text
                )

                Spacer(modifier = Modifier.height(32.dp))

                Text(
                    modifier = Modifier
                        .padding(horizontal = 32.dp)
                        .fillMaxWidth(),
                    text = "Enter your preferred breathe mode if you are ready",
                    style = BreathTheme.typography.bodyMedium,
                    textAlign = TextAlign.Center
                )

            }

        }

    }

}

private fun LazyListScope.breatheSegmentsListItem(
    onUIAction: (BreatheScreenUIAction) -> Unit
) {

    item(key = "deep_breathing_item") {

        Spacer(modifier = Modifier.height(56.dp))

        BreatheScreenSegmentItem(
            icon = painterResource(id = R.drawable.leaf),
            label = "Deep Breathing",
            description = "Helps to improve mood",
            onClick = {

                onUIAction(
                    BreatheScreenUIAction.SetSegment(
                        BreatheScreenSegmentType.DeepBreathing
                    )
                )

            }
        )

    }

    item(key = "alert_mode_item") {

        BreatheScreenSegmentItem(
            icon = painterResource(id = R.drawable.hr_high),
            label = "Alert Mode (High HR)",
            description = "Increases alertness, feeling sleepy but need to keep awake.",
            onClick = {

                onUIAction(
                    BreatheScreenUIAction.SetSegment(
                        BreatheScreenSegmentType.AlertMode
                    )
                )

            }
        )

    }

    item(key = "calm_mode_item") {

        BreatheScreenSegmentItem(
            icon = painterResource(id = R.drawable.hr_low),
            label = "Calm Mode (Low HR)",
            description = "Helps to decrease stress  quickly",
            onClick = {

                onUIAction(
                    BreatheScreenUIAction.SetSegment(
                        BreatheScreenSegmentType.CalmMode
                    )
                )

            }
        )

    }

}

@Composable
private fun BreatheScreenSegmentItem(
    modifier: Modifier = Modifier,
    icon: Painter,
    label: String,
    description: String,
    onClick: () -> Unit
) {

    Row(
        modifier = modifier
            .then(
                Modifier
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = rememberBreathRipple(color = BreathTheme.colors.background),
                        onClick = onClick
                    )
                    .padding(horizontal = 32.dp)
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Box(
            modifier = Modifier
                .size(64.dp)
                .background(
                    color = BreathTheme.colors.card,
                    shape = SquircleShape(24.dp)
                ),
            contentAlignment = Alignment.Center
        ) {

            Icon(
                modifier = Modifier.size(32.dp),
                painter = icon,
                contentDescription = null
            )

        }

        Spacer(modifier = Modifier.width(32.dp))

        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.Center
        ) {

            Text(
                text = label,
                style = BreathTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = description,
                style = BreathTheme.typography.bodyMedium,
                color = BreathTheme.colors.text.copy(.86f)
            )

        }

        Spacer(modifier = Modifier.width(16.dp))

    }

}