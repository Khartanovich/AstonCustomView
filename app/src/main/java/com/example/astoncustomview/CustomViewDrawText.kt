package com.example.astoncustomview

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

class CustomViewDrawText(context: Context, attributeSet: AttributeSet) :
    View(context, attributeSet) {
    private var paint = Paint()
    var colorAndText: Pair<Int, String>? = null

    private fun setColorAndText(colorAndText: Pair<Int, String>?): Pair<Int, String>? {
        return colorAndText
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val x = 0f
        val y = height - 10
        paint.style = Paint.Style.FILL
        paint.textSize = 200f
        if (colorAndText != null) {
            val color = setColorAndText(colorAndText)?.first
            val text = setColorAndText(colorAndText)?.second
            if (color != null && text != null) {
                paint.color = color
                canvas.drawText(text, x, y.toFloat(), paint)
            }
        }
    }
}
