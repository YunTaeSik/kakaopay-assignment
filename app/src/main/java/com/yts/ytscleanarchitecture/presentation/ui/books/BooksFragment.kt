package com.yts.ytscleanarchitecture.presentation.ui.books

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.lifecycle.asLiveData
import androidx.lifecycle.coroutineScope
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.yts.domain.entity.Book
import com.yts.ytscleanarchitecture.BR
import com.yts.ytscleanarchitecture.R
import com.yts.ytscleanarchitecture.databinding.FragmentBooksBinding
import com.yts.ytscleanarchitecture.extension.visible
import com.yts.ytscleanarchitecture.presentation.base.BaseFragment
import com.yts.ytscleanarchitecture.presentation.ui.search.SearchViewModel
import com.yts.ytscleanarchitecture.utils.AnimationDuration
import com.yts.ytscleanarchitecture.utils.TransitionName
import kotlinx.android.synthetic.main.fragment_books.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.launch
import okhttp3.Dispatcher
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
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        addBindingVariable(BR.booksViewModel, booksViewModel)
        setBookAdapter()
        setGotoSearch()
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
        searchViewModel.query.observe(viewLifecycleOwner, {

            Log.e("test", it)
            text_search.text = it
            booksViewModel.getBooks(it)
        })
        booksViewModel.isLoading.observe(viewLifecycleOwner, {
            loading.visible(it)
        })

        booksViewModel.books.observe(viewLifecycleOwner, {
            // viewLifecycleOwner.lifecycle.coroutineScope.
            lifecycleScope.launchWhenResumed {
                //delay(AnimationDuration.LARGE_COLLAPSING + 50)
                booksAdapter.submitData(it)
            }
        })
        /*      booksAdapter.loadStateFlow.asLiveData().observe(viewLifecycleOwner, {

                  lifecycleScope.launch(Dispatchers.Main) {
                      text_empty.visible(false)
                  }
              })*/
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