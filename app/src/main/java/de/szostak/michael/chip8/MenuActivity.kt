package de.szostak.michael.chip8

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import kotlinx.android.synthetic.main.activity_menu.*

class MenuActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        start_button.setOnClickListener(startListener)
        settings_button.setOnClickListener(stopListener)
    }

    private val startListener = View.OnClickListener {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    private val stopListener = View.OnClickListener {

    }
}