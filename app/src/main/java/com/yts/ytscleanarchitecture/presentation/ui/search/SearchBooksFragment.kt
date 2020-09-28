package com.yts.ytscleanarchitecture.presentation.ui.search

import android.os.Bundle
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.lifecycle.lifecycleScope
import com.yts.ytscleanarchitecture.BR
import com.yts.ytscleanarchitecture.R
import com.yts.ytscleanarchitecture.databinding.FragmentSearchBooksBinding
import com.yts.ytscleanarchitecture.presentation.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_search_books.*
import kotlinx.coroutines.delay
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class SearchBooksFragment : BaseFragment<FragmentSearchBooksBinding>(),
    MotionLayout.TransitionListener {
    private val model: SearchBooksViewModel by sharedViewModel()

    override fun onLayoutId(): Int {
        return R.layout.fragment_search_books
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSharedElementTransition()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        addBindingVariable(BR.searchBooksViewModel, model)
        layout_root.setTransitionListener(this)
    }

    override fun observer() {
    }

    override fun onTransitionStarted(motionLayout: MotionLayout?, startId: Int, endId: Int) {
    }

    override fun onTransitionChange(
        motionLayout: MotionLayout?,
        startId: Int,
        endId: Int,
        progress: Float
    ) {
    }

    override fun onTransitionCompleted(motionLayout: MotionLayout?, currentId: Int) {
        if (currentId == R.id.introduce) {
            lifecycleScope.launchWhenCreated {
                delay(500)
                layout_root.transitionToStart()
            }
        }
    }

    override fun onTransitionTrigger(
        motionLayout: MotionLayout?,
        triggerId: Int,
        positive: Boolean,
        progress: Float
    ) {
    }

}