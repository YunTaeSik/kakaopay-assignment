package com.yts.ytscleanarchitecture.extension

import android.os.Build
import android.util.Log
import android.view.View
import android.view.ViewAnimationUtils
import android.widget.ProgressBar
import android.widget.TextView
import androidx.interpolator.view.animation.FastOutSlowInInterpolator
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_intro.*
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.math.hypot

fun View.visible(value: Boolean) {
    if (value) {
        this.visibility = View.VISIBLE
    } else {
        this.visibility = View.GONE
    }
}

fun TextView.startCircularRevealAnimation(): Disposable {
    return startCircularRevealAnimation(null)
}

fun TextView.startCircularRevealAnimation(text: String?): Disposable {
    this.visibility = View.GONE

    return Observable.timer(250, TimeUnit.MILLISECONDS)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe({
            if (!text.isNullOrEmpty()) {
                this.text = text
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                try {
                    val width = this.textSize * this.text.length
                    val height = this.textSize

                    val startRadius = 0.0f
                    val endRadius = hypot(width.toDouble(), height.toDouble())

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
                    throw e
                }
            }
        }, {
            it.printStackTrace()
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
