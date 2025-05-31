package com.app.main.tab

import com.app.chat.list.DefaultChatListComponent
import com.app.settings.SettingType
import com.app.settings.list.DefaultSettingsComponent
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.bringToFront
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.value.Value
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.badoo.reaktive.observable.Observable
import kotlinx.serialization.Serializable

class DefaultTabComponent(
    componentContext: ComponentContext,
    private val storeProvider: StoreFactory,
    private val selectedChatId: Observable<Long?>,
    private val selectedSetting: Observable<SettingType?>,
    private val onChatClick: (Long?) -> Unit,
    private val onOpenSettingDetails: (SettingType) -> Unit,
) : ComponentContext by componentContext, TabComponent {

    private val navigation = StackNavigation<MainConfig>()
    override val childStack: Value<ChildStack<*, TabComponent.MainChild>> =
        childStack(
            source = navigation,
            serializer = MainConfig.serializer(),
            initialConfiguration = MainConfig.Chat,
            childFactory = ::createChild,
            handleBackButton = false
        )

    private fun createChild(
        config: MainConfig,
        componentContext: ComponentContext,
    ): TabComponent.MainChild = when (config) {

        MainConfig.Chat -> TabComponent.MainChild.ChatChild(
            component = DefaultChatListComponent(
                componentContext = componentContext,
                storeProvider = storeProvider,
                selectedChatId = selectedChatId,
                onChatClicked = onChatClick,
            )
        )

        MainConfig.Settings -> TabComponent.MainChild.SettingsChild(
            DefaultSettingsComponent(
                componentContext = componentContext,
                selectedSetting = selectedSetting,
                onOpenSettingDetails = onOpenSettingDetails
            )
        )
    }

    override fun openChats() {
        navigation.bringToFront(MainConfig.Chat)
    }

    override fun openSettings() {
        navigation.bringToFront(MainConfig.Settings)
    }

    @Serializable
    private sealed interface MainConfig {
        @Serializable
        data object Chat : MainConfig

        @Serializable
        data object Settings : MainConfig
    }

}