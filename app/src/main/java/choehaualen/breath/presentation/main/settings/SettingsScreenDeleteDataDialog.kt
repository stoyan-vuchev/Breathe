package choehaualen.breath.presentation.main.settings

import androidx.compose.foundation.layout.size
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.compositeOver
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import choehaualen.breath.R
import choehaualen.breath.core.ui.components.button.UniqueButton
import choehaualen.breath.core.ui.theme.BreathTheme
import sv.lib.squircleshape.SquircleShape

@Composable
fun SettingsScreenDeleteDataDialog(
    modifier: Modifier = Modifier,
    isVisible: Boolean,
    onUIAction: (SettingsScreenUIAction) -> Unit
) = if (isVisible) {

    AlertDialog(
        modifier = modifier,
        properties = DialogProperties(
            decorFitsSystemWindows = false
        ),
        icon = {
            Icon(
                modifier = Modifier.size(20.dp),
                painter = painterResource(id = R.drawable.delete),
                contentDescription = null
            )
        },
        title = { Text(text = "Delete Data") },
        text = {

            Text(
                text = "Are you sure you want to delete all the data?\nThis action cannot be undone!",
                textAlign = TextAlign.Center
            )

        },
        onDismissRequest = { onUIAction(SettingsScreenUIAction.DismissDeleteDataDialog) },
        containerColor = BreathTheme.colors.card,
        textContentColor = BreathTheme.colors.text,
        titleContentColor = BreathTheme.colors.text,
        iconContentColor = Color.Red,
        dismissButton = { 
            
            UniqueButton(
                onClick = { onUIAction(SettingsScreenUIAction.DismissDeleteDataDialog) }
            ) { Text(text = "Cancel") }
            
        },
        confirmButton = {

            UniqueButton(
                onClick = { onUIAction(SettingsScreenUIAction.ConfirmDeleteData) },
                backgroundColor = BreathTheme.colors.card.copy(.33f)
                    .compositeOver(Color.Red),
                contentColor = Color.Black
            ) { Text(text = "Delete Data") }

        },
        shape = SquircleShape(48.dp)
    )

} else Unit

// Lastly I've implemented a delete all data dialog.
// But haven't pushed the code cuz it's not yet deleting anything.
// yes cus e still have to do data for habit control and others like sleep, yes.
// So, should we start implementing the habbits, etc. ?, yes, i will share the needed files for
//sound scape,i renamed some of em and got images for thumbnail, awesome!