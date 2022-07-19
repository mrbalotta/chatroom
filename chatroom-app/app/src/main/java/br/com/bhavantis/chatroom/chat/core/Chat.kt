package br.com.bhavantis.chatroom.chat.core

import br.com.bhavantis.chatroom.chat.model.ChatMessage

interface Chat {
    fun connect(vararg auto: String = emptyArray())
    fun send(destination: String, message: ChatMessage)
}