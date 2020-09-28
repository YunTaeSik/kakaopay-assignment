package com.yts.ytscleanarchitecture.presentation.ui.books

import android.os.Bundle
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.yts.ytscleanarchitecture.BR
import com.yts.ytscleanarchitecture.R
import com.yts.ytscleanarchitecture.databinding.FragmentBooksBinding
import com.yts.ytscleanarchitecture.extension.visible
import com.yts.ytscleanarchitecture.presentation.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_books.*
import kotlinx.coroutines.delay
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class BooksFragment : BaseFragment<FragmentBooksBinding>(),
    MotionLayout.TransitionListener {
    private val model: BooksViewModel by sharedViewModel()
    private val booksAdapter: BooksAdapter by inject()

    override fun onLayoutId(): Int {
        return R.layout.fragment_books
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSharedElementTransition()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        addBindingVariable(BR.booksViewModel, model)
        layout_root.setTransitionListener(this)
        setBookAdapter()
        model.getBooks("미움")
    }

    private fun setBookAdapter() {
        // list_book.layoutManager = StaggeredGridLayoutManager(3, RecyclerView.VERTICAL)
        list_book.layoutManager = LinearLayoutManager(context)
        list_book.adapter = booksAdapter
    }

    override fun observer() {
        model.query.observe(this, {
            model.getBooks(it)
        })
        model.books.observe(this, {
            text_empty.visible(false)
            lifecycleScope.launchWhenCreated {
                booksAdapter.submitData(it)
                text_empty.visible(booksAdapter.itemCount <= 0)

            }
        })
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