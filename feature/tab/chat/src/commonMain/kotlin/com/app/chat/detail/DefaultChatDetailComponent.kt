package com.app.chat.detail

import com.app.chat.database.impl.DefaultChatDatabase
import com.app.chat.database.model.AuthorEntity
import com.app.chat.database.model.MessageEntity
import com.app.chat.detail.ChatDetailComponent.Author
import com.app.chat.detail.ChatDetailComponent.Message
import com.app.chat.detail.store.ChatDetailStoreFactory
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.Value
import com.arkivanov.decompose.value.operator.map
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.badoo.reaktive.observable.Observable
import com.common.decompose.asValue

class DefaultChatDetailComponent(
    componentContext: ComponentContext,
    provideStoreFactory: StoreFactory,
    chatId: Long?,
    isBackButtonVisible: Observable<Boolean>,
    private val onBacked: () -> Unit,
) : ComponentContext by componentContext, ChatDetailComponent {

    private val database = DefaultChatDatabase()

    private val storeFactory = ChatDetailStoreFactory(
        storeFactory = provideStoreFactory,
        database = database,
        chatId = chatId,
        isBackButtonVisible = isBackButtonVisible
    )

    private val store = instanceKeeper.getStore { storeFactory.create() }

    override val model: Value<ChatDetailComponent.Model> = store.asValue().map {
        ChatDetailComponent.Model(
            messages = it.messages.map { it.toMessage() },
            isBackButtonVisible = it.isBackButtonVisible
        )
    }

    override fun onBack() = onBacked()

    private fun MessageEntity.toMessage(): Message =
        Message(
            id = id,
            chatId = chatId,
            author = author.toAuthor(),
            text = text,
            time = time
        )

    private fun AuthorEntity.toAuthor(): Author =
        Author(
            id = id,
            avatar = avatar,
            name = name,
            text = text,
            bio = bio,
        )

}