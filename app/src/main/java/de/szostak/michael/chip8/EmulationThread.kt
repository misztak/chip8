package de.szostak.michael.chip8

class EmulationThread(private val displayView: DisplayView) : Thread() {
    private var running: Boolean = false
    private val lock = Any()

    fun setRunning(running: Boolean) {
        synchronized(lock) {
            this.running = running
        }
    }

    override fun run() {
        var running = false
        synchronized(lock) {
            running = this.running
        }
        CPU.reset()
        while (running) {
            CPU.tick()

            if (CPU.endFlag) {
                setRunning(false)
            }

            if (CPU.drawFlag) {
                displayView.postInvalidate()
            }

            synchronized(lock) {
                running = this.running
            }
        }
    }
}
