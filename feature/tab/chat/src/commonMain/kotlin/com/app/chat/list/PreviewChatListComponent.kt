package com.app.chat.list

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.Value
import com.common.decompose.PreviewComponentContext

class PreviewChatListComponent :
    ChatListComponent,
    ComponentContext by PreviewComponentContext {
    override val model: Value<ChatListComponent.Model>
        get() = TODO("Not yet implemented")

    override fun onChatClick(id: Long?) {
        TODO("Not yet implemented")
    }

    override fun onSearchChange(search: String) {
        TODO("Not yet implemented")
    }

}