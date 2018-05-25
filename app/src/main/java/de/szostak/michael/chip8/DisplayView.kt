package de.szostak.michael.chip8

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Point
import android.util.AttributeSet
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.view.WindowManager

class DisplayView: SurfaceView, SurfaceHolder.Callback {
    private val tag = javaClass.simpleName

    private lateinit var viewContext: Context
    private lateinit var emulationThread: EmulationThread

    private var displayWidth = 0
    private var displayHeight = 0

    private val paint = Paint()

    constructor(context: Context): super(context) {
        init(context)
    }

    constructor(context: Context, attributeSet: AttributeSet): super(context, attributeSet) {
        init(context)
    }

    private fun init(context: Context) {
        viewContext = context

        holder.addCallback(this)

        setWillNotDraw(false)

        emulationThread = EmulationThread(this)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        val scaledPixelWidth: Int = width / 64
        val scaledPixelHeight: Int = height / 32

        for (x in 0 until 64) {
            for (y in 0 until 32) {
                if (CPU.display[x][y] == 1) {
                    paint.color = Color.WHITE
                } else {
                    paint.color = Color.BLACK
                }
                canvas?.drawRect((x * scaledPixelWidth).toFloat(), (y * scaledPixelHeight).toFloat(),
                        ((x + 1) * scaledPixelWidth).toFloat(), ((y + 1) * scaledPixelHeight).toFloat(), paint)
            }
        }
    }

    @SuppressLint("SwitchIntDef")
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val windowManager = viewContext.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val display = windowManager.defaultDisplay
        val point = Point()
        display.getSize(point)

        val desiredWidth = point.x
        val desiredHeight = point.y

        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val widthSize = MeasureSpec.getSize(widthMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        val heightSize = MeasureSpec.getSize(heightMeasureSpec)

        displayWidth = when (widthMode) {
            MeasureSpec.EXACTLY -> widthSize
            MeasureSpec.AT_MOST -> Math.min(desiredWidth, widthSize)
            else -> desiredWidth
        }

        displayHeight = when (heightMode) {
            MeasureSpec.EXACTLY -> heightSize
            MeasureSpec.AT_MOST -> Math.min(desiredHeight, heightSize)
            else -> desiredHeight
        }

        setMeasuredDimension(displayWidth, displayHeight)
    }

    override fun surfaceChanged(p0: SurfaceHolder?, p1: Int, p2: Int, p3: Int) {
    }

    override fun surfaceCreated(p0: SurfaceHolder?) {
        emulationThread.setRunning(true)
        emulationThread.start()
    }

    override fun surfaceDestroyed(p0: SurfaceHolder?) {
        emulationThread.setRunning(false)
        emulationThread.join()
    }

}