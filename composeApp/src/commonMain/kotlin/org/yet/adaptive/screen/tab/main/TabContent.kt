package org.yet.adaptive.screen.tab.main

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.app.main.panel.PanelComponent
import com.app.main.tab.TabComponent
import com.arkivanov.decompose.Child
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.extensions.compose.experimental.panels.ChildPanels
import com.arkivanov.decompose.extensions.compose.experimental.panels.HorizontalChildPanelsLayout
import com.arkivanov.decompose.extensions.compose.experimental.stack.animation.PredictiveBackParams
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.stack.animation.predictiveback.materialPredictiveBackAnimatable
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.arkivanov.decompose.router.panels.ChildPanelsMode
import org.yet.adaptive.screen.tab.chat.detail.MessageDetailContent
import org.yet.adaptive.screen.tab.chat.list.ChatListContent
import org.yet.adaptive.screen.tab.main.component.Navigation
import org.yet.adaptive.screen.tab.main.component.PanelPlaceholder
import org.yet.adaptive.screen.tab.settings.detail.SettingsDetailContent
import org.yet.adaptive.screen.tab.settings.list.SettingsContent

@OptIn(ExperimentalDecomposeApi::class)
@Composable
fun TabContent(component: PanelComponent) {

    val panels by component.panels.subscribeAsState()
    BoxWithConstraints(
        modifier = Modifier.fillMaxSize()
    ) {
        ChildPanels(
            panels = panels,
            mainChild = { mainCreatedChild ->
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    TabContent(mainCreatedChild, component)
                }
            },
            detailsChild = { detailsCreatedChild ->
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    PanelDetailChildren(
                        component = detailsCreatedChild.instance,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            },
            secondPanelPlaceholder = {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    PanelPlaceholder()
                }
            },
            layout = HorizontalChildPanelsLayout(
                dualWeights = Pair(first = 0.4F, second = 0.6F),
            ),
            predictiveBackParams = {
                PredictiveBackParams(
                    backHandler = component.backHandler,
                    onBack = component::onBackClicked,
                    animatable = ::materialPredictiveBackAnimatable,
                )
            },
        )

        val mode = when {
            maxWidth >= 800.dp -> ChildPanelsMode.DUAL
            else -> ChildPanelsMode.SINGLE
        }

        DisposableEffect(mode) {
            component.setMode(mode)
            onDispose {}
        }
    }
}


@Composable
private fun TabContent(
    mainCreatedChild: Child.Created<Any, PanelComponent.MainTab>,
    component: PanelComponent
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        TabChildren(
            modifier = Modifier.weight(1F)
                .consumeWindowInsets(WindowInsets.navigationBars),
            component = mainCreatedChild.instance.component
        )
        Navigation(
            tabComponent = component,
            component = mainCreatedChild.instance.component
        )
    }
}

@OptIn(ExperimentalDecomposeApi::class)
@Composable
private fun TabChildren(
    modifier: Modifier = Modifier,
    component: TabComponent,
) {
    Children(
        stack = component.childStack,
        modifier = modifier,
    ) { child ->
        when (val child = child.instance) {

            is TabComponent.MainChild.ChatChild -> ChatListContent(
                component = child.component,
                modifier = modifier.fillMaxSize()
            )

            is TabComponent.MainChild.SettingsChild -> SettingsContent(
                component = child.component,
                modifier = modifier.fillMaxSize()
            )

        }
    }
}

@Composable
private fun PanelDetailChildren(
    modifier: Modifier = Modifier,
    component: PanelComponent.DetailPanel,
) {
    when (val child = component) {
        is PanelComponent.DetailPanel.Message ->MessageDetailContent(
            component = child.component,
            modifier = modifier.fillMaxSize()
        )

        is PanelComponent.DetailPanel.SettingsDetails -> SettingsDetailContent(
            component = child.component,
            modifier = modifier.fillMaxSize()
        )
    }
}