// File: SoundscapeFilterScreen.kt
package io.proxima.breathe.presentation.main.soundscape

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.tooling.preview.Preview
import io.proxima.breathe.core.ui.theme.BreathTheme
import io.proxima.breathe.domain.model.SoundScapeItem
import io.proxima.breathe.domain.model.soundScapeItemsList
import io.proxima.breathe.R

// A simple toggle button implementation
@Composable
fun ToggleButton(
    text: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .padding(4.dp)
            .background(
                color = if (selected) Color.White.copy(alpha = 0.5f) else Color.Gray.copy(alpha = 0.2f),
                shape = androidx.compose.foundation.shape.RoundedCornerShape(100.dp)
            )
            .clickable { onClick() }
            .padding(horizontal = 16.dp, vertical = 8.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(text = text, color = Color.White)
    }
}

// This composable contains all the UI logic and accepts the state as a parameter.
// It can be used for previewing as well as in production.
@Composable
fun SoundscapeFilterScreenContent(
    screenState: SoundscapeScreenState,
    onUIAction: (SoundscapeUIAction) -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {
        // 🌄 Background Image Layer
        androidx.compose.foundation.Image(
            painter = androidx.compose.ui.res.painterResource(id = R.drawable.figmafakeblur), // Replace with your drawable
            contentDescription = "Background",
            modifier = Modifier.fillMaxSize(),
            contentScale = androidx.compose.ui.layout.ContentScale.Crop
        )

        
        // 🌟 Foreground Content
        Column(modifier = Modifier.fillMaxSize()) {
            Spacer(modifier = Modifier.height(45.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                ToggleButton(
                    text = "Enhance Sleep",
                    selected = screenState.selectedMood == "Enhance Sleep"
                ) { onUIAction(SoundscapeUIAction.MoodSelected("Enhance Sleep")) }
                ToggleButton(
                    text = "Focus",
                    selected = screenState.selectedMood == "Focus"
                ) { onUIAction(SoundscapeUIAction.MoodSelected("Focus")) }
                ToggleButton(
                    text = "Sad",
                    selected = screenState.selectedMood == "Sad"
                ) { onUIAction(SoundscapeUIAction.MoodSelected("Sad")) }
            }
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(screenState.filteredSounds, key = { it.id }) { item ->
                    SoundScapeScreenItem(
                        soundscapeItem = item,
                        isPlaying = false,
                        onUIAction = onUIAction
                    )
                }
            }
        }
    }
}


// Production composable that uses the ViewModel to observe state and then calls the content composable.
@Composable
fun SoundscapeFilterScreen(
    viewModel: SoundscapeViewModel,
    onUIAction: (SoundscapeUIAction) -> Unit
) {
    val screenState by viewModel.screenState.collectAsState()
    SoundscapeFilterScreenContent(screenState, onUIAction)
}



// Preview: using a dummy state
@Preview(showBackground = true)
@Composable
fun SoundscapeFilterScreenPreview() {
    // Create a dummy state for preview
    val dummyState = SoundscapeScreenState(
        selectedMood = "Enhance Sleep",
        // For preview, we use a filtered list (for example, items with even IDs)
        filteredSounds = soundScapeItemsList.filter { it.id % 2 == 0 }
    )
    BreathTheme {
        SoundscapeFilterScreenContent(dummyState) {}
    }
}
