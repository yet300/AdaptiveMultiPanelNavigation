package com.app.chat.detail.store

import com.app.chat.detail.store.ChatDetailStore.State
import com.arkivanov.mvikotlin.core.store.Store
import com.app.chat.database.model.MessageEntity

internal interface ChatDetailStore : Store<Nothing, State, Nothing> {
    data class State(
        val messages: List<MessageEntity> = emptyList(),
        val isBackButtonVisible: Boolean = false,
        val isLoading: Boolean = false,
        val error: String? = null
    )

    sealed interface Action {
        data class Load(val messages: List<MessageEntity>) : Action
        data class LoadFailed(val error: String) : Action
    }

    sealed interface Message {
        object StartLoading : Message
        data class InitialDataLoaded(val messages: List<MessageEntity>) : Message
        data class Error(val message: String) : Message
        data class UpdateBackButtonVisibility(val isVisible: Boolean) : Message
    }
}