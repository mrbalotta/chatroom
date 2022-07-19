package br.com.bhavantis.chatroom.infrastructure.messaging

import br.com.bhavantis.chatroom.infrastructure.logging.Logger
import br.com.bhavantis.chatroom.infrastructure.parsing.JsonParser
import br.com.bhavantis.jinko.di.inject
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import ua.naiksoftware.stomp.Stomp
import ua.naiksoftware.stomp.dto.LifecycleEvent

class StompMessagingBroker(
    private val url: String,
    private val parser: JsonParser
): MessagingBroker {
    private val logger: Logger by inject()
    private val stompClient = Stomp.over(Stomp.ConnectionProvider.OKHTTP, url)
    private val compositeDisposable = CompositeDisposable()

    override fun connect(listener: ConnectionListener) {
        logger.info("ICHAT", "running 'StompMessagingBroker.connect' on: ${Thread.currentThread().name}")
        logger.info("ICHAT", "try to connect to $url")
        prepareClient(listener)
        stompClient.connect()
    }

    private fun prepareClient(listener: ConnectionListener) {
        val disposable = stompClient
            .lifecycle()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { event -> handleStompLifecycleEvent(event, listener) }
        compositeDisposable.add(disposable)
    }

    private fun handleStompLifecycleEvent(event: LifecycleEvent, listener: ConnectionListener) {
        logger.info("ICHAT", "running 'StompMessagingBroker.handleStompLifecycleEvent' on: ${Thread.currentThread().name}")
        when (event.type!!) {
            LifecycleEvent.Type.CLOSED -> { listener.onClosed(this) }
            LifecycleEvent.Type.ERROR -> { listener.onFailed(this, event.exception) }
            LifecycleEvent.Type.OPENED -> { listener.onOpened(this) }
            LifecycleEvent.Type.FAILED_SERVER_HEARTBEAT -> { listener.onHeartbeatFailed(this, event.exception) }
        }
    }

    override fun disconnect() {
        logger.info("ICHAT", "running 'StompMessagingBroker.disconnect' on: ${Thread.currentThread().name}")
        stompClient.disconnect()
    }

    override fun send(destination: String, message: Any) {
        logger.info("ICHAT", "running 'StompMessagingBroker.send' on: ${Thread.currentThread().name}")
        val disposable = stompClient
            .send(destination, parser.toJson(message))
            .subscribe()
        compositeDisposable.add(disposable)
    }

    override fun subscribe(topic: String, listener: MessagingListener) {
        logger.info("ICHAT", "running 'StompMessagingBroker.subscribe' on: ${Thread.currentThread().name}")
        val disposable = stompClient
            .topic(topic)
            .subscribe { listener.onMessage(topic, it.payload) }
        compositeDisposable.add(disposable)
    }
}