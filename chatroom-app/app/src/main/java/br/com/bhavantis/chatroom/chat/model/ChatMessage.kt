package br.com.bhavantis.chatroom.chat.model

data class ChatMessage(
    val sender: ChatUser,
    val receiver: ChatUser,
    val content: String
)