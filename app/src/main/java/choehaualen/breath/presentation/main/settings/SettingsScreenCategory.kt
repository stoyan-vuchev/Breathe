package choehaualen.breath.presentation.main.settings

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import choehaualen.breath.R
import sv.lib.squircleshape.SquircleShape

fun LazyListScope.settingsScreenMainCategory(
    onUIAction: (SettingsScreenUIAction) -> Unit
) {

    item(key = "main_profile_item") {

        Spacer(modifier = Modifier.height(16.dp))

        SettingsScreenCategoryItem(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth(),
            shape = SquircleShape(
                topStart = 24.dp,
                topEnd = 24.dp,
                bottomEnd = 4.dp,
                bottomStart = 4.dp
            ),
            icon = painterResource(id = R.drawable.smile),
            label = "Profile",
            onClick = { onUIAction(SettingsScreenUIAction.Profile) }
        )

        Spacer(modifier = Modifier.height(1.dp))

    }

    item(key = "main_notifications_item") {

        SettingsScreenCategoryItem(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth(),
            shape = SquircleShape(4.dp),
            icon = painterResource(id = R.drawable.notification),
            label = "Notifications",
            onClick = { onUIAction(SettingsScreenUIAction.Notifications) }
        )

        Spacer(modifier = Modifier.height(1.dp))

    }

    item(key = "main_themes_item") {

        SettingsScreenCategoryItem(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth(),
            shape = SquircleShape(4.dp),
            icon = painterResource(id = R.drawable.theme),
            label = "Themes",
            onClick = { onUIAction(SettingsScreenUIAction.Themes) }
        )

        Spacer(modifier = Modifier.height(1.dp))

    }

    item(key = "main_delete_data_item") {

        SettingsScreenCategoryItem(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth(),
            shape = SquircleShape(
                topStart = 4.dp,
                topEnd = 4.dp,
                bottomEnd = 24.dp,
                bottomStart = 24.dp
            ),
            icon = painterResource(id = R.drawable.delete),
            label = "Delete Data",
            textColor = Color.Red,
            onClick = { onUIAction(SettingsScreenUIAction.ShowDeleteDataDialog) }
        )

        Spacer(modifier = Modifier.height(16.dp))

    }

}

fun LazyListScope.settingsScreenOtherCategory(
    onUIAction: (SettingsScreenUIAction) -> Unit
) {

    item(key = "other_about_item") {

        SettingsScreenCategoryItem(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth(),
            shape = SquircleShape(24.dp),
            icon = painterResource(id = R.drawable.info),
            label = "About ${stringResource(id = R.string.app_name)}",
            onClick = { onUIAction(SettingsScreenUIAction.About) }
        )

    }

}