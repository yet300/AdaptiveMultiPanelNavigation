package org.yet.adaptive

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.app.root.DefaultRootComponent
import com.app.root.PreviewRootComponent
import com.arkivanov.decompose.defaultComponentContext

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        val root = DefaultRootComponent(
            componentContext = defaultComponentContext()
        )

        setContent {
            App(root)
        }
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    App(component = PreviewRootComponent())
}