package com.app.settings.detail

import com.app.settings.SettingType
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value

class DefaultSettingsDetailsComponent(
    componentContext: ComponentContext,
    settingType: SettingType,
    private val onBacked: () -> Unit,
) : ComponentContext by componentContext, SettingsDetailsComponent {

    private val _model = MutableValue(
        SettingsDetailsComponent.Model(settingType = settingType)
    )
    override val model: Value<SettingsDetailsComponent.Model> = _model

    override fun onBackClick() = onBacked()

}