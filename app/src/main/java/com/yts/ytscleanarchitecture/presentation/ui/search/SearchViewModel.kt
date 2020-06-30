package com.yts.ytscleanarchitecture.presentation.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.yts.domain.entity.Document
import com.yts.domain.response.SearchResponse
import com.yts.domain.usecase.search.SearchUseCase
import com.yts.ytscleanarchitecture.R
import com.yts.ytscleanarchitecture.extension.addAll
import com.yts.ytscleanarchitecture.extension.clear
import com.yts.ytscleanarchitecture.presentation.base.BaseViewModel
import com.yts.ytscleanarchitecture.utils.Const
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.collections.ArrayList
import kotlin.collections.HashSet


class SearchViewModel(private val searchUseCase: SearchUseCase) : BaseViewModel() {
    private val waitTimeForSearch = 500L
    private val firstPage = 1
    private var isEndDocumentList = false

    private var waitTimeForSearchDisposable: Disposable? = null

    private var _query = MutableLiveData<String>()
    private var _page = MutableLiveData<Int>()

    private var _filter = MutableLiveData<String>()
    private var _viewType = MutableLiveData<SearchViewType>()

    private var _documentList = MutableLiveData<List<Document>>()
    private var _documentFilterList = MutableLiveData<List<Document>>()

    private var _filterHashSet = MutableLiveData<HashSet<String>>()

    val query: LiveData<String> get() = _query
    val page: LiveData<Int> get() = _page

    val filter: LiveData<String> get() = _filter
    val viewType: LiveData<SearchViewType> get() = _viewType

    val documentList: LiveData<List<Document>> get() = _documentList
    val documentFilterList: LiveData<List<Document>> get() = _documentFilterList

    val filterHashSet: LiveData<HashSet<String>> get() = _filterHashSet

    private fun setQuery(query: String) {
        _query.value = query
        if (query.isNotEmpty()) {
            changeViewType(SearchViewType.RESULT)
        } else {
            changeViewType(SearchViewType.NONE)
        }
    }

    private fun setPage(page: Int) {
        _page.postValue(page)
    }


    private fun clearFilter() {
        _filter.postValue(null)
    }

    private fun clearFilterHashSet() {
        val filterHashSet: HashSet<String> = HashSet()
        filterHashSet.add(Const.FILTER_ALL)
        _filterHashSet.postValue(filterHashSet)
    }

    private fun setDocumentList(searchResponse: SearchResponse) {
        _documentList.addAll(searchResponse.documents!!)
    }

    private fun emptyDocumentList(searchResponse: SearchResponse): Boolean {
        return searchResponse.documents?.size == 0
    }

    private fun clearDocumentList() {
        _documentList.clear()
        _documentFilterList.clear()
    }


    private fun setDocumentFilterList(filter: String?) {
        setDocumentFilterList(filter, documentList.value)
    }

    private fun setDocumentFilterList(filter: String?, documentList: List<Document>?) {
        if (filter == null || filter == Const.FILTER_ALL) {
            _documentFilterList.postValue(documentList)
        } else {
            documentList?.let {
                val filterList: ArrayList<Document> = ArrayList()
                for (document in documentList) {
                    if (filter == document.collection?.toUpperCase()) {
                        filterList.add(document)
                    }
                }
                _documentFilterList.postValue(filterList)
            }
        }
    }

    private fun search(query: String) {
        _isLoading.postValue(false)
        setQuery(query)

        waitTimeForSearchDisposable?.dispose()
        _isLoading.postValue(true)
        waitTimeForSearchDisposable =
            Observable.timer(waitTimeForSearch, TimeUnit.MILLISECONDS)
                .subscribe({
                    _isLoading.postValue(false)
                    getImages(firstPage)
                }, {
                    it.printStackTrace()
                })
    }


    private fun getImages(page: Int) {
        if (query.value != null && query.value!!.isNotEmpty()) {
            _isLoading.postValue(true)
            addDisposable(
                searchUseCase.getImages(
                    query.value!!,
                    page
                )
                    .subscribeOn(Schedulers.io())
                    .observeOn(Schedulers.io())
                    .subscribe({
                        if (emptyDocumentList(it)) {
                            _toastMessageId.postValue(R.string.error_query_size_null_message)
                        } else if (it.meta?.total_count != _documentList.value?.size) {
                            isEndDocumentList = it.meta?.is_end!!
                            setDocumentList(it)
                        }
                        setPage(page)
                        _isLoading.postValue(false)
                    }, {
                        it.printStackTrace()
                        _toastMessageId.postValue(R.string.error_message)
                        _isLoading.postValue(false)
                    })
            )
        }
    }

    private fun setFilterHashSet(documents: List<Document>?) {
        if (documents != null) {
            val filterHashSet: HashSet<String> = HashSet()
            filterHashSet.add(Const.FILTER_ALL)
            for (document in documents) {
                if (document.collection != null) {
                    filterHashSet.add(document.collection!!.toUpperCase(Locale.getDefault()))
                }
            }
            _filterHashSet.postValue(filterHashSet)
        }
    }

    fun changeViewType(viewType: SearchViewType) {
        _viewType.postValue(viewType)
    }

    fun changeQueryText(query: String) {
        clearFilter()
        clearFilterHashSet()
        clearDocumentList()
        setPage(firstPage)
        search(query)
    }

    fun changeDocumentList(documentList: List<Document>?) {
        setDocumentFilterList(filter.value, documentList)
        setFilterHashSet(documentList)
    }

    fun clickFilterItem(filter: String) {
        _isLoading.postValue(true)
        _filter.postValue(filter)
        addDisposable(
            Observable.just(filter).observeOn(Schedulers.io())
                .subscribe({
                    _isLoading.postValue(false)
                    setDocumentFilterList(it)
                }, {
                    _toastMessageId.postValue(R.string.error_message)
                    _isLoading.postValue(false)
                })
        )
    }

    fun loadMore(findLastCompletelyVisibleItemPosition: Int): Boolean {
        val isLastVisibleItem =
            findLastCompletelyVisibleItemPosition == documentFilterList.value!!.size - 1
        if (isLastVisibleItem) {
            if (!_isLoading.value!! && !isEndDocumentList) {
                getImages(page.value!! + 1)
                return true
            }
        }
        return false
    }
}

