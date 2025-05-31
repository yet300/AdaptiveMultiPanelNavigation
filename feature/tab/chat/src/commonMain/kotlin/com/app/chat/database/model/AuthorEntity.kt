package com.app.chat.database.model

internal data class AuthorEntity(
    val id: Long,
    val avatar: String,
    val name: String,
    val text: String,
    val bio: String,
)