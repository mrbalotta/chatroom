package br.com.bhavantis.chatroom.infrastructure.messaging

abstract class ConnectionListener {
    open fun onClosed(broker: MessagingBroker) {}
    open fun onOpened(broker: MessagingBroker) {}
    open fun onFailed(broker: MessagingBroker, throwable: Throwable) {}
    open fun onHeartbeatFailed(broker: MessagingBroker, throwable: Throwable?) {}
}