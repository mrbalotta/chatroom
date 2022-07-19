package br.com.bhavantis.chatroom.main

import br.com.bhavantis.chatroom.infrastructure.messaging.MessagingBroker
import br.com.bhavantis.chatroom.infrastructure.messaging.StompMessagingBroker
import br.com.bhavantis.chatroom.infrastructure.logging.DefaultLogger
import br.com.bhavantis.chatroom.infrastructure.logging.Logger
import br.com.bhavantis.chatroom.infrastructure.logging.Verbose
import br.com.bhavantis.jinko.di.core.Bean
import br.com.bhavantis.jinko.di.core.Provider
import br.com.bhavantis.jinko.di.core.Single
import br.com.bhavantis.jinko.di.get
import com.google.gson.Gson
import com.google.gson.GsonBuilder

@Provider
class Configuration {

    @Bean @Single
    fun getBroker(): MessagingBroker {
        return StompMessagingBroker(
            "http://10.0.2.2:9090/app/websocket",
            get()
        )
    }

    @Bean @Single
    fun getLogger(): Logger {
        val logger = DefaultLogger()
        logger.setLevel(Verbose)
        return logger
    }

    @Bean @Single
    fun gson(): Gson = GsonBuilder().create()
}