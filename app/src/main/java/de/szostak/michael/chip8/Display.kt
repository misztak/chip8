package de.szostak.michael.chip8

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.util.Log

class Display {
    private val tag = javaClass.simpleName

    private val width = 64
    private val height = 32
    val bitmap: Bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565)
    val scaledBitmap: BitmapDrawable = BitmapDrawable(Resources.getSystem(), bitmap)

    init {
        scaledBitmap.isFilterBitmap = false
    }

    fun switchPixel(x: Int, y: Int, state: Boolean) {
        bitmap.setPixel(x, y, if (state) Color.WHITE else Color.BLACK)
    }

    fun reset() {
        val colorBlack = Color.BLACK
        for (i in 0 until width) {
            for (j in 0 until height) {
                bitmap.setPixel(i, j, colorBlack)
            }
        }
    }

    fun dumpDisplay() {
        val builder = StringBuilder("Dumping display values\n")
        for (i in 0 until height) {
            for (j in 0 until width) {
                builder.append(if (bitmap.getPixel(j, i) == Color.WHITE) "X" else "-")
            }
            builder.append("\n")
        }
        Log.i(tag, builder.toString())
    }
}