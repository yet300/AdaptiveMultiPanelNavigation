package com.app.settings.list

import com.app.settings.SettingType
import com.arkivanov.decompose.value.Value

interface SettingsComponent {
    val model : Value<Model>

    data class Model(
        val selectedSetting: SettingType?,
    )

    fun onProfileClick()
    fun onGeneralClick()
    fun onNotificationClick()
    fun onPrivacyClick()
    fun onDataStorageClick()
    fun onActiveSessionClick()
    fun onAppearanceClick()
    fun onLanguageClick()
    fun onEmojiClick()
    fun onChatFolderClick()

}