package com.yts.ytscleanarchitecture.presentation.ui.books

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.yts.domain.entity.Book
import com.yts.ytscleanarchitecture.BR
import com.yts.ytscleanarchitecture.R
import com.yts.ytscleanarchitecture.databinding.FragmentBooksBinding
import com.yts.ytscleanarchitecture.extension.visible
import com.yts.ytscleanarchitecture.presentation.base.BaseFragment
import com.yts.ytscleanarchitecture.presentation.ui.search.SearchViewModel
import com.yts.ytscleanarchitecture.utils.TransitionName
import kotlinx.android.synthetic.main.fragment_books.*
import kotlinx.coroutines.delay
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class BooksFragment : BaseFragment<FragmentBooksBinding>(),
    MotionLayout.TransitionListener, OnBooksAdapterListener {
    private val booksViewModel: BooksViewModel by sharedViewModel()
    private val searchViewModel: SearchViewModel by sharedViewModel()

    private val booksAdapter: BooksAdapter by inject()

    override fun onLayoutId(): Int = R.layout.fragment_books

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHoldExitTransition()
        setSharedElementTransition()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        addBindingVariable(BR.booksViewModel, booksViewModel)
        setLayoutTransitionListener()
        setBookAdapter()
        setGotoSearch()
        //  booksViewModel.getBooks("미움")
    }

    private fun setLayoutTransitionListener() {
        layout_root.setTransitionListener(this)

        text_title.setOnClickListener {
            layout_root.setTransition(R.id.intro_to_result)
            layout_root.transitionToEnd()
        }
    }

    private fun setBookAdapter() {
        booksAdapter.setOnBooksAdapterListener(this)
        list_book.layoutManager = LinearLayoutManager(context)
        list_book.adapter = booksAdapter
    }

    private fun setGotoSearch() {
        text_search.setOnClickListener {
            gotoSearch()
        }
    }

    override fun observer() {
        searchViewModel.query.observe(this, {
            text_search.text = it
            booksViewModel.getBooks(it)
        })
        booksViewModel.isLoading.observe(this, {
            loading.visible(it)
        })

        booksViewModel.books.observe(this, {
            lifecycleScope.launchWhenCreated {
                booksAdapter.submitData(it)
                text_empty.visible(booksAdapter.itemCount <= 0)
            }
        })
    }

    private fun gotoSearch() {
        findNavController().navigate(
            R.id.action_searchBooksFragment_to_searchFragment, null, null,
            FragmentNavigatorExtras(
                layout_search to TransitionName.BOOKS_SEARCH_LAYOUT
            )
        )
    }

    override fun gotoDetailBook(book: Book, position: Int) {
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
        } else if (currentId == R.id.search_base) {
            layout_root.setTransition(R.id.search_base_to_drag)
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