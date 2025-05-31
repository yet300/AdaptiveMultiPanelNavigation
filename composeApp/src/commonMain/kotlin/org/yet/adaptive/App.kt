package org.yet.adaptive

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import com.app.root.RootComponent
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.yet.adaptive.screen.root.RootContent

@Composable
@Preview
fun App(component: RootComponent) {
    MaterialTheme {
        RootContent(component = component)
    }
}