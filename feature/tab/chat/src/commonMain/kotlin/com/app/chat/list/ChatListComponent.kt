package com.app.chat.list

import com.arkivanov.decompose.value.Value

interface ChatListComponent {
    val model: Value<Model>

    data class Model(
        val chats: List<Chat>,
        val selectedChatId: Long?
    )

    fun onChatClick(id: Long?)
    fun onSearchChange(search: String)

    data class Chat(
        val id: Long,
        val author: Author,
        val avatar: String,
        val title: String,
        val time: String,
    )

    data class Author(
        val id: Long,
        val name: String,
        val text: String,
        val bio: String,
    )

}