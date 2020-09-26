package com.yts.ytscleanarchitecture.presentation.ui.intro

import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.yts.ytscleanarchitecture.R
import com.yts.ytscleanarchitecture.databinding.FragmentIntroBinding
import com.yts.ytscleanarchitecture.presentation.base.BaseFragment
import com.yts.ytscleanarchitecture.utils.AnimationDuration
import kotlinx.android.synthetic.main.fragment_intro.*
import kotlinx.coroutines.delay

class IntroFragment : BaseFragment<FragmentIntroBinding>() {
    override fun onLayoutId(): Int {
        return R.layout.fragment_intro
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        lifecycleScope.launchWhenCreated {
            delay(AnimationDuration.SMALL)
            layout_root.transitionToEnd()
            delay(AnimationDuration.LARGE_EXPANDING)
        }
    }

    override fun observer() {
    }


}