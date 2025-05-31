package com.app.root

import com.app.main.panel.PreviewPanelComponent
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.common.decompose.PreviewComponentContext

class PreviewRootComponent :
    RootComponent,
    ComponentContext by PreviewComponentContext {
    override val childStack: Value<ChildStack<*, RootComponent.Child>> =
        MutableValue(
            ChildStack(
                configuration = Unit,
                instance = RootComponent.Child.Tab(PreviewPanelComponent()),
            )
        )

    override fun onBackClicked() {
        TODO("Not yet implemented")
    }
}
