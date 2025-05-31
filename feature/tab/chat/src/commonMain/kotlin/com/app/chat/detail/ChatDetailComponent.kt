package com.app.chat.detail

import com.arkivanov.decompose.value.Value


interface ChatDetailComponent {
    val model: Value<Model>

    data class Model(
        val messages: List<Message>,
        val isBackButtonVisible: Boolean,
    )

    fun onBack()

    data class Message(
        val id: Long,
        val chatId: Long,
        val author: Author,
        val text: String,
        val time: String
    )

    data class Author(
        val id: Long,
        val avatar: String,
        val name: String,
        val text: String,
        val bio: String,
    )

}