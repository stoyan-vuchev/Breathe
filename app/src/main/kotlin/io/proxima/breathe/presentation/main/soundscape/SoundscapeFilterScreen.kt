// SoundscapeFilterScreen.kt
package io.proxima.breathe.presentation.main.soundscape

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import io.proxima.breathe.core.ui.theme.BreathTheme
import io.proxima.breathe.domain.model.SoundScapeItem
import io.proxima.breathe.domain.model.soundScapeItemsList

// A very simple toggle button implementation.
@Composable
fun ToggleButton(
    text: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    androidx.compose.foundation.layout.Box(
        modifier = Modifier
            .padding(4.dp)
            .background(
                color = if (selected) Color.Blue else Color.Gray,
                shape = androidx.compose.foundation.shape.RoundedCornerShape(12.dp)
            )
            .clickable { onClick() }
            .padding(horizontal = 16.dp, vertical = 8.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(text = text, color = Color.White)
    }
}

@Composable
fun SoundscapeFilterScreen(
    viewModel: SoundscapeViewModel,
    onUIAction: (SoundscapeUIAction) -> Unit
) {
    // Observe the state from the view model.
    val screenState by viewModel.screenState.collectAsState()

    Column(modifier = Modifier.fillMaxSize()) {
        // Row with 3 toggle buttons for mood selection.
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            ToggleButton(
                text = "Enhance Sleep",
                selected = screenState.selectedMood == "Enhance Sleep"
            ) { viewModel.onUIAction(SoundscapeUIAction.MoodSelected("Enhance Sleep")) }

            ToggleButton(
                text = "Focus",
                selected = screenState.selectedMood == "Focus"
            ) { viewModel.onUIAction(SoundscapeUIAction.MoodSelected("Focus")) }

            ToggleButton(
                text = "Sad",
                selected = screenState.selectedMood == "Sad"
            ) { viewModel.onUIAction(SoundscapeUIAction.MoodSelected("Sad")) }
        }
        // Grid of filtered soundscape items.
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(screenState.filteredSounds, key = { it.id }) { item: SoundScapeItem ->
                SoundScapeScreenItem(
                    soundscapeItem = item,
                    isPlaying = false, // For preview, we assume nothing is playing.
                    onUIAction = onUIAction
                )
            }
        }
    }
}