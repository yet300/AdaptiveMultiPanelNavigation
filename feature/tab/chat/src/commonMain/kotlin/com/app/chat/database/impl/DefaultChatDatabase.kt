package com.app.chat.database.impl

import com.app.chat.database.LorenIpsumGenerator.generate
import com.app.chat.database.LorenIpsumGenerator.generateSentence
import com.app.chat.database.LorenIpsumGenerator.generateTime
import com.app.chat.database.LorenIpsumGenerator.randomEmoji
import com.app.chat.database.LorenIpsumGenerator.randomWord
import com.app.chat.database.api.ChatDatabase
import com.app.chat.database.model.AuthorEntity
import com.app.chat.database.model.ChatEntity
import com.app.chat.database.model.MessageEntity
import kotlin.random.Random

internal class DefaultChatDatabase : ChatDatabase {

    private val authors =
        List(10) { index ->
            AuthorEntity(
                id = index.toLong() + 1L,
                avatar = randomEmoji(),
                name = "${randomWord(capitalize = true)} ${randomWord(capitalize = true)}",
                bio = List(50) { generateSentence() }.joinToString(separator = " "),
                text = generateSentence(),
            )
        }

    private val chats = List(50) { index ->
        ChatEntity(
            id = index.toLong() + 1L,
            author = authors[index % authors.size],
            avatar = randomEmoji(),
            title = generate(count = Random.nextInt(3, 5), minWordLength = 3)
                .joinToString(separator = " ") { it.replaceFirstChar(Char::uppercase) },
            time = generateTime()
        )
    }

    private val messages = chats.flatMap { chat ->
        List(Random.nextInt(5, 20)) { msgIndex ->
            MessageEntity(
                id = msgIndex.toLong() + chat.id * 1000,
                chatId = chat.id,
                author = authors[Random.nextInt(authors.size)],
                text = generateSentence(),
                time = generateTime()
            )
        }
    }


    private val chatsMap = chats.associateBy(ChatEntity::id)
    private val messagesMap = messages.groupBy { it.chatId }


    override fun getChats(): List<ChatEntity> = chats

    override fun getChat(id: Long): ChatEntity =
        chatsMap.getValue(id)

    override fun getMessages(chatId: Long): List<MessageEntity> =
        messagesMap.getValue(chatId)

}