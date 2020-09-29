package com.yts.ytscleanarchitecture.presentation.ui.search

import android.os.Bundle
import com.yts.ytscleanarchitecture.R
import com.yts.ytscleanarchitecture.databinding.FragmentSearchBinding
import com.yts.ytscleanarchitecture.presentation.base.BaseFragment
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class SearchFragment : BaseFragment<FragmentSearchBinding>() {
    private val searchViewModel: SearchViewModel by sharedViewModel()

    override fun onLayoutId(): Int = R.layout.fragment_search

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSharedElementTransition()
    }


    override fun observer() {
    }
}