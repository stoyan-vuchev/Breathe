package io.proxima.breathe.presentation.main.settings.profile

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.KeyboardArrowLeft
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import io.proxima.breathe.R
import io.proxima.breathe.core.etc.transformFraction
import io.proxima.breathe.core.ui.components.button.UniqueButton
import io.proxima.breathe.core.ui.components.topbar.TopBarDefaults
import io.proxima.breathe.core.ui.components.topbar.basic_topbar.BasicTopBar
import io.proxima.breathe.core.ui.theme.BreathTheme
import sv.lib.squircleshape.SquircleShape

@Composable
fun SettingsProfileScreen(
    screenState: SettingsProfileScreenState,
    focusRequester: FocusRequester,
    onUIAction: (SettingsProfileScreenUIAction) -> Unit
) {

    val scrollBehavior = TopBarDefaults.exitUntilCollapsedScrollBehavior()

    val bgAlpha by remember(scrollBehavior.state.collapsedFraction) {
        derivedStateOf { scrollBehavior.state.collapsedFraction }
    }

    val topBarBgAlpha by remember(bgAlpha) { // this ensures smooth color transition
        derivedStateOf {
            transformFraction(
                value = bgAlpha,
                startX = .8f,
                endX = 1f,
                startY = 0f,
                endY = 1f
            )
        }
    }

    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        containerColor = BreathTheme.colors.background.copy(bgAlpha),
        contentColor = BreathTheme.colors.text,
        topBar = {

            BasicTopBar(
                titleText = "Profile",
                scrollBehavior = scrollBehavior,
                navigationIcon = {

                    IconButton(
                        onClick = { onUIAction(SettingsProfileScreenUIAction.NavigateUp) }
                    ) {

                        Icon(
                            modifier = Modifier.size(32.dp),
                            imageVector = Icons.AutoMirrored.Rounded.KeyboardArrowLeft,
                            contentDescription = "Navigate up to Settings."
                        )

                    }

                },
                backgroundColor = BreathTheme.colors.background.copy(topBarBgAlpha)
            )

        }
    ) { insetsPadding ->

        Box(
            modifier = Modifier.fillMaxSize()
        ) {

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = insetsPadding,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                item(
                    key = "item_top_spacer",
                    content = { Spacer(modifier = Modifier.height(150.dp)) }
                )

                item(
                    key = "item_username_text",
                    content = {

                        AnimatedContent(
                            modifier = Modifier.fillMaxWidth(),
                            targetState = screenState.isInEditMode,
                            label = ""
                        ) { isInEditMode ->

                            Column(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {

                                if (!isInEditMode) {

                                    Icon(
                                        modifier = Modifier.size(56.dp),
                                        painter = painterResource(id = R.drawable.amuzing_face),
                                        contentDescription = null,
                                        tint = BreathTheme.colors.text
                                    )

                                    Spacer(modifier = Modifier.height(32.dp))

                                    Text(
                                        text = screenState.username,
                                        style = BreathTheme.typography.titleLarge,
                                        color = BreathTheme.colors.text
                                    )

                                } else {

                                    Text(
                                        text = "Edit Username",
                                        style = BreathTheme.typography.titleLarge,
                                        color = BreathTheme.colors.text
                                    )

                                }

                            }

                        }

                    }
                )

                item(
                    key = "item_text_field",
                    content = {

                        AnimatedVisibility(
                            visible = screenState.isInEditMode,
                            enter = fadeIn() + scaleIn() + slideInVertically { -it },
                            exit = fadeOut() + scaleOut() + slideOutVertically { -it },
                            label = ""
                        ) {

                            Column(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {

                                Spacer(modifier = Modifier.height(64.dp))

                                TextField(
                                    modifier = Modifier
                                        .focusRequester(focusRequester)
                                        .padding(horizontal = 32.dp)
                                        .clip(SquircleShape())
                                        .background(BreathTheme.colors.card)
                                        .padding(horizontal = 6.dp)
                                        .animateContentSize(),
                                    value = screenState.usernameTextFieldText,
                                    onValueChange = { newText ->
                                        onUIAction(
                                            SettingsProfileScreenUIAction.SetUsernameTextFieldText(
                                                newText = newText
                                            )
                                        )
                                    },
                                    colors = TextFieldDefaults.colors(
                                        focusedTextColor = BreathTheme.colors.text,
                                        unfocusedTextColor = BreathTheme.colors.text,
                                        focusedContainerColor = Color.Transparent,
                                        unfocusedContainerColor = Color.Transparent,
                                        focusedIndicatorColor = Color.Transparent,
                                        unfocusedIndicatorColor = Color.Transparent,
                                        cursorColor = BreathTheme.colors.text,
                                        errorIndicatorColor = Color.Red,
                                        errorContainerColor = Color.Transparent,
                                    ),
                                    placeholder = {

                                        Text(
                                            text = "e.g. Something Unique",
                                            style = BreathTheme.typography.bodyLarge
                                        )

                                    },
                                    textStyle = BreathTheme.typography.bodyLarge,
                                    keyboardOptions = KeyboardOptions.Default.copy(
                                        imeAction = ImeAction.Done,
                                        keyboardType = KeyboardType.Text
                                    ),
                                    keyboardActions = KeyboardActions(
                                        onAny = {
                                            focusManager.clearFocus()
                                            keyboardController?.hide()
                                        }
                                    )
                                )

                            }

                        }

                    }
                )

            }

            Box(
                modifier = Modifier
                    .systemBarsPadding()
                    .padding(bottom = 48.dp)
                    .align(Alignment.BottomCenter)
            ) {

                UniqueButton(
                    onClick = {
                        onUIAction(
                            if (screenState.isInEditMode) SettingsProfileScreenUIAction.Save
                            else SettingsProfileScreenUIAction.Edit
                        )
                    }
                ) {

                    Text(
                        modifier = Modifier.padding(horizontal = 24.dp),
                        text = if (screenState.isInEditMode) "Save" else "Edit"
                    )

                }

            }

        }

    }

}