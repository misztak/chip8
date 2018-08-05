package de.szostak.michael.chip8

import android.annotation.SuppressLint
import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.Button
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private val tag = javaClass.simpleName

    @Suppress("ObjectLiteralToLambda")
    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        pause_button.setOnClickListener(pauseListener)
        dump_button.setOnClickListener(dumpListener)

        for (i in 0 .. 15) {
            val id = resources.getIdentifier("button_$i", "id", packageName)
            val button = findViewById<Button>(id)
            button.setOnTouchListener(object : View.OnTouchListener {
                override fun onTouch(view: View?, event: MotionEvent?): Boolean {
                    when (event?.action) {
                        MotionEvent.ACTION_DOWN -> {
                            CPU.keyboard[i] = 1
                            CPU.keyPressed = i
                        }
                        MotionEvent.ACTION_UP -> {
                            CPU.keyboard[i] = 0
                        }
                    }
                    return true
                }
            })
        }

        // TODO: rework profiler attach
    }

    private val pauseListener = View.OnClickListener {
        if (!CPU.pauseFlag) {
            Log.d(tag, "Pausing execution")
            CPU.pauseFlag = true
        } else {
            Log.d(tag, "Resuming execution")
            CPU.pauseFlag = false
        }
    }

    private val dumpListener = View.OnClickListener {
        Log.d(tag, "Dumping profiler values")
        val occurrences = StringBuilder()
        CPU.profiler.opcodeOccurrences().forEach { key, value ->
            occurrences.append("${key.toString(16)} -> $value\n")
        }
        Log.d(tag, occurrences.toString())

        applicationContext.openFileOutput("cpu_log.txt", Context.MODE_PRIVATE).use {
            it.write(CPU.profiler.snapshots.toString().toByteArray())
        }
    }

    fun keyboardClicked(view: View) {
        // TODO: do this with tags instead of text
        val index = (view as Button).text.toString().toInt(16)
        CPU.keyboard[index] = if (CPU.keyboard[index] == 0) 1 else 0
        CPU.keyPressed = index
    }
}
