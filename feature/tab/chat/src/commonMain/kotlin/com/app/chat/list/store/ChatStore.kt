package com.app.chat.list.store

import com.app.chat.list.store.ChatStore.Intent
import com.arkivanov.mvikotlin.core.store.Store
import com.app.chat.database.model.ChatEntity

internal interface ChatStore : Store<Intent, ChatStore.State, Nothing> {
    data class State(
        val chats: List<ChatEntity> = emptyList(),
        val selectedChatId: Long? = null,
    )

    sealed interface Intent {
        data class OnSearchChange(val search: String) : Intent
    }

    sealed interface Action {
        data class DataLoaded(
            val chats: List<ChatEntity>,
        ) : Action

        data class SelectedIdChanged(val selectedId: Long?) : Action
    }

    sealed interface Message {
        object StartLoading : Message
        data class DataLoaded(
            val chats: List<ChatEntity>,
        ) : Message

        data class SelectedIdChanged(val selectedId: Long?) : Message
    }
}