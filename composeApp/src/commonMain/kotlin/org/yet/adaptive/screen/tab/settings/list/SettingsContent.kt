package org.yet.adaptive.screen.tab.settings.list

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Brush
import androidx.compose.material.icons.filled.Folder
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.Laptop
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.SendTimeExtension
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Storage
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.app.settings.SettingType
import com.app.settings.list.SettingsComponent
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import org.yet.adaptive.screen.tab.settings.list.component.SettingItem
import org.yet.adaptive.screen.tab.settings.list.component.SettingsTopBar

@Composable
fun SettingsContent(
    component: SettingsComponent,
    modifier: Modifier
) {
    val state by component.model.subscribeAsState()

    val items = SettingsList(component)
    Scaffold(
        topBar = {
            SettingsTopBar()
        },
        content = {
            LazyColumn(modifier = modifier.fillMaxSize().padding(it).padding(horizontal = 6.dp)) {
                items(items) {
                    val isSelected = it.type == state.selectedSetting
                    SettingItem(
                        item = it,
                        select = isSelected,
                        modifier = Modifier
                            .fillMaxWidth()
                            .selectable(
                                selected = isSelected,
                                onClick = { it.onClick() }
                            )
                    )
                }
            }
        }
    )
}

@Composable
private fun SettingsList(component: SettingsComponent): List<SettingItem> = listOf(
    SettingItem(
        type = SettingType.Profile,
        title = "My Profile",
        icon = Icons.Default.AccountCircle,
        onClick = component::onProfileClick,
        iconTint = MaterialTheme.colorScheme.background,
        iconBackgroundColor = Color.Red
    ),
    SettingItem(
        type = SettingType.General,
        title = "General",
        icon = Icons.Default.Settings,
        onClick = component::onGeneralClick,
        iconTint = MaterialTheme.colorScheme.background,
        iconBackgroundColor = Color.Gray
    ),
    SettingItem(
        type = SettingType.Notification,
        title = "Notifications and Sounds",
        icon = Icons.Default.Notifications,
        onClick = component::onNotificationClick,
        iconTint = MaterialTheme.colorScheme.background,
        iconBackgroundColor = Color.Red.copy(alpha = 0.5f)
    ),
    SettingItem(
        type = SettingType.Privacy,
        title = "Privacy and Security",
        icon = Icons.Default.Lock,
        onClick = component::onPrivacyClick,
        iconTint = MaterialTheme.colorScheme.background,
        iconBackgroundColor = Color.Blue
    ),
    SettingItem(
        type = SettingType.Storage,
        title = "Data and Storage",
        icon = Icons.Default.Storage,
        onClick = component::onDataStorageClick,
        iconTint = MaterialTheme.colorScheme.background,
        iconBackgroundColor = Color.Green
    ),
    SettingItem(
        type = SettingType.ActiveSession,
        title = "Active Session",
        icon = Icons.Default.Laptop,
        onClick = component::onActiveSessionClick,
        iconTint = MaterialTheme.colorScheme.background,
        iconBackgroundColor = Color.Cyan
    ),
    SettingItem(
        type = SettingType.Appearance,
        title = "Appearance",
        icon = Icons.Default.Brush,
        onClick = component::onAppearanceClick,
        iconTint = MaterialTheme.colorScheme.background,
        iconBackgroundColor = Color.Blue
    ),
    SettingItem(
        type = SettingType.Language,
        title = "Language",
        icon = Icons.Default.Language,
        onClick = component::onLanguageClick,
        iconTint = MaterialTheme.colorScheme.background,
        iconBackgroundColor = MaterialTheme.colorScheme.primary
    ),
    SettingItem(
        type = SettingType.Emoji,
        title = "Stickers and  Emoji",
        icon = Icons.Default.SendTimeExtension,
        onClick = component::onEmojiClick,
        iconTint = MaterialTheme.colorScheme.background,
        iconBackgroundColor = MaterialTheme.colorScheme.secondary
    ),
    SettingItem(
        type = SettingType.ChatFolder,
        title = "Chat Folders",
        icon = Icons.Default.Folder,
        onClick = component::onChatFolderClick,
        iconTint = MaterialTheme.colorScheme.background,
        iconBackgroundColor = MaterialTheme.colorScheme.primary
    ),
)

