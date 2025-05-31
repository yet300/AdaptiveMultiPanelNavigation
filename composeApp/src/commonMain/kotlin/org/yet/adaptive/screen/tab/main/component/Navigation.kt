package org.yet.adaptive.screen.tab.main.component

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Message
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import com.app.main.panel.PanelComponent
import com.app.main.tab.TabComponent
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.extensions.compose.subscribeAsState

@OptIn(ExperimentalDecomposeApi::class)
@Composable
fun Navigation(
    modifier: Modifier = Modifier,
    tabComponent: PanelComponent,
    component: TabComponent,
) {
    val stackState by component.childStack.subscribeAsState()
    val activeComponent = stackState.active.instance

    val topLevelRoutes = listOf(
        TopLevelRoute(
            onClick = tabComponent::openChats,
            selected = activeComponent is TabComponent.MainChild.ChatChild,
            icon = Icons.Default.Message,
        ),
        TopLevelRoute(
            onClick = tabComponent::openSettings,
            selected = activeComponent is TabComponent.MainChild.SettingsChild,
            icon = Icons.Default.Settings,
        ),
    )

    NavigationBar(
        modifier = modifier,
    ) {
        topLevelRoutes.forEach {
            NavigationBarItem(
                selected = it.selected,
                onClick = it.onClick,
                icon = {
                    Icon(
                        imageVector = it.icon,
                        contentDescription = null
                    )
                },
            )
        }
    }
}

data class TopLevelRoute(
    val onClick: () -> Unit,
    val selected: Boolean,
    val icon: ImageVector,
)