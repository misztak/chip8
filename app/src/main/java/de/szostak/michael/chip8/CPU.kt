package de.szostak.michael.chip8

import android.util.Log
import java.io.BufferedReader
import java.io.InputStreamReader

object CPU {
    val tag = javaClass.simpleName!!

    // 8 bit memory with 16 bit pc
    var memory = Array(4096, {0})
    var pc: Int = 0

    // 8 bit registers and 16 bit I register
    var V = Array(16, {0})
    var I: Int = 0

    // 16 bit stack with 8 bit sp
    var stack = Array(16, {0})
    var sp: Int = 0

    // 8 bit timer
    var soundTimer: Int = 0
    var delayTimer: Int = 0

    // keyboard

    // 64x32 display
    val display = Display()

    init {
        loadFile(BufferedReader(InputStreamReader(App.getAssetManager().open("fontset"))), 0)
    }

    fun loadFile(reader: BufferedReader, from: Int) {
        var position = from
        val values = reader.readLines()
        values.forEach {
            val value = it.split(" ")
            value.forEach {
                memory[position] = Integer.parseInt(it, 16) and 0xFF
                position++
            }
        }
        Log.d(tag, "Successfully loaded file into memory")
    }

    fun dumpMemory(from: Int, to: Int) {
        for (i in from until to) {
            Log.d(tag, "Pos $i: ${memory[i]}")
        }
    }
}