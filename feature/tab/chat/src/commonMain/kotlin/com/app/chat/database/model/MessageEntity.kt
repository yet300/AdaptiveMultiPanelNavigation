package com.app.chat.database.model

internal data class MessageEntity(
    val id: Long,
    val chatId: Long,
    val author: AuthorEntity,
    val text: String,
    val time: String
)