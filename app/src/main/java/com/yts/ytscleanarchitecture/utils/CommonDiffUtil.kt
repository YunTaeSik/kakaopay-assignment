package com.yts.ytscleanarchitecture.utils

import android.annotation.SuppressLint
import androidx.recyclerview.widget.DiffUtil
import com.google.gson.Gson
import com.yts.domain.entity.Book

class CommonDiffUtil<B> : DiffUtil.ItemCallback<B>() {

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: B, newItem: B): Boolean =
        oldItem == newItem

    override fun areItemsTheSame(oldItem: B, newItem: B): Boolean { // 주로 id 비교
        if (newItem is Book && oldItem is Book) {
            return oldItem.url == newItem.url
        }
        return oldItem == newItem
    }

}