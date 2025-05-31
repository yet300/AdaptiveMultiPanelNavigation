package com.app.main.panel


import com.app.main.tab.DefaultTabComponent
import com.app.main.tab.TabComponent
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.router.panels.ChildPanels
import com.arkivanov.decompose.router.panels.ChildPanelsMode
import com.arkivanov.decompose.router.panels.Panels
import com.arkivanov.decompose.router.panels.PanelsNavigation
import com.arkivanov.decompose.router.panels.childPanels
import com.arkivanov.decompose.router.panels.isSingle
import com.arkivanov.decompose.router.panels.navigate
import com.arkivanov.decompose.router.panels.pop
import com.arkivanov.decompose.router.panels.setMode
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.badoo.reaktive.observable.map
import com.badoo.reaktive.observable.notNull
import com.badoo.reaktive.subject.behavior.BehaviorSubject
import com.common.provideStoreFactory
import com.app.chat.detail.DefaultChatDetailComponent
import com.app.settings.SettingType
import com.app.settings.detail.DefaultSettingsDetailsComponent
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable


@OptIn(ExperimentalDecomposeApi::class, ExperimentalSerializationApi::class)
class DefaultPanelComponent(
    componentContext: ComponentContext,
) : ComponentContext by componentContext, PanelComponent {
    private val storeFactory = provideStoreFactory()

    private val panelsNavigation = PanelsNavigation<MainPanelConfig, DetailPanelConfig, Nothing>()

    private val _navState =
        BehaviorSubject<Panels<MainPanelConfig, DetailPanelConfig, Nothing>?>(null)
    private val navState = _navState.notNull()

    private val _mode = MutableValue(ChildPanelsMode.SINGLE)
    override val mode: Value<ChildPanelsMode> = _mode

    private var lastSelectedMessageDetailConfig: DetailPanelConfig.Message? =
        stateKeeper.consume(
            key = "lastSelectedMessageConfig",
            strategy = DetailPanelConfig.Message.serializer()
        )

    private var lastSelectedSettingDetailConfig: DetailPanelConfig.Setting? =
        stateKeeper.consume(
            key = "lastSelectedSettingsTypeConfig",
            strategy = DetailPanelConfig.Setting.serializer()
        )

    init {
        stateKeeper.register(
            key = "lastSelectedMessageDetailConfig",
            strategy = DetailPanelConfig.Message.serializer()
        ) { lastSelectedMessageDetailConfig }

        stateKeeper.register(
            key = "lastSelectedSettingDetailConfig",
            strategy = DetailPanelConfig.Setting.serializer()
        ) { lastSelectedSettingDetailConfig }

    }


    private val tabComponent: TabComponent = DefaultTabComponent(
        componentContext = this,
        storeProvider = storeFactory,
        selectedChatId = navState.map { state ->
            (state.details as? DetailPanelConfig.Message)?.messageId
        },
        selectedSetting = navState.map { state ->
            (state.details as? DetailPanelConfig.Setting)?.settingType
        },
        onChatClick = ::selectChat,
        onOpenSettingDetails = ::selectSetting
    )

    override val panels: Value<ChildPanels<*, PanelComponent.MainTab, *, PanelComponent.DetailPanel, Nothing, Nothing>> =
        childPanels(
            source = panelsNavigation,
            serializers = MainPanelConfig.serializer() to DetailPanelConfig.serializer(),
            onStateChanged = { newState, oldState ->
                _navState.onNext(newState)
                updateSelection(newState.details)
            },
            initialPanels = { Panels(main = MainPanelConfig) },
            handleBackButton = true,
            mainFactory = { config, ctx ->
                PanelComponent.MainTab(tabComponent)
            },
            detailsFactory = { config, ctx ->
                when (config) {
                    is DetailPanelConfig.Message -> PanelComponent.DetailPanel.Message(
                        component = DefaultChatDetailComponent(
                            componentContext = ctx,
                            provideStoreFactory = storeFactory,
                            chatId = config.messageId,
                            isBackButtonVisible = navState.map { it.mode.isSingle },
                            onBacked = { handleBack(config) }
                        )
                    )

                    is DetailPanelConfig.Setting -> PanelComponent.DetailPanel.SettingsDetails(
                        component = DefaultSettingsDetailsComponent(
                            componentContext = ctx,
                            settingType = config.settingType,
                            onBacked = { handleBack(config) }
                        )
                    )
                }
            }
        )

    override fun setMode(mode: ChildPanelsMode) {
        _mode.value = mode
        panelsNavigation.setMode(mode)
    }

    override fun onBackClicked() {
        panelsNavigation.pop()
    }

    override fun openChats() {
        tabComponent.openChats()
        navigateToTab(lastSelectedMessageDetailConfig)
    }

    override fun openSettings() {
        tabComponent.openSettings()
        navigateToTab(lastSelectedSettingDetailConfig)
    }

    private fun selectChat(chatId: Long?) {
        val newConfig = DetailPanelConfig.Message(messageId = chatId)
        lastSelectedMessageDetailConfig = newConfig
        navigateTo(newConfig)
    }

    private fun selectSetting(settingType: SettingType) {
        val newConfig = DetailPanelConfig.Setting(settingType = settingType)
        lastSelectedSettingDetailConfig = newConfig
        navigateTo(newConfig)
    }

    private fun navigateTo(config: DetailPanelConfig) {
        panelsNavigation.navigate { currentPanels ->
            currentPanels.copy(details = config)
        }
    }

    private fun navigateToTab(config: DetailPanelConfig?) {
        panelsNavigation.navigate { currentPanels ->
            currentPanels.copy(
                main = MainPanelConfig,
                details = if (currentPanels.mode.isSingle) null else config
            )
        }
    }

    private fun updateSelection(detail: DetailPanelConfig?) {
        when (detail) {
            is DetailPanelConfig.Message -> lastSelectedMessageDetailConfig = detail
            is DetailPanelConfig.Setting -> lastSelectedSettingDetailConfig = detail
            null -> {}
        }
    }

    private fun handleBack(config: DetailPanelConfig) {
        panelsNavigation.pop()
        when (config) {
            is DetailPanelConfig.Message -> lastSelectedMessageDetailConfig = null
            is DetailPanelConfig.Setting -> lastSelectedSettingDetailConfig = null
        }
    }

    @Serializable
    private data object MainPanelConfig

    @Serializable
    private sealed interface DetailPanelConfig {
        @Serializable
        data class Message(val messageId: Long?) : DetailPanelConfig

        @Serializable
        data class Setting(val settingType: SettingType) : DetailPanelConfig
    }
}