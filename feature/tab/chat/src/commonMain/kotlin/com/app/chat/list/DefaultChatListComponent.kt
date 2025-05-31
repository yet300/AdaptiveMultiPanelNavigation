package com.app.chat.list

import com.app.chat.database.impl.DefaultChatDatabase
import com.app.chat.database.model.AuthorEntity
import com.app.chat.database.model.ChatEntity
import com.app.chat.list.ChatListComponent.Author
import com.app.chat.list.ChatListComponent.Chat
import com.app.chat.list.store.ChatStore
import com.app.chat.list.store.ChatStoreFactory
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.Value
import com.arkivanov.decompose.value.operator.map
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.badoo.reaktive.observable.Observable
import com.common.decompose.asValue

class DefaultChatListComponent(
    componentContext: ComponentContext,
    storeProvider: StoreFactory,
    selectedChatId: Observable<Long?>,
    private val onChatClicked: (Id: Long?) -> Unit,
) : ComponentContext by componentContext, ChatListComponent {

    private val database = DefaultChatDatabase()


    private val storeFactory = ChatStoreFactory(
        selectedChatId = selectedChatId,
        storeFactory = storeProvider,
        database = database
    )

    private val store = instanceKeeper.getStore {
        storeFactory.create()
    }

    override val model: Value<ChatListComponent.Model> = store.asValue().map {
        ChatListComponent.Model(
            chats = it.chats.map { it.toAuthor() },
            selectedChatId = it.selectedChatId
        )
    }

    override fun onChatClick(id: Long?) {
        onChatClicked(id)
    }


    override fun onSearchChange(search: String) {
        store.accept(ChatStore.Intent.OnSearchChange(search))
    }

    private fun ChatEntity.toAuthor(): Chat =
        Chat(
            id = id,
            author = author.toAuthor(),
            avatar = avatar,
            title = title,
            time = time
        )


    private fun AuthorEntity.toAuthor(): Author =
        Author(
            id = id,
            name = name,
            text = text,
            bio = bio,
        )

}