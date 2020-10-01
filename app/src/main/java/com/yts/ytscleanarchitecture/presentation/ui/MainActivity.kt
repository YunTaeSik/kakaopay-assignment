package com.yts.ytscleanarchitecture.presentation.ui

import com.yts.ytscleanarchitecture.R
import com.yts.ytscleanarchitecture.databinding.ActivityMainBinding
import com.yts.ytscleanarchitecture.presentation.base.BackDoubleClickFinishActivity

class MainActivity : BackDoubleClickFinishActivity<ActivityMainBinding>() {
    override fun onLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun observer() {
    }
}