package com.yts.ytscleanarchitecture.presentation.ui.search

import androidx.lifecycle.MutableLiveData
import com.yts.ytscleanarchitecture.presentation.base.BaseViewModel

class SearchViewModel : BaseViewModel() {
    var query = MutableLiveData<String>() //two way binding
}