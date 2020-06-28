package com.yts.ytscleanarchitecture.extension

import android.os.Build
import android.util.Log
import android.view.View
import android.view.ViewAnimationUtils
import android.widget.ProgressBar
import android.widget.TextView
import androidx.interpolator.view.animation.FastOutSlowInInterpolator
import kotlin.math.hypot

fun View.visible(value: Boolean) {
    if (value) {
        this.visibility = View.VISIBLE
    } else {
        this.visibility = View.GONE
    }
}

fun TextView.startCircularRevealAnimation() {
    this.post(Runnable {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            try {
                val width = this.textSize * this.text.length
                val height = this.textSize

                val startRadius = 0.0f
                val endRadius = hypot(width.toDouble(), height.toDouble())

                Log.e("width", width.toString())
                Log.e("height", height.toString())
                Log.e("startRadius", startRadius.toString())
                Log.e("endRadius", endRadius.toString())

                val animator = ViewAnimationUtils.createCircularReveal(
                    this,
                    this.left,
                    this.top,
                    startRadius,
                    endRadius.toFloat()
                )
                animator.interpolator = FastOutSlowInInterpolator()
                animator.duration = 750
                this.visibility = View.VISIBLE
                animator.start()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    })
}


fun ProgressBar.showLoading(value: Boolean) {
    val visible = if (value) {
        View.VISIBLE
    } else {
        View.GONE
    }
    this.visibility = visible
}
