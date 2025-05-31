package com.app.root

import com.app.main.panel.PanelComponent
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.backhandler.BackHandlerOwner

interface RootComponent : BackHandlerOwner {

    val childStack: Value<ChildStack<*, Child>>

    fun onBackClicked()

    sealed class Child {
        data class Tab(val component: PanelComponent) : Child()
    }
}