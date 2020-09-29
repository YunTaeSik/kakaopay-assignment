package com.yts.ytscleanarchitecture.presentation.ui.search

import android.os.Bundle
import androidx.navigation.fragment.findNavController
import com.yts.ytscleanarchitecture.BR
import com.yts.ytscleanarchitecture.R
import com.yts.ytscleanarchitecture.databinding.FragmentSearchBinding
import com.yts.ytscleanarchitecture.presentation.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_search.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class SearchFragment : BaseFragment<FragmentSearchBinding>() {
    private val searchViewModel: SearchViewModel by sharedViewModel()

    override fun onLayoutId(): Int = R.layout.fragment_search

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSharedElementTransition()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        addBindingVariable(BR.searchViewModel, searchViewModel)
        btn_back.setOnClickListener {
            searchViewModel.setQuery(text_search.text.toString())
            findNavController().navigateUp()
        }
    }


    override fun observer() {
    }
}