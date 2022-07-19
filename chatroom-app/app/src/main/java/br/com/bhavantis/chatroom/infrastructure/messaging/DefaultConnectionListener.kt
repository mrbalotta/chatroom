package br.com.bhavantis.chatroom.infrastructure.messaging

import br.com.bhavantis.chatroom.infrastructure.logging.Logger
import br.com.bhavantis.jinko.di.inject

open class DefaultConnectionListener: ConnectionListener() {
    private val logger: Logger by inject()

    override fun onClosed(broker: MessagingBroker) {
        logger.info(msg="connection closed")
        logger.info("ICHAT", "running 'onClosed' on: ${Thread.currentThread().name}")
    }

    override fun onOpened(broker: MessagingBroker) {
        logger.info(msg="connection opened")
        logger.info("ICHAT", "running 'onOpened' on: ${Thread.currentThread().name}")
    }

    override fun onFailed(broker: MessagingBroker, throwable: Throwable) {
        logger.error(msg = "connection failed", throwable = throwable)
        logger.info("ICHAT", "running 'onFailed' on: ${Thread.currentThread().name}")
    }

    override fun onHeartbeatFailed(broker: MessagingBroker, throwable: Throwable?) {
        logger.info("ICHAT", "running 'onHeartbeatFailed' on: ${Thread.currentThread().name}")
        val msg = "heartbeat failed"
        if(throwable == null) logger.warn(msg = msg)
        else logger.error(msg = msg, throwable = throwable)
    }
}