package com.yts.ytscleanarchitecture.presentation.ui

import com.yts.ytscleanarchitecture.R
import com.yts.ytscleanarchitecture.databinding.ActivityMainBinding
import com.yts.ytscleanarchitecture.presentation.base.BaseActivity

class MainActivity : BaseActivity<ActivityMainBinding>() {
    override fun onLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun observer() {
    }
}