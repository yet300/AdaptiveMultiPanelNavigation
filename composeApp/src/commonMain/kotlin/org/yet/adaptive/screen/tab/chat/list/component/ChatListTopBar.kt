package org.yet.adaptive.screen.tab.chat.list.component

import adaptivemultipanelnavigation.composeapp.generated.resources.Res
import adaptivemultipanelnavigation.composeapp.generated.resources.chats
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatListTopBar() {
    CenterAlignedTopAppBar(
        title = { Text(stringResource(Res.string.chats)) },
        actions = {
            IconButton(
                onClick = { /*TODO*/ },
                colors = IconButtonDefaults.iconButtonColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                ),
                content = {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Crate"
                    )
                }
            )
        }
    )
}