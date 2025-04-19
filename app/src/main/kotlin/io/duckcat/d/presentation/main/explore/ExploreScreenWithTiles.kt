    package io.duckcat.d.presentation.main.explore

    import androidx.compose.foundation.layout.Arrangement
    import androidx.compose.foundation.layout.PaddingValues
    import androidx.compose.foundation.lazy.LazyColumn
    import androidx.compose.foundation.lazy.items
    import androidx.compose.runtime.Composable
    import androidx.compose.ui.Modifier
    import androidx.compose.ui.unit.dp

    @Composable
    fun ExploreScreenWithTiles(
        tiles: List<ExploreTileData>,
        modifier: Modifier = Modifier,
        onUIAction: (ExploreScreenUIAction) -> Unit
    ) {
        LazyColumn(
            modifier = modifier,
            contentPadding = PaddingValues(20.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            items(tiles) { tile ->
                ExploreVerticalTiles(
                    header = tile.header,
                    description = tile.description,
                    imageRes = tile.imageRes,
                    progress = tile.progress,
                    maxBlur = 20.dp,
                    onClick = tile.onClick

                )
            }
        }
    }
