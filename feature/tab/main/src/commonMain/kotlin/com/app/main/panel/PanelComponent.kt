package com.app.main.panel

import com.app.chat.detail.ChatDetailComponent
import com.app.main.tab.TabComponent
import com.app.settings.detail.SettingsDetailsComponent
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.router.panels.ChildPanels
import com.arkivanov.decompose.router.panels.ChildPanelsMode
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.backhandler.BackHandlerOwner

@OptIn(ExperimentalDecomposeApi::class)
interface PanelComponent : BackHandlerOwner {

    val panels: Value<ChildPanels<*, MainTab, *, DetailPanel, Nothing, Nothing>>

    val mode: Value<ChildPanelsMode>
    fun setMode(mode: ChildPanelsMode)

    fun onBackClicked()

    class MainTab(val component: TabComponent)

    fun openChats()
    fun openSettings()

    sealed interface DetailPanel {
        class Message(val component: ChatDetailComponent) : DetailPanel
        class SettingsDetails(val component: SettingsDetailsComponent) : DetailPanel
    }
}