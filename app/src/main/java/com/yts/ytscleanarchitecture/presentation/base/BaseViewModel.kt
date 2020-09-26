package com.yts.ytscleanarchitecture.presentation.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.yts.ytscleanarchitecture.R
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.net.ConnectException
import java.net.SocketTimeoutException

abstract class BaseViewModel : ViewModel() {
    private val compositeDisposable = CompositeDisposable()

    protected var _isLoading = MutableLiveData<Boolean>()
    protected var _toastMessageId = MutableLiveData<Int>()

    val isLoading: LiveData<Boolean> get() = _isLoading
    val toastMessageId: LiveData<Int> get() = _toastMessageId

    fun addDisposable(disposable: Disposable) {
        compositeDisposable.add(disposable)
    }

    fun <T> addDisposable(
        observable: Observable<T>,
        onSuccess: ((t: T) -> Unit),
        onError: (Throwable) -> Unit
    ) {
        val disposable = observable
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                _isLoading.postValue(true)
            }.doAfterNext {
                _isLoading.postValue(false)
            }.doAfterTerminate {
                _isLoading.postValue(false)
            }.subscribe(onSuccess, onError)

        disposable?.let {
            compositeDisposable.add(it)
        }
    }

    private fun clear() {
        compositeDisposable.clear()
    }

    protected fun commonError(throwable: Throwable) {
        throwable.printStackTrace()
        if (throwable is SocketTimeoutException || throwable is ConnectException) {
            _toastMessageId.postValue(R.string.error_socket_timeout)
        } else {
            _toastMessageId.postValue(R.string.error_default)
        }
    }

    //해당 Activity onDestory 다음 호출
    override fun onCleared() {
        clear()
        super.onCleared()
    }
}