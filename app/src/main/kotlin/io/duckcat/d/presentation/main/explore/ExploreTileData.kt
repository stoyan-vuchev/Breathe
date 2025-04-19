package io.duckcat.d.presentation.main.explore

data class ExploreTileData(
    val header: String,
    val description: String,
    val imageRes: Int,
    val backgroundColor: androidx.compose.ui.graphics.Color,
    val progress: Float,
    val onClick: () -> Unit // ✅ Added onTileClick parameter
)


