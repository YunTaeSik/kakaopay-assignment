package com.yts.ytscleanarchitecture.presentation.ui.books

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.navigation.*
import androidx.navigation.fragment.FragmentNavigator
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.yts.domain.entity.Book
import com.yts.ytscleanarchitecture.BR
import com.yts.ytscleanarchitecture.R
import com.yts.ytscleanarchitecture.databinding.FragmentBooksBinding
import com.yts.ytscleanarchitecture.extension.visible
import com.yts.ytscleanarchitecture.presentation.base.BaseFragment
import com.yts.ytscleanarchitecture.presentation.ui.bookdetail.BookDetailViewModel
import kotlinx.android.synthetic.main.fragment_books.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.core.parameter.parametersOf
import java.lang.Exception

class BooksFragment : BaseFragment<FragmentBooksBinding>(), OnBooksAdapterListener {
    private val booksViewModel: BooksViewModel by sharedViewModel()
    private val bookDetailViewModel: BookDetailViewModel by sharedViewModel()
    private val booksAdapter: BooksAdapter by inject() { parametersOf(this) }


    override fun onLayoutId(): Int = R.layout.fragment_books

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHoldExitTransition()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        addBindingVariable(BR.booksViewModel, booksViewModel)
        setOnDeleteBtnClick()
        setBookAdapter()
    }

    private fun setBookAdapter() {
        list_book.layoutManager = LinearLayoutManager(context)

        list_book.adapter = booksAdapter.withLoadStateFooter(
            footer = BooksPageLoadStateAdapter(booksAdapter)
        )
        lifecycleScope.launch {
            booksAdapter.loadStateFlow.collectLatest {
                if (it.refresh is LoadState.Error) {
                    val error = it.refresh as LoadState.Error
                    booksViewModel.loadError(error.error)
                }
            }
        }
    }

    override fun observer() {
        booksViewModel.isLoading.observe(viewLifecycleOwner, {
            loading?.visible(it)
        })

        booksViewModel.toastMessageId.observe(this, { toastMessageId ->
            if (toastMessageId != 0) {
                context?.let { context ->
                    Toast.makeText(context, toastMessageId, Toast.LENGTH_SHORT).show()
                    booksViewModel.initToastMessage()
                }
            }
        })

        booksViewModel.query.observe(viewLifecycleOwner, {
            btn_text_delete?.visible(it.isNotEmpty())
            booksViewModel.getBooks(it)
        })
        booksViewModel.booksType.observe(viewLifecycleOwner, { type ->
            text_empty?.visible(type == BooksFragmentType.EMPTY)
            list_book?.visible(type == BooksFragmentType.RESULT)
        })

        booksViewModel.books.observe(viewLifecycleOwner, {
            lifecycleScope.launchWhenCreated {
                booksAdapter.submitData(it)
            }
        })
    }

    private fun setOnDeleteBtnClick() {
        btn_text_delete.setOnClickListener {
            booksViewModel.initQuery()
        }
    }

    override fun gotoDetailBook(book: Book, position: Int, extras: FragmentNavigator.Extras) {
        activity?.let {
            bookDetailViewModel.setBookAndPosition(book, position)
            Navigation.findNavController(it, R.id.fragment_main).navigate(
                R.id.action_booksFragment_to_bookDetailFragment, null, null, extras
            )
        }

    }


}