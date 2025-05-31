package org.yet.adaptive.screen.tab.settings.detail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.app.settings.detail.SettingsDetailsComponent
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import org.yet.adaptive.screen.tab.settings.detail.component.DetailSettingsAppBar

@Composable
fun SettingsDetailContent(
    component: SettingsDetailsComponent,
    modifier: Modifier
) {
    val state by component.model.subscribeAsState()
    Scaffold(
        topBar = { DetailSettingsAppBar(component) },
        content = { paddingValues ->
            Box(
                modifier = modifier.padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                Text(state.settingType.name)
            }
        }
    )
}