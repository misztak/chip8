package de.szostak.michael.chip8

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private val display = Display()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //TODO: save bitmap instance

        displayView.setImageDrawable(display.scaledBitmap)

        reset_button.setOnClickListener(resetListener)
        test_button.setOnClickListener(testListener)
        print_button.setOnClickListener(printListener)
    }

    private val resetListener = View.OnClickListener {
        display.reset()
    }

    private val testListener = View.OnClickListener {
        display.switchPixel(5, 0, true)
    }

    private val printListener = View.OnClickListener {
        display.dumpDisplay()
    }
}
