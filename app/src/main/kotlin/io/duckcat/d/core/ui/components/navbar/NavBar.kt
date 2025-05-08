package io.duckcat.d.core.ui.components.navbar

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun NavBar(
    modifier: Modifier = Modifier,
    toggle1Text: String,
    toggle2Text: String,
    toggle3Text: String,
    toggle1Icon: Painter? = null,
    toggle2Icon: Painter? = null,
    toggle3Icon: Painter? = null,
    onToggle1Click: () -> Unit,
    onToggle2Click: () -> Unit,
    onToggle3Click: () -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .navigationBarsPadding() // inset for system nav bars
            .background(Color.White.copy(alpha = 0f))
            .padding(vertical = 24.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            NavBarItem(icon = toggle1Icon, text = toggle1Text, onClick = onToggle1Click)
            NavBarItem(icon = toggle2Icon, text = toggle2Text, onClick = onToggle2Click)
            NavBarItem(icon = toggle3Icon, text = toggle3Text, onClick = onToggle3Click)
        }
    }
}

@Composable
fun NavBarItem(
    icon: Painter? = null,
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.clickable { onClick() },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        if (icon != null) {
            Image(
                painter = icon,
                contentDescription = null,
                modifier = Modifier.size(24.dp)
            )
        }
        Text(text = text)
    }
}

@Preview(showBackground = true)
@Composable
fun NavBarPreview() {
    NavBar(
        toggle1Text = "Home",
        toggle2Text = "Search",
        toggle3Text = "Profile",
        toggle1Icon = null,
        toggle2Icon = null,
        toggle3Icon = null,
        onToggle1Click = {},
        onToggle2Click = {},
        onToggle3Click = {}
    )
}
