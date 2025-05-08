package io.duckcat.d.presentation.main.settings

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import dev.chrisbanes.haze.HazeStyle
import dev.chrisbanes.haze.hazeChild
import io.duckcat.d.R
import io.duckcat.d.core.etc.transformFraction
import io.duckcat.d.core.ui.components.navbar.NavBar
import io.duckcat.d.core.ui.components.rememberBreathRipple
import io.duckcat.d.core.ui.components.snackbar.SnackBar
import io.duckcat.d.core.ui.components.topbar.TopBarDefaults
import io.duckcat.d.core.ui.theme.BreathDefaultColors
import io.duckcat.d.core.ui.theme.BreathTheme
import io.duckcat.d.data.preferences.AppPreferences
import sv.lib.squircleshape.SquircleShape

@Composable
fun SettingsScreen(
    appPreferences: AppPreferences,
    snackbarHostState: SnackbarHostState,
    isDeleteDataDialogVisible: Boolean,
    onUIAction: (SettingsScreenUIAction) -> Unit
) {
    val scrollBehavior = TopBarDefaults.exitUntilCollapsedScrollBehavior()
    val bgAlpha by remember(scrollBehavior.state.collapsedFraction) {
        derivedStateOf { scrollBehavior.state.collapsedFraction }
    }

    var username by remember { mutableStateOf("Loading...") }

    val height by appPreferences.getFitnessHeight().collectAsState(initial = 0f)
    val weight by appPreferences.getFitnessWeight().collectAsState(initial = 0f)

    val topBarBgAlpha by remember(bgAlpha) {
        derivedStateOf {
            transformFraction(
                value = bgAlpha,
                startX = 0.8f,
                endX = 1f,
                startY = 0f,
                endY = 1f
            )
        }
    }
    val hazeState = remember { dev.chrisbanes.haze.HazeState() }

    // ✅ Fetch username safely
    LaunchedEffect(Unit) {
        val userResult = appPreferences.getUser()
        username = userResult.data ?: "No Username"
    }

    Box(modifier = Modifier.fillMaxSize()) {
        // Background Image
        Image(
            painter = painterResource(id = R.drawable.figmafakeblur),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        Scaffold(
            modifier = Modifier
                .fillMaxSize()
                .navigationBarsPadding(),
            containerColor = Color.Transparent,
            contentColor = BreathDefaultColors.background,
            bottomBar = {
                Box(
                    modifier = Modifier.hazeChild(
                        state = hazeState,
                        style = HazeStyle(
                            tint = BreathTheme.colors.background.copy(alpha = 0.2f),
                            blurRadius = 20.dp
                        )
                    )
                ) {
                    NavBar(
                        toggle1Text = "Home",
                        toggle2Text = "Explore",
                        toggle3Text = "Profile",
                        toggle1Icon = painterResource(id = R.drawable.home_fade),
                        toggle2Icon = painterResource(id = R.drawable.explore_fade),
                        toggle3Icon = painterResource(id = R.drawable.desk_active),
                        onToggle1Click = { onUIAction(SettingsScreenUIAction.NavigateToHome) },
                        onToggle2Click = { onUIAction(SettingsScreenUIAction.NavigateToExplore) },
                        onToggle3Click = { }
                    )
                }
            },
            snackbarHost = {
                SnackbarHost(
                    hostState = snackbarHostState,
                    snackbar = { SnackBar(it) }
                )
            }
        ) { insetsPadding ->
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = insetsPadding
            ) {
                // ✅ User Data Box appears first
                item {
                    Box(
                        modifier = Modifier
                            .padding(horizontal = 20.dp, vertical = 16.dp)
                            .fillMaxWidth()
                            .clip(SquircleShape(28.dp))
                            .background(BreathTheme.colors.card.copy(alpha = 0.2f))
                            .padding(25.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Column {
                            Spacer(modifier = Modifier.height(20.dp))
                            Text(
                                text = " - Profile -",
                                style = MaterialTheme.typography.titleLarge,
                                color = Color.White,
                                textAlign = TextAlign.Center

                            )

                            Spacer(modifier = Modifier.height(15.dp))

                            Text(
                                text = "    Name: $username",
                                style = MaterialTheme.typography.titleMedium,
                                color = Color.White
                            )
                            Spacer(modifier = Modifier.height(5.dp))
                            Divider(
                                color = Color.Gray, // Change the color as needed
                                thickness = 1.dp,   // Adjust the thickness of the line
                                modifier = Modifier.padding(horizontal = 16.dp) // Optional: Add padding
                            )
                            Spacer(modifier = Modifier.height(5.dp))

                            Text(
                                text = "    Height: ${height} m",
                                style = MaterialTheme.typography.titleMedium,
                                color = Color.White
                            )
                            Spacer(modifier = Modifier.height(5.dp))
                            Divider(
                                color = Color.Gray, // Change the color as needed
                                thickness = 1.dp,   // Adjust the thickness of the line
                                modifier = Modifier.padding(horizontal = 16.dp) // Optional: Add padding
                            )
                            Spacer(modifier = Modifier.height(5.dp))

                            Text(
                                text = "    Weight: ${weight} kg",
                                style = MaterialTheme.typography.titleMedium,
                                color = Color.White
                            )
                        }
                    }
                }

                // ✅ Continue with the rest of the settings
                settingsScreenMainCategory(onUIAction = onUIAction)
                settingsScreenOtherCategory(onUIAction = onUIAction)
            }
        }
    }

    if (isDeleteDataDialogVisible) {
        AlertDialog(
            onDismissRequest = { onUIAction(SettingsScreenUIAction.DismissDeleteDataDialog) },
            title = { Text("Delete All Data?") },
            text = { Text("This action cannot be undone. Are you sure you want to delete all data?") },
            confirmButton = {
                TextButton(onClick = { onUIAction(SettingsScreenUIAction.ConfirmDeleteData) }) {
                    Text("Delete", color = Color.Red)
                }
            },
            dismissButton = {
                TextButton(onClick = { onUIAction(SettingsScreenUIAction.DismissDeleteDataDialog) }) {
                    Text("Cancel")
                }
            }
        )
    }
}

@Composable
fun SettingsScreenCategoryItem(
    modifier: Modifier = Modifier,
    shape: Shape = SquircleShape(24.dp),
    icon: Painter,
    label: String,
    textColor: Color = Color.White,
    onClick: () -> Unit
) = Row(
    modifier = modifier
        .clip(shape)
        .background(BreathTheme.colors.card.copy(alpha = 0.2f))
        .clickable(
            interactionSource = remember { MutableInteractionSource() },
            indication = rememberBreathRipple(),
            onClick = onClick
        )
        .padding(16.dp),
    verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = Arrangement.spacedBy(16.dp)
) {

    Icon(
        modifier = Modifier.size(20.dp),
        painter = icon,
        contentDescription = null
    )
    Text(
        text = label,
        style = BreathTheme.typography.labelLarge,
        color = textColor
    )
}

fun LazyListScope.settingsScreenMainCategory(onUIAction: (SettingsScreenUIAction) -> Unit) {
    item(key = "main_profile_item") {
        Spacer(modifier = Modifier.height(4.dp))
        SettingsScreenCategoryItem(
            modifier = Modifier
                .padding(horizontal = 20.dp)
                .fillMaxWidth(),
            shape = SquircleShape(24.dp),
            icon = painterResource(id = R.drawable.smile),
            label = "Edit Profile",
            onClick = { onUIAction(SettingsScreenUIAction.Profile) }
        )
        Spacer(modifier = Modifier.height(10.dp))
    }

    item(key = "main_notifications_item") {
        SettingsScreenCategoryItem(
            modifier = Modifier
                .padding(horizontal = 20.dp)
                .fillMaxWidth(),
            shape = SquircleShape(24.dp),
            icon = painterResource(id = R.drawable.notification),
            label = "Notifications",
            onClick = { onUIAction(SettingsScreenUIAction.Notifications) }
        )
        Spacer(modifier = Modifier.height(10.dp))
    }

    item(key = "main_delete_data_item") {
        SettingsScreenCategoryItem(
            modifier = Modifier
                .padding(horizontal = 20.dp)
                .fillMaxWidth(),
            shape = SquircleShape(24.dp),
            icon = painterResource(id = R.drawable.delete),
            label = "Delete Data",
            textColor = Color.Red,
            onClick = { onUIAction(SettingsScreenUIAction.ShowDeleteDataDialog) }
        )
        Spacer(modifier = Modifier.height(10.dp))
    }
}

fun LazyListScope.settingsScreenOtherCategory(onUIAction: (SettingsScreenUIAction) -> Unit) {
    item(key = "other_about_item") {
        SettingsScreenCategoryItem(
            modifier = Modifier
                .padding(horizontal = 20.dp)
                .fillMaxWidth(),
            shape = SquircleShape(24.dp),
            icon = painterResource(id = R.drawable.info),
            label = "About ${stringResource(id = R.string.app_name)}",
            onClick = { onUIAction(SettingsScreenUIAction.About) }
        )
    }
}
