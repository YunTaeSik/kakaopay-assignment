package com.yts.ytscleanarchitecture.presentation.ui.bookdetail

import android.os.Bundle
import com.yts.ytscleanarchitecture.R
import com.yts.ytscleanarchitecture.BR
import com.yts.ytscleanarchitecture.databinding.FragmentBookDetailBinding
import com.yts.ytscleanarchitecture.presentation.base.BaseFragment
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class BookDetailFragment : BaseFragment<FragmentBookDetailBinding>() {
    private val bookDetailViewModel: BookDetailViewModel by sharedViewModel()

    override fun onLayoutId(): Int = R.layout.fragment_book_detail



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSharedElementTransition()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        addBindingVariable(BR.bookDetailViewModel, bookDetailViewModel)

    }

    override fun observer() {
        bookDetailViewModel.position.observe(this, {
            addBindingVariable(BR.position, it)
        })
    }


}