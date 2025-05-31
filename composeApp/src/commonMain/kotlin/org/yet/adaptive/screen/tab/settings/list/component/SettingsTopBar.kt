package org.yet.adaptive.screen.tab.settings.list.component

import adaptivemultipanelnavigation.composeapp.generated.resources.Res
import adaptivemultipanelnavigation.composeapp.generated.resources.settings
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsTopBar() {
    CenterAlignedTopAppBar(
        title = { Text(stringResource(Res.string.settings)) },
    )
}