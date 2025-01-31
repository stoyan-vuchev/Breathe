package io.proxima.breathe.core.ui.components.snackbar

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarData
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp
import io.proxima.breathe.core.ui.theme.BreathTheme
import sv.lib.squircleshape.SquircleShape

@Composable
fun SnackBar(
    snackbarData: SnackbarData,
    modifier: Modifier = Modifier,
    shape: Shape = SquircleShape()
) = Snackbar(
    snackbarData = snackbarData,
    modifier = Modifier.snackBarModifier(modifier),
    shape = shape,
    containerColor = BreathTheme.colors.text,
    contentColor = BreathTheme.colors.background
)

private fun Modifier.snackBarModifier(
    otherModifier: Modifier
): Modifier {
    return this
        .padding(24.dp)
        .then(otherModifier)
}