package com.example.astoncustomview

import android.animation.Animator
import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import kotlin.random.Random
import kotlin.random.nextInt

class CustomRouletteWithButtons @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {
    private val btnStart: Button
    private val btnReset: Button
    private val image: ImageView
    private val customRoulette: CustomRoulette
    private val drawText: CustomViewDrawText
    private val listString =
        listOf(
            ContextCompat.getColor(context, R.color.liteBlue) to "Голубой",
            Color.BLUE to "синий",
            ContextCompat.getColor(context, R.color.purple) to "фиолетовый",
            Color.RED to "красный",
            ContextCompat.getColor(context, R.color.orange) to "оранжевый",
            Color.YELLOW to "желтый",
            Color.GREEN to "зеленый"
        )
    private var oldAngle = 0
    var newAngle = 0
    private var halfAngle = 25.714f
    var url = "https://loremflickr.com/320/240"

    init {
        val root = inflate(context, R.layout.custom_view_roulette, this)
        btnStart = root.findViewById(R.id.btnStart)
        btnReset = root.findViewById(R.id.btnReset)
        image = root.findViewById(R.id.image)
        customRoulette = root.findViewById(R.id.customRoulette)
        drawText = root.findViewById(R.id.drawText)
        btnStart.setOnClickListener { startRotationAnimation() }
        btnReset.setOnClickListener {
            Glide.with(context).clear(image)
            drawText.colorAndText = null
            drawText.invalidate()
        }
    }

    private fun startRotationAnimation() {
        oldAngle = newAngle % 360
        newAngle = (Random.nextInt(0..3600) + 720)
        val objectAnim = ObjectAnimator.ofFloat(
            customRoulette,
            "rotation",
            oldAngle.toFloat(),
            newAngle.toFloat()
        )
        objectAnim.addListener(
            object : Animator.AnimatorListener {
                override fun onAnimationEnd(p0: Animator) {
                    if (getResultRotation(360 - (newAngle % 360)) == null) {
                        url = "https://loremflickr.com/320/240/dog"
                        Glide.with(context)
                            .load(url)
                            .diskCacheStrategy(DiskCacheStrategy.NONE)
                            .skipMemoryCache(true)
                            .into(image)
                    } else {
                        drawText.colorAndText = getResultRotation(360 - (newAngle % 360))
                        drawText.invalidate()
                    }
                }

                override fun onAnimationStart(p0: Animator) {}
                override fun onAnimationCancel(p0: Animator) {}
                override fun onAnimationRepeat(p0: Animator) {}
            }
        )
        objectAnim.duration = 1000
        objectAnim.start()
    }

    private fun getResultRotation(newAngle: Int): Pair<Int, String>? {
        var textColor: Pair<Int, String>? = null
        var positionX = 1
        var positionY = 3
        for (i in 0..6) {
            if (newAngle >= halfAngle * positionX && newAngle < halfAngle * positionY) {
                if (i % 2 == 0) {
                    image.visibility = View.VISIBLE
                    textColor = null
                } else {
                    image.visibility = View.GONE
                    textColor = listString[i]
                }
            }
            positionX += 2
            positionY += 2
        }
        return textColor
    }
}