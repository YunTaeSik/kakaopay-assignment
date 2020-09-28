package com.yts.ytscleanarchitecture.presentation.ui.search

import android.os.Bundle
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.constraintlayout.motion.widget.TransitionAdapter
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import com.yts.ytscleanarchitecture.R
import com.yts.ytscleanarchitecture.databinding.FragmentSearchIntroBinding
import com.yts.ytscleanarchitecture.presentation.base.BaseFragment
import com.yts.ytscleanarchitecture.utils.AnimationDuration
import com.yts.ytscleanarchitecture.utils.TransitionName
import kotlinx.android.synthetic.main.fragment_search_intro.*
import kotlinx.coroutines.delay

class SearchIntroFragment : BaseFragment<FragmentSearchIntroBinding>() {
    override fun onLayoutId(): Int {
        return R.layout.fragment_search_intro
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        startTransitionEnd()
    }

    private fun startTransitionEnd() {
        lifecycleScope.launchWhenCreated {
            layout_root.transitionToEnd()
            delay(AnimationDuration.LARGE_COLLAPSING)
            startNextFragment()
        }
    }

    private fun startNextFragment() {
        findNavController().navigate(
            R.id.action_introFragment_to_searchBooksFragment,
            null,
            null,
            FragmentNavigatorExtras(text_title to TransitionName.SEARCH_HEADER_TITLE)
        )
    }


    override fun observer() {
    }


}