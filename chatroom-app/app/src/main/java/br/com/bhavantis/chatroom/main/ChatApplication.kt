package br.com.bhavantis.chatroom.main

import android.app.Application
import br.com.bhavantis.jinko.di.GeneratedProviders

class ChatApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        GeneratedProviders().load()
    }
}