package com.yts.ytscleanarchitecture.presentation.bindingAdapter

import android.widget.ImageView
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
                .error(R.drawable.img_error)
                .thumbnail(0.3f)
                .into(view)

        }
    }
}