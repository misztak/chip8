package de.szostak.michael.chip8

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        run_button.setOnClickListener(runListener)
        reset_button.setOnClickListener(resetListener)
        print_button.setOnClickListener(printListener)
        dump_button.setOnClickListener(dumpListener)

        CPU.loadDisplay()
        displayView.setImageDrawable(CPU.display.scaledBitmap)
    }

    private val runListener = View.OnClickListener {
        CPU.run()
    }

    private val resetListener = View.OnClickListener {
        CPU.reset()
        CPU.display.reset()
    }

    private val printListener = View.OnClickListener {
        CPU.display.dumpDisplay()
    }

    private val dumpListener = View.OnClickListener {
        CPU.dumpMemory(0, 512)
    }
}
