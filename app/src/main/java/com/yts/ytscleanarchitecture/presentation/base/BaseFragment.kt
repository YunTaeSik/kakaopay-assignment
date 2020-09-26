package com.yts.ytscleanarchitecture.presentation.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.doOnPreDraw
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import com.yts.ytscleanarchitecture.BR

abstract class BaseFragment<B : ViewDataBinding> : Fragment() {
    private lateinit var binding: B

    protected abstract fun onLayoutId(): Int
    protected abstract fun setupViewModel(): ViewModel?
    protected abstract fun observer()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, onLayoutId(), container, false)
        binding.setVariable(BR.model, setupViewModel())
        binding.lifecycleOwner = this
        return binding.root
    }
    protected fun addBindingVariable(variableId: Int, value: Any) {
        binding.setVariable(variableId, value)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        postponeEnterTransition()
        view?.doOnPreDraw {
            startPostponedEnterTransition()
        }
        observer()
    }

}