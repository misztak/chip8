package de.szostak.michael.chip8

import org.junit.Before
import org.junit.Test

class CPUTest {

    private fun setMemoryValue(value: Int) {
        CPU.memory[CPU.pc] = (value and 0xFF00) shr 8
        CPU.memory[CPU.pc+1] = value and 0xFF
    }

    @Before
    fun resetCPU() {
        CPU.reset()
    }

    @Test
    fun pcAtCorrectMemoryAddress() {
        assert(CPU.pc == 512)
    }

    // TODO: create semi-dynamic tests

    @Test
    fun fetchReturnsCorrectOpcode() {
        setMemoryValue(0)
        assert(0 == CPU.fetch())

        setMemoryValue(0xFFFF)
        assert(0xFFFF == CPU.fetch())

        setMemoryValue(0xF0F)
        assert(0xF0F == CPU.fetch())

        setMemoryValue(0xF00F0)
        assert(0xF0 == CPU.fetch())
    }
}