package de.szostak.michael.chip8

import org.junit.Before
import org.junit.Test

class CPUTest {

    private fun setMemoryValues(value1: Int, value2: Int) {
        CPU.memory[CPU.pc] = value1 and 0xFF
        CPU.memory[CPU.pc+1] = value2 and 0xFF
    }

    @Before
    fun resetCPU() {
        CPU.reset()
    }

    @Test
    fun fetchReturnsCorrectOpcode() {
        setMemoryValues(0, 0)
        assert(0 == CPU.fetch())

        setMemoryValues("FF".toInt(16), "FF".toInt(16))
        assert("FFFF".toInt(16) == CPU.fetch())
    }
}