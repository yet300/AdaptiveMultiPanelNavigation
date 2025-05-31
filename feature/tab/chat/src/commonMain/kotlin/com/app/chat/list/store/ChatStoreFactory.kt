package com.app.chat.list.store

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.badoo.reaktive.coroutinesinterop.asFlow
import com.badoo.reaktive.observable.Observable
import com.app.chat.database.api.ChatDatabase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

internal class ChatStoreFactory(
    private val selectedChatId: Observable<Long?>,
    private val storeFactory: StoreFactory,
    private val database: ChatDatabase
) {

    fun create(): ChatStore =
        object : ChatStore, Store<ChatStore.Intent, ChatStore.State, Nothing> by storeFactory.create(
            name = "ChatListStore",
            initialState = ChatStore.State(),
            bootstrapper = SimpleBootstrapper(ChatStore.Action.DataLoaded(database.getChats())),
            executorFactory = ::ExecutorImpl,
            reducer = ReducerImpl
        ) {}

    private object ReducerImpl : Reducer<ChatStore.State, ChatStore.Message> {
        override fun ChatStore.State.reduce(msg: ChatStore.Message): ChatStore.State =
            when (msg) {
                is ChatStore.Message.StartLoading -> this
                is ChatStore.Message.DataLoaded -> copy(chats = msg.chats)
                is ChatStore.Message.SelectedIdChanged -> copy(selectedChatId = msg.selectedId)
            }
    }


    @OptIn(ExperimentalCoroutinesApi::class)
    private inner class ExecutorImpl :
        CoroutineExecutor<ChatStore.Intent, ChatStore.Action, ChatStore.State, ChatStore.Message, Nothing>() {

        override fun executeAction(action: ChatStore.Action) {
            when (action) {
                is ChatStore.Action.DataLoaded -> {
                    dispatch(ChatStore.Message.DataLoaded(action.chats))
                }

                is ChatStore.Action.SelectedIdChanged -> {
                    dispatch(ChatStore.Message.SelectedIdChanged(action.selectedId))
                }
            }
        }

        override fun executeIntent(intent: ChatStore.Intent) {
            when (intent) {
                is ChatStore.Intent.OnSearchChange -> {
                    val allChats = database.getChats()
                    val filteredChats = if (intent.search.isBlank()) {
                        allChats
                    } else {
                        allChats.filter {
                            it.title.contains(intent.search, ignoreCase = true)
                        }
                    }
                    dispatch(ChatStore.Message.DataLoaded(filteredChats))
                }
            }
        }

        init {
            val selectedIdFlow: Flow<Long?> = selectedChatId.asFlow()
            scope.launch {
                selectedIdFlow.collect { selectedId ->
                    dispatch(ChatStore.Message.SelectedIdChanged(selectedId))
                }
            }
        }
    }

}