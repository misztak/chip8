package de.szostak.michael.chip8

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private val tag = javaClass.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        pause_button.setOnClickListener(pauseListener)
        dump_button.setOnClickListener(dumpListener)

        //CPU.profiler.attach()
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
}
