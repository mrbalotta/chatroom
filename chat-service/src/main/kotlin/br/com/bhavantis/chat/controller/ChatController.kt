package br.com.bhavantis.chat.controller

import br.com.bhavantis.chat.model.ChatMessage
import br.com.bhavantis.chat.model.User
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.messaging.handler.annotation.SendTo
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class ChatController(private val messagingTemplate: SimpMessagingTemplate) {

    companion object {
        val map = mutableMapOf<String, User>()
    }

    private val logger: Logger = LoggerFactory.getLogger(ChatController::class.java)

    @MessageMapping("/broadcast") //chat/broadcast
    @SendTo("/room/public")
    fun sendBroadcast(@Payload chatMessage: ChatMessage): ChatMessage {
        println("mensagem recebida: ${chatMessage.content}")
        return chatMessage
    }

    @MessageMapping("/private") //chat/private
    fun sendPrivateMessage(@Payload chatMessage: ChatMessage): ChatMessage {
        messagingTemplate.convertAndSendToUser(chatMessage.receiver.id, "/private", chatMessage) //user/{name}/private
        return chatMessage
    }

    @PostMapping("/api/users")
    fun createUser(@RequestBody user: User): List<User> {
        map[user.id] = user
        val users = map.values.toList()
        messagingTemplate.convertAndSend("/room/users", users)
        return users
    }

}