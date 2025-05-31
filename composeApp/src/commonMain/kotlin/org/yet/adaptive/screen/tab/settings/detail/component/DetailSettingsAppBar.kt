package org.yet.adaptive.screen.tab.settings.detail.component

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import com.app.settings.detail.SettingsDetailsComponent


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailSettingsAppBar(
    component: SettingsDetailsComponent,
) {
    TopAppBar(
        title = { },
        navigationIcon = {
            IconButton(
                onClick = component::onBackClick,
                colors = IconButtonDefaults.iconButtonColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                ),
                content = {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back"
                    )
                }
            )
        }
    )
}