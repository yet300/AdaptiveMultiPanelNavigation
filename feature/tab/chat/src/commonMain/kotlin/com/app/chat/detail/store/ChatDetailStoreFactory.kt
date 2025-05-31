package com.app.chat.detail.store

import com.app.chat.database.api.ChatDatabase
import com.app.chat.detail.store.ChatDetailStore.Action
import com.app.chat.detail.store.ChatDetailStore.Message
import com.app.chat.detail.store.ChatDetailStore.State
import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.badoo.reaktive.coroutinesinterop.asFlow
import com.badoo.reaktive.observable.Observable
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

internal class ChatDetailStoreFactory(
    private val storeFactory: StoreFactory,
    private val database: ChatDatabase,
    private val chatId: Long?,
    private val isBackButtonVisible: Observable<Boolean>,
) {

    fun create(): ChatDetailStore =
        object : ChatDetailStore, Store<Nothing, State, Nothing> by storeFactory.create(
            name = "ChatDetailStore",
            initialState = State(),
            bootstrapper = SimpleBootstrapper(Action.Load(database.getMessages(chatId ?: 0L))),
            executorFactory = ::ExecutorImpl,
            reducer = ReducerImpl
        ) {}

    @OptIn(ExperimentalCoroutinesApi::class)
    private inner class ExecutorImpl :
        CoroutineExecutor<Nothing, Action, State, Message, Nothing>() {
        override fun executeAction(action: Action) {
            when (action) {
                is Action.Load -> {
                    dispatch(Message.InitialDataLoaded(action.messages))
                }

                is Action.LoadFailed -> {
                    dispatch(Message.Error(action.error))
                }
            }
        }


        init {
            val visibleFlow: Flow<Boolean> = isBackButtonVisible.asFlow()
            scope.launch {
                visibleFlow.collect {
                    dispatch(Message.UpdateBackButtonVisibility(it))
                }
            }
        }

    }

    private object ReducerImpl : Reducer<State, Message> {
        override fun State.reduce(msg: Message): State =
            when (msg) {
                is Message.StartLoading -> copy(isLoading = true, error = null)
                is Message.InitialDataLoaded -> copy(
                    messages = msg.messages,
                    isLoading = false,
                    error = null
                )

                is Message.Error -> copy(isLoading = false, error = msg.message)
                is Message.UpdateBackButtonVisibility -> copy(isBackButtonVisible = msg.isVisible)
            }
    }
}