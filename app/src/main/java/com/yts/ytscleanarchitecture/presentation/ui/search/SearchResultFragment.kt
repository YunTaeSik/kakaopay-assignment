package com.yts.ytscleanarchitecture.presentation.ui.search

import android.os.Bundle
import android.util.SparseArray
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.yts.ytscleanarchitecture.BR
import com.yts.ytscleanarchitecture.R
import com.yts.ytscleanarchitecture.databinding.FragmentSearchResultBinding
import com.yts.ytscleanarchitecture.presentation.base.BaseFragment
import com.yts.ytscleanarchitecture.presentation.ui.filter.FilterAdapter
import com.yts.ytscleanarchitecture.presentation.ui.filter.FilterItemClickListener
import kotlinx.android.synthetic.main.fragment_search_result.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class SearchResultFragment : BaseFragment<FragmentSearchResultBinding>(),
    FilterItemClickListener {
    private val filterAdapter: FilterAdapter by inject()
    private val searchAdapter: SearchAdapter by inject()

    private val model: SearchViewModel by sharedViewModel()

    private val firstPosition = 0
    private val searchAdapterSpanCount = 3

    companion object {
        fun newInstance(): SearchResultFragment {
            val args = Bundle()
            val fragment = SearchResultFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onLayoutId(): Int {
        return R.layout.fragment_search_result
    }

    override fun setupViewModel(): SparseArray<ViewModel> {
        val setupViewModel = SparseArray<ViewModel>()
        setupViewModel.put(BR.model, model)
        return setupViewModel
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        settingFilterList()
        settingSearchList()
    }


    private fun settingFilterList() {
        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        list_filter.layoutManager = layoutManager
        filterAdapter.setFilterItemClickListener(this)
        list_filter.adapter = filterAdapter
    }

    private fun settingSearchList() {
        val layoutManager = GridLayoutManager(context, searchAdapterSpanCount)
        list_search.layoutManager = layoutManager
        list_search.adapter = searchAdapter

        list_search.addOnScrollListener(searchScrollListener)
    }

    //로드 모어
    private val searchScrollListener: RecyclerView.OnScrollListener =
        object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                model.loadMore((recyclerView.layoutManager as GridLayoutManager).findLastCompletelyVisibleItemPosition())
            }
        }

    override fun observer() {
        model.filter.observe(this, Observer {
            list_search.smoothScrollToPosition(firstPosition)
        })

        model.filterHashSet.observe(this, Observer { filterHashSet ->
            filterAdapter.submitList(ArrayList<String>(filterHashSet))
        })

        model.documentFilterList.observe(this, Observer {
            searchAdapter.submitList(it?.toMutableList())
        })
    }

    //필터 아이템 클릭 이벤트 리스너
    override fun onFilterItemClick(filter: String) {
        model.clickFilterItem(filter)
    }


    override fun onDestroyView() {
        list_search.removeOnScrollListener(searchScrollListener)
        list_filter.adapter = null
        list_search.adapter = null
        super.onDestroyView()
    }

}