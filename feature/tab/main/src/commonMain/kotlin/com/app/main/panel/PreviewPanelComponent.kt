package com.app.main.panel

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.router.panels.ChildPanels
import com.arkivanov.decompose.router.panels.ChildPanelsMode
import com.arkivanov.decompose.value.Value
import com.common.decompose.PreviewComponentContext

@OptIn(ExperimentalDecomposeApi::class)
class PreviewPanelComponent :
    PanelComponent,
    ComponentContext by PreviewComponentContext {
    override val panels: Value<ChildPanels<*, PanelComponent.MainTab, *, PanelComponent.DetailPanel, Nothing, Nothing>>
        get() = TODO("Not yet implemented")
    override val mode: Value<ChildPanelsMode>
        get() = TODO("Not yet implemented")

    override fun setMode(mode: ChildPanelsMode) {
        TODO("Not yet implemented")
    }

    override fun onBackClicked() {
        TODO("Not yet implemented")
    }


    override fun openChats() {
        TODO("Not yet implemented")
    }


    override fun openSettings() {
        TODO("Not yet implemented")
    }

}