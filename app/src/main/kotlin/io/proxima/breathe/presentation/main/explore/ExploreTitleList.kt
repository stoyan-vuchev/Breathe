package io.proxima.breathe.presentation.main.explore


import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import io.proxima.breathe.R

@Composable
fun getExploreTiles(onUIAction: (ExploreScreenUIAction) -> Unit): List<ExploreTileData> {
    return listOf(

        ExploreTileData(
            header = "Do you know?",
            description = "\nGetting sunlight for atleast 5 minuites can keep you stay feel energetic throughout the day",
            backgroundColor = Color(0xFFFFFFFF).copy(alpha = 0.6f),
            progress = 0.5f,
            imageRes = R.drawable.nulltoggleoverlay,
            onClick = {  }
        ),

        ExploreTileData(
            header = "Effective Learning",
            description = "null?",
            imageRes = R.drawable.effective_blur,
            backgroundColor = Color(0xFF4CAF50).copy(alpha = 0f),
            progress = 0.5f,
            onClick = {  }
        ),
        ExploreTileData(
            header = "Mindfulness",
            description = "Practice mindfulness to reduce stress.",
            imageRes = R.drawable.wave_blur,
            Color(0xFF4CAF50).copy(alpha = 0f),
            progress = 0.5f,
            onClick = { onUIAction(ExploreScreenUIAction.NavigateToBreathe()) }
        ),
        ExploreTileData(
            header = "Productivity Boost",
            description = "Learn strategies to work smarter, not harder.",
            imageRes = R.drawable.focus_blur,
            Color(0xFF4CAF50).copy(alpha = 0f),
            progress = 0.5f,
            onClick = { onUIAction(ExploreScreenUIAction.NavigateToPomodoro()) }
        ),
        ExploreTileData(
            header = "Soundscape",
            description = "Enhance focus with relaxing sounds.",
            imageRes = R.drawable.soundscape_blur,
            Color(0xFF4CAF50).copy(alpha = 0f),
            progress = 0.5f,
            onClick = { onUIAction(ExploreScreenUIAction.NavigateToSoundscape()) }
        ),
        ExploreTileData(
            header = "Productivity Reminders",
            description = "Small but certain actions to keep your day better",
            imageRes = R.drawable.productivity_box,
            Color(0xFF4CAF50).copy(alpha = 0f),
            progress = 0.5f,
            onClick = { onUIAction(ExploreScreenUIAction.NavigateToProductivity()) }
        ),

        ExploreTileData(
            header = "Fitness Assist",
            description = "Fitness assist make u less fat\n" +
                    "and more fat according to your fat condition",
            imageRes = R.drawable.fitness_blur,
            Color(0xFF4CAF50).copy(alpha = 0f),
            progress = 0.5f,
            onClick = { onUIAction(ExploreScreenUIAction.NavigateToFitness()) }
        )


    )
}
