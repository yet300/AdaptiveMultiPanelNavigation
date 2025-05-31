package com.app.settings.list

import com.app.settings.SettingType
import com.app.settings.list.SettingsComponent.Model
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.arkivanov.decompose.value.update
import com.badoo.reaktive.coroutinesinterop.asFlow
import com.badoo.reaktive.observable.Observable
import com.common.decompose.coroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch

@OptIn(ExperimentalCoroutinesApi::class)
class DefaultSettingsComponent(
    componentContext: ComponentContext,
    selectedSetting: Observable<SettingType?>,
    private val onOpenSettingDetails: (SettingType) -> Unit,
) : ComponentContext by componentContext, SettingsComponent {

    private val _model =
        MutableValue(
            Model(
                selectedSetting = null
            )
        )
    override val model: Value<Model> = _model

    init {
        val selectFlow = selectedSetting.asFlow()
        coroutineScope().launch {
            selectFlow.collect { selectedSetting ->
                _model.update { it.copy(selectedSetting = selectedSetting) }
            }
        }
    }

    override fun onProfileClick() {
        onOpenSettingDetails(SettingType.Profile)
    }

    override fun onGeneralClick() {
        onOpenSettingDetails(SettingType.General)
    }

    override fun onNotificationClick() {
        onOpenSettingDetails(SettingType.Notification)
    }

    override fun onPrivacyClick() {
        onOpenSettingDetails(SettingType.Privacy)
    }

    override fun onDataStorageClick() {
        onOpenSettingDetails(SettingType.Storage)
    }

    override fun onActiveSessionClick() {
        onOpenSettingDetails(SettingType.ActiveSession)
    }

    override fun onAppearanceClick() {
        onOpenSettingDetails(SettingType.Appearance)
    }

    override fun onLanguageClick() {
        onOpenSettingDetails(SettingType.Language)
    }

    override fun onEmojiClick() {
        onOpenSettingDetails(SettingType.Emoji)
    }

    override fun onChatFolderClick() {
        onOpenSettingDetails(SettingType.ChatFolder)
    }

}