package org.yet.adaptive

import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import com.app.root.DefaultRootComponent
import java.awt.Dimension
import javax.swing.SwingUtilities
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.essenty.lifecycle.LifecycleRegistry

fun main() = application {
    val lifecycle = LifecycleRegistry()

    val root =
        runOnUiThread {
            DefaultRootComponent(
                componentContext = DefaultComponentContext(lifecycle = lifecycle),
            )
        }

    val windowState = rememberWindowState(
        size = DpSize(800.dp, 600.dp)
    )

    Window(
        state = windowState,
        onCloseRequest = ::exitApplication,
        title = "AdaptiveMultiPanelNavigation",
    ) {
        window.minimumSize = Dimension(400.dp.value.toInt(), 600.dp.value.toInt())

        App(root)
    }
}


private fun <T> runOnUiThread(block: () -> T): T {
    if (SwingUtilities.isEventDispatchThread()) {
        return block()
    }

    var error: Throwable? = null
    var result: T? = null

    SwingUtilities.invokeAndWait {
        try {
            result = block()
        } catch (e: Throwable) {
            error = e
        }
    }

    error?.also { throw it }

    @Suppress("UNCHECKED_CAST")
    return result as T
}