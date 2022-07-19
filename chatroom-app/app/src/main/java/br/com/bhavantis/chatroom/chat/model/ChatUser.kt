package br.com.bhavantis.chatroom.chat.model

data class ChatUser(
    val id: String,
    val name: String,
    val online: Boolean = true
)