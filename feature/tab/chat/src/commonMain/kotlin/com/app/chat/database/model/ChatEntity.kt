package com.app.chat.database.model

internal data class ChatEntity(
    val id: Long,
    val author: AuthorEntity,
    val avatar: String,
    val title: String,
    val time: String,
)