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

    private val tabComponent: TabComponent =
        DefaultTabComponent(
            componentContext = this,
            storeProvider = storeFactory,
            selectedChatId = navState.map { panelsState ->
                if (panelsState.details is DetailPanelConfig.Message) {
                    (panelsState.details as DetailPanelConfig.Message).messageId
                } else {
                    null
                }
            },
            onChatClick = { itemId ->
                panelsNavigation.navigate { currentPanels ->
                    val newDetailsConfig = DetailPanelConfig.Message(messageId = itemId)
                    lastSelectedMessageDetailConfig = newDetailsConfig
                    currentPanels.copy(details = newDetailsConfig)
                }
            },
            selectedSetting = navState.map { panelsState ->
                if (panelsState.details is DetailPanelConfig.Setting) {
                    (panelsState.details as DetailPanelConfig.Setting).settingType
                } else {
                    null
                }
            },
            onOpenSettingDetails = { settingType ->
                panelsNavigation.navigate { currentPanels ->
                    val newDetailsConfig =
                        DetailPanelConfig.Setting(settingType = settingType)
                    lastSelectedSettingDetailConfig = newDetailsConfig
                    currentPanels.copy(details = newDetailsConfig)
                }
            }
        )

    override val panels: Value<ChildPanels<*, PanelComponent.MainTab, *, PanelComponent.DetailPanel, Nothing, Nothing>> =
        childPanels(
            source = panelsNavigation,
            serializers = MainPanelConfig.serializer() to DetailPanelConfig.serializer(),
            onStateChanged = { newState, oldState ->
                _navState.onNext(newState)
                val currentDetail = newState.details
                if (currentDetail != oldState?.details) {
                    when (currentDetail) {
                        is DetailPanelConfig.Message -> {
                            lastSelectedMessageDetailConfig = currentDetail
                        }

                        is DetailPanelConfig.Setting -> {
                            lastSelectedSettingDetailConfig = currentDetail
                        }

                        null -> {}
                    }
                }
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
                            onBacked = {
                                panelsNavigation.pop()
                                lastSelectedMessageDetailConfig = null
                            },
                        )
                    )

                    is DetailPanelConfig.Setting -> PanelComponent.DetailPanel.SettingsDetails(
                        component = DefaultSettingsDetailsComponent(
                            componentContext = ctx,
                            settingType = config.settingType,
                            onBacked = {
                                panelsNavigation.pop()
                                lastSelectedSettingDetailConfig = null
                            }
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
        panelsNavigation.navigate { currentPanels ->
            currentPanels.copy(
                main = MainPanelConfig,
                details = if (currentPanels.mode.isSingle) null else lastSelectedMessageDetailConfig
            )
        }
    }

    override fun openSettings() {
        tabComponent.openSettings()
        panelsNavigation.navigate { currentPanels ->
            currentPanels.copy(
                main = MainPanelConfig,
                details = if (currentPanels.mode.isSingle) null else lastSelectedSettingDetailConfig
            )
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