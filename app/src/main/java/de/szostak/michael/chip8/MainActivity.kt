package de.szostak.michael.chip8

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import java.io.BufferedReader
import java.io.InputStreamReader

class MainActivity : AppCompatActivity() {
    // TODO: create launcher activity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        run_button.setOnClickListener(runListener)
        reset_button.setOnClickListener(resetListener)
        print_button.setOnClickListener(printListener)
        dump_button.setOnClickListener(dumpListener)
    }

    private val runListener = View.OnClickListener {

    }

    private val resetListener = View.OnClickListener {
        CPU.reset()
    }

    private val printListener = View.OnClickListener {

    }

    private val dumpListener = View.OnClickListener {
        CPU.loadFile(BufferedReader(InputStreamReader(assets
                .open("particle_demo"))), 512)
        CPU.dumpMemory(512, 1024)
    }
}
