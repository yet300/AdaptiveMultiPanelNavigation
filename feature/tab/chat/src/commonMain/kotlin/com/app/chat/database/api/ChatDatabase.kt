package com.app.chat.database.api

import com.app.chat.database.model.ChatEntity
import com.app.chat.database.model.MessageEntity

internal interface ChatDatabase {

    fun getChats(): List<ChatEntity>

    fun getChat(id: Long): ChatEntity

    fun getMessages(chatId: Long): List<MessageEntity>

}