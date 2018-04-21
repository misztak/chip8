package de.szostak.michael.chip8

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        reset_button.setOnClickListener(resetListener)
        test_button.setOnClickListener(testListener)
        print_button.setOnClickListener(printListener)
        dump_button.setOnClickListener(dumpListener)

        // TODO: see if this could cause memory leaks
        displayView.setImageDrawable(CPU.display.scaledBitmap)
    }

    private val resetListener = View.OnClickListener {
        CPU.display.reset()
    }

    private val testListener = View.OnClickListener {
        CPU.display.switchPixel(5, 0, true)
    }

    private val printListener = View.OnClickListener {
        CPU.display.dumpDisplay()
    }

    private val dumpListener = View.OnClickListener {
        CPU.dumpMemory(0, 512)
    }
}
