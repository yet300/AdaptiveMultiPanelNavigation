package org.yet.adaptive.screen.tab.chat.detail.component

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import com.app.chat.detail.ChatDetailComponent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MessageAppBar(
    component: ChatDetailComponent,
    isVisible: Boolean
) {
    TopAppBar(
        title = { },
        navigationIcon = {
            if (isVisible){
                IconButton(
                    onClick = component::onBack,
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
        }
    )
}