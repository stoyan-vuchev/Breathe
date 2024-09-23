package io.proxima.breathe.presentation.main.breathe.segment

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.KeyboardArrowLeft
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import io.proxima.breathe.core.ui.components.topbar.TopBarDefaults
import io.proxima.breathe.core.ui.components.topbar.basic_topbar.BasicTopBar
import io.proxima.breathe.core.ui.theme.BreathTheme
import io.proxima.breathe.presentation.main.breathe.BreatheScreenUIAction

@Composable
fun BreatheScreenSegment(
    segmentType: BreatheScreenSegmentType,
    segmentState: BreatheScreenSegmentState,
    onUIAction: (BreatheScreenUIAction) -> Unit
) {

    DisposableEffect(Unit) {
        onUIAction(BreatheScreenUIAction.StartSegment(segmentType))
        onDispose { onUIAction(BreatheScreenUIAction.ClearSegment) }
    }

    val scrollBehavior = TopBarDefaults.exitUntilCollapsedScrollBehavior()

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        containerColor = Color.Unspecified,
        contentColor = BreathTheme.colors.text,
        topBar = {

            BasicTopBar(
                titleText = stringResource(id = segmentType.title),
                navigationIcon = {

                    IconButton(
                        onClick = {

                            onUIAction(
                                BreatheScreenUIAction.SetSegment(
                                    BreatheScreenSegmentType.Main
                                )
                            )

                        }
                    ) {

                        Icon(
                            modifier = Modifier.size(32.dp),
                            imageVector = Icons.AutoMirrored.Rounded.KeyboardArrowLeft,
                            contentDescription = "Navigate back to Main segment"
                        )

                    }

                },
                scrollBehavior = scrollBehavior
            )

        }
    ) { insetsPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(insetsPadding)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Spacer(modifier = Modifier.weight(1f))

            InhaleExhaleCircle(segmentState = segmentState)

            Spacer(modifier = Modifier.height(32.dp))

            val counterText by rememberUpdatedState(segmentState.countDown.toString())

            Text(
                modifier = Modifier.animateContentSize(alignment = Alignment.Center),
                text = counterText,
                style = BreathTheme.typography.headlineSmall
            )

            Spacer(modifier = Modifier.height(32.dp))

            BreathMeter(segmentState = segmentState)

            Spacer(modifier = Modifier.weight(1f))

        }

    }

}