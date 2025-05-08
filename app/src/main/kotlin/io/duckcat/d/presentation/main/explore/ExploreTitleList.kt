package io.duckcat.d.presentation.main.explore


import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import io.duckcat.d.R

@Composable
fun getExploreTiles(onUIAction: (ExploreScreenUIAction) -> Unit): List<ExploreTileData> {
    return listOf(

        ExploreTileData(
            header = "Do you know?",
            description = "\nGetting sunlight for atleast 5 minuites can keep you stay feel energetic throughout the day",
            backgroundColor = Color(0xFFFFFFFF).copy(alpha = 1f),
            progress = 0.5f,
            imageRes = R.drawable.nulltoggleoverlay,
            onClick = {  }
        ),

        ExploreTileData(
            header = "Effective Learning",
            description = "Tells you to focus on what at the moment is most important",
            imageRes = R.drawable.effective_blur,
            backgroundColor = Color(0xFF4CAF50).copy(alpha = 0f),
            progress = 0.5f,
            onClick = { onUIAction(ExploreScreenUIAction.NavigateToStudy()) }
        ),
        ExploreTileData(
            header = "Mindfulness",
            description = "Practice mindfulness to reduce stress.\n or to get energetic",
            imageRes = R.drawable.wave_blur,
            Color(0xFF4CAF50).copy(alpha = 0f),
            progress = 0.5f,
            onClick = { onUIAction(ExploreScreenUIAction.NavigateToBreathe()) }
        ),
        ExploreTileData(
            header = "Focus Assist",
            description = "Stay focused without feeling tired.",
            imageRes = R.drawable.focus_blur,
            Color(0xFF4CAF50).copy(alpha = 0f),
            progress = 0.5f,
            onClick = { onUIAction(ExploreScreenUIAction.NavigateToPomodoro()) }
        ),
        ExploreTileData(
            header = "Soundscape",
            description = "Enhance focus with ambient sounds.",
            imageRes = R.drawable.soundscape_blur,
            Color(0xFF4CAF50).copy(alpha = 0f),
            progress = 0.5f,
            onClick = { onUIAction(ExploreScreenUIAction.NavigateToSoundscapeFilter()) }
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
            header = "Fitness Suggestion",
            description = "Fitness Suggestion as the name suggests, suggest \n" +
                    "what suits to your fitness status",
            imageRes = R.drawable.fitness_blur,
            Color(0xFF4CAF50).copy(alpha = 0f),
            progress = 0.5f,
            onClick = { onUIAction(ExploreScreenUIAction.NavigateToFitness()) }
        )


    )
}
