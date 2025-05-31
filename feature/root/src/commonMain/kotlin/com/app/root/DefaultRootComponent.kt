package com.app.root

import com.app.main.panel.DefaultPanelComponent
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.value.Value
import kotlinx.serialization.Serializable

class DefaultRootComponent(
    componentContext: ComponentContext,
) : ComponentContext by componentContext, RootComponent {

    private val navigation = StackNavigation<Configuration>()

    private val stack = childStack(
        source = navigation,
        serializer = Configuration.serializer(),
        initialConfiguration = Configuration.TabScreen,
        childFactory = ::createChild,
    )


    override val childStack: Value<ChildStack<*, RootComponent.Child>>
        get() = stack

    override fun onBackClicked() {
        navigation.pop()
    }

    private fun createChild(
        config: Configuration,
        componentContext: ComponentContext,
    ): RootComponent.Child = when (config) {

        Configuration.TabScreen -> RootComponent.Child.Tab(
            DefaultPanelComponent(
                componentContext = componentContext,
            )
        )

    }

    @Serializable
    sealed class Configuration {

        @Serializable
        data object TabScreen : Configuration()

    }

}