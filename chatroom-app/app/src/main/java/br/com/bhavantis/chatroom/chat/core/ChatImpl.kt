package br.com.bhavantis.chatroom.chat.core

import br.com.bhavantis.chatroom.chat.model.ChatMessage
import br.com.bhavantis.chatroom.infrastructure.logging.Logger
import br.com.bhavantis.chatroom.infrastructure.messaging.DefaultConnectionListener
import br.com.bhavantis.chatroom.infrastructure.messaging.MessagingBroker
import br.com.bhavantis.chatroom.infrastructure.messaging.MessagingListener
import br.com.bhavantis.jinko.di.core.Component
import br.com.bhavantis.jinko.di.core.ComponentMapping
import br.com.bhavantis.jinko.di.core.Single

@Single
@Component
@ComponentMapping(Chat::class)
class ChatImpl(
    private val broker: MessagingBroker,
    private val logger: Logger
): Chat, MessagingListener {
    override fun connect(vararg auto: String) {
        broker.connect(AutoSubscription(auto.toList()))
    }

    override fun send(destination: String, message: ChatMessage) {
        broker.send(destination, message)
    }

    private inner class AutoSubscription(
        private val auto: List<String>
    ): DefaultConnectionListener() {
        override fun onOpened(broker: MessagingBroker) {
            super.onOpened(broker)
            if(auto.isNotEmpty()) {
                auto.forEach {
                    broker.subscribe(it, this@ChatImpl)
                }
            }
        }
    }

    override fun onMessage(topic: String, message: String) {
        logger.info("ICHAT", "on message arrived")
    }
}