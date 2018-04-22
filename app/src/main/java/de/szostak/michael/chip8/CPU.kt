package de.szostak.michael.chip8

import android.util.Log
import java.io.BufferedReader
import java.io.InputStreamReader

object CPU {
    private val tag = javaClass.simpleName

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
    var display: Display = Display()

    init {
        // TODO: see if initialization inside reset can be recognised by compiler
        reset()
        loadFile(BufferedReader(InputStreamReader(App.getAssetManager().open("fontset"))), 0)
    }

    fun fetch(): Int {
        // TODO: unit tests
        val opcode: Int = ((memory[pc] and 0xFF) shl 8) or (memory[pc+1] and 0xFF)
        // could cause problems to update pc this early
        pc += 2
        return opcode
    }

    private fun reset() {
        memory = Array(4096, {0})
        pc = 0

        V = Array(16, {0})
        I = 0

        stack = Array(16, {0})
        sp = 0

        soundTimer = 0
        delayTimer = 0

        // keyboard

        display = Display()
    }

    fun loadFile(reader: BufferedReader, from: Int) {
        // TODO: check behaviour for binary files
        // TODO: check if file isn't too big
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
        if (!isWithinMemory(from, to)) return
        Log.d(tag, "Dumping memory from address $from to $to")
        for (i in from until to) {
            Log.d(tag, "Pos $i: Dec ${memory[i]} Hex ${memory[i].toString(16)}")
        }
    }

    private fun isWithinMemory(from: Int, to: Int): Boolean {
        // TODO: see if kotlin offers an implementation for this
        // TODO: do unit test for this
        if (to <= from) return false
        if (from < 0 || from >= memory.size) return false
        if (to < 1 || to > memory.size) return false
        return true
    }
}