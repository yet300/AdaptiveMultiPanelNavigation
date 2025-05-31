package com.app.chat.detail

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.Value
import com.common.decompose.PreviewComponentContext

class PreviewChatDetailComponent :
    ChatDetailComponent,
    ComponentContext by PreviewComponentContext {
    override val model: Value<ChatDetailComponent.Model>
        get() = TODO("Not yet implemented")

    override fun onBack() {
        TODO("Not yet implemented")
    }
}