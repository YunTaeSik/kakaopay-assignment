package com.yts.ytscleanarchitecture.utils

import androidx.recyclerview.widget.DiffUtil
import com.google.gson.Gson

class CommonDiffUtil<B> : DiffUtil.ItemCallback<B>() {
    override fun areItemsTheSame(oldItem: B, newItem: B): Boolean { //객체가 같은지 여부
        return oldItem == newItem
    }
    override fun areContentsTheSame(oldItem: B, newItem: B): Boolean { //객체 안에 내용물이 같은지에 대한 여부
        return (Gson().toJson(oldItem)) == (Gson().toJson(newItem))
    }


}