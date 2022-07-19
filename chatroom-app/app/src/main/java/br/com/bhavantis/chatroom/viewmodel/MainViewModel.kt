package br.com.bhavantis.chatroom.viewmodel

import androidx.lifecycle.ViewModel
import br.com.bhavantis.chatroom.chat.core.Chat
import br.com.bhavantis.chatroom.chat.model.ChatMessage
import br.com.bhavantis.chatroom.chat.model.ChatUser
import br.com.bhavantis.jinko.di.inject

class MainViewModel: ViewModel() {
    private val chat: Chat by inject()

    fun connect() {
        chat.connect("/user/alessandro/private") //"user/${user.id}/private"
    }

    fun sendPrivate() {
        chat.send("/chat/private", ChatMessage(
            sender = ChatUser("main", ""),
            receiver = ChatUser("alessandro", ""),
            content = "gostoso!"
        ))
    }
}