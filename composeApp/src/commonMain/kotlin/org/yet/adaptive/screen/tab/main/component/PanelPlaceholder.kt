package org.yet.adaptive.screen.tab.main.component

import adaptivemultipanelnavigation.composeapp.generated.resources.Res
import adaptivemultipanelnavigation.composeapp.generated.resources.select_detail
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.stringResource

@Composable
fun PanelPlaceholder() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {

        Box(
            modifier = Modifier.background(
                MaterialTheme.colorScheme.primary.copy(alpha = 0.5f),
                shape = MaterialTheme.shapes.small
            )
        ) {
            Text(
                text = stringResource(Res.string.select_detail),
                color = MaterialTheme.colorScheme.background,
                modifier = Modifier.padding(6.dp)
            )
        }
    }
}