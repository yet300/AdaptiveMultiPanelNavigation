package com.app.settings.detail

import com.app.settings.SettingType
import com.arkivanov.decompose.value.Value

interface SettingsDetailsComponent {
    val model : Value<Model>

    data class Model(
        val settingType: SettingType,
    )

    fun onBackClick()
}