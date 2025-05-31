package org.yet.adaptive.screen.root

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.app.root.RootComponent
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.stack.animation.predictiveback.predictiveBackAnimation
import org.yet.adaptive.screen.tab.main.TabContent

@OptIn(ExperimentalDecomposeApi::class)
@Composable
fun RootContent(
    modifier: Modifier = Modifier,
    component: RootComponent
) {
    Children(
        stack = component.childStack,
        modifier = modifier,
        animation = predictiveBackAnimation(
            backHandler = component.backHandler,
            onBack = component::onBackClicked
        ),
    ) { child ->
        when (val instance = child.instance) {
            is RootComponent.Child.Tab -> TabContent(component = instance.component)
        }
    }
}