package com.example.astoncustomview

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat

class CustomRoulette(
    context: Context,
    attributeSet: AttributeSet
) : View(context, attributeSet) {
    private var paint = Paint()

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val centerX = width / 2f
        val centerY = height / 2f
        val radius = width.coerceAtMost(height) / 2f
        val sweepAngle = 51.4285714286f
        val listData = listOf(
            0f to Color.RED,
            sweepAngle to ContextCompat.getColor(context, R.color.orange),
            sweepAngle * 2 to Color.YELLOW,
            sweepAngle * 3 to Color.GREEN,
            sweepAngle * 4 to ContextCompat.getColor(context, R.color.liteBlue),
            sweepAngle * 5 to Color.BLUE,
            sweepAngle * 6 to ContextCompat.getColor(context, R.color.purple)
        )
        paint.style = Paint.Style.FILL
        for (item in listData) {
            paint.color = item.second
            canvas.drawArc(
                centerX - radius,
                centerY - radius,
                centerX + radius,
                centerY + radius,
                item.first,
                sweepAngle,
                true,
                paint
            )
        }
    }
}