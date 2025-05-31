package com.app.main.tab

import com.app.chat.list.ChatListComponent
import com.app.settings.list.SettingsComponent
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value

interface TabComponent {
    val childStack: Value<ChildStack<*, MainChild>>

    fun openChats()
    fun openSettings()

    sealed class MainChild {
        class ChatChild(val component: ChatListComponent) : MainChild()
        class SettingsChild(val component: SettingsComponent) : MainChild()
    }
}