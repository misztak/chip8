package de.szostak.michael.chip8

import android.util.Log

class Profiler {
    private val tag = javaClass.simpleName

    var active = false
    var opcodes = ArrayList<Int>()
    var snapshots = StringBuilder()

    fun attach() {
        active = true
        Log.d(tag, "Attached profiler to current session")

        opcodes.clear()
    }

    fun detach() {
        active = false
        Log.d(tag, "Detached profiler from current session")
    }

    fun addOpcode(opcode: Int) {
        if (active) opcodes.add(opcode)
    }

    fun addSnapshot(s: String) {
        if (active) snapshots.append(s)
    }

    fun opcodeOccurrences(): Map<Int, Int> {
        val opcodeMap = opcodes.groupingBy { it }.eachCount()
        return opcodeMap.toSortedMap()
    }
}