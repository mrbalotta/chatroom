package br.com.bhavantis.chatroom.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.activity.viewModels
import br.com.bhavantis.chatroom.R
import br.com.bhavantis.chatroom.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {
    private val viewModel: MainViewModel by viewModels()
    private lateinit var sendPrivateBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        sendPrivateBtn = findViewById(R.id.sendPrivateBtn)
        sendPrivateBtn.setOnClickListener {
            viewModel.sendPrivate()
        }
        viewModel.connect()
    }
}