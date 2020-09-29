package com.yts.ytscleanarchitecture.extension

import android.view.View
import android.widget.ProgressBar

fun View.visible(value: Boolean) {
    val visible = if (value) {
        View.VISIBLE
    } else {
        View.GONE
    }
    if (this.visibility != visible) {
        this.visibility = visibility
    }
}


fun ProgressBar.showLoading(value: Boolean) {
    val visible = if (value) {
        View.VISIBLE
    } else {
        View.GONE
    }
    this.visibility = visible
}
