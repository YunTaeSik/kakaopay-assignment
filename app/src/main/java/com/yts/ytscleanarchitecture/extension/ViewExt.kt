package com.yts.ytscleanarchitecture.extension

import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import com.yts.ytscleanarchitecture.R

fun View.visible(value: Boolean) {
    val visible = if (value) {
        View.VISIBLE
    } else {
        View.GONE
    }
    visibility = visible

}

fun TextView.price(price: Int? = 0) {
    text = context.getString(R.string.book_price, ((price ?: 0)).toString())
}

fun TextView.priceDived(price: Int? = 0) {
    text = context.getString(R.string.book_price, ((price?.toFloat() ?: 0.0f) / 1000.0f).toString())
}


fun ProgressBar.showLoading(value: Boolean) {
    val visible = if (value) {
        View.VISIBLE
    } else {
        View.GONE
    }
    this.visibility = visible
}
