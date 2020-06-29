package com.yts.ytscleanarchitecture.extension

import android.content.Context
import android.content.DialogInterface
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.yts.ytscleanarchitecture.R

/**
 * 토스트 만들기
 */
fun Context.makeToast(id: Int) {
    try {
        Toast.makeText(this, this.getString(id), Toast.LENGTH_LONG).show()
    } catch (e: Exception) {
        e.printStackTrace()
    }
}
