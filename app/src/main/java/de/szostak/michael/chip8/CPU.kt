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
    lateinit var display: Display

    fun run() {
        reset()
        display.reset()
        loadFile(BufferedReader(InputStreamReader(App.getAssetManager().open("fontset"))), 0)

        // start emulation loop
    }

    fun fetch(): Int {
        val opcode: Int = ((memory[pc] and 0xFF) shl 8) or (memory[pc+1] and 0xFF)
        // could cause problems to update pc this early

        return opcode
    }

    fun tick(opcode: Int): Int {
        // TODO: write method description
        // TODO: find useful return value
        val index = (opcode and 0xF000) shr 12
        val value = opcode and 0xFFF
        when (index) {
            0 -> {
                when (value) {
                    0x0E0 -> {
                        // clear the display
                        display.reset()
                    }
                    0x0EE -> {
                        // return from subroutine
                        if (sp > 0) {
                            pc = stack[sp]
                            sp--
                        }
                    }
                    else -> {
                        // error or call to RCA 1802 program
                        Log.e(tag, "Unimplemented opcode ${opcode.toString(16)}")
                    }
                }
            }
            1 -> {
                // jump to address 'value'
                pc = value
            }
            2 -> {
                // call subroutine at 'value'
                if (sp < 15) {
                    sp++
                    stack[sp] = pc
                    pc = memory[pc]
                }
            }
            3 -> {
                // skip next instruction if Vx == NN
                val x = (value and 0xF00) shr 8
                if (V[x] == (value and 0xFF)) {
                    pc += 2
                }
            }
            4 -> return 4
            5 -> return 5
            6 -> return 6
            7 -> return 7
            8 -> return 8
            9 -> return 9
            0xA -> return 0xA
            0xB -> return 0xB
            0xC -> return 0xC
            0xD -> return 0xD
            0xE -> return 0xE
            0xF -> return 0xF
            else -> return -1
        }
        Log.d(tag, "Successfully executed opcode ${opcode.toString(16)}")

        // TODO: see if this is the right place to increment
        pc += 2
        return opcode
    }

    fun reset() {
        // TODO: behaviour when resetting during emulation loop

        memory = Array(4096, {0})
        pc = 512

        V = Array(16, {0})
        I = 0

        stack = Array(16, {0})
        sp = 0

        soundTimer = 0
        delayTimer = 0

        // keyboard

    }

    fun loadDisplay() {
        display = Display
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