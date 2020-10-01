package com.yts.ytscleanarchitecture.presentation.bindingAdapter

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.yts.ytscleanarchitecture.R


object ImageBindingAdapter {
    @JvmStatic
    @BindingAdapter("srcCompat")
    fun srcCompat(view: ImageView, url: String?) {
        if (url != null) {
            Glide.with(view.context)
                .load(url)
                .centerCrop()
                .thumbnail(0.1f)
                .error(
                    ColorDrawable(ContextCompat.getColor(view.context, R.color.grayLight))
                )
                .into(view)

        }
    }
}