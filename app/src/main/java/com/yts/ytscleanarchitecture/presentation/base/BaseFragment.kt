package com.yts.ytscleanarchitecture.presentation.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.google.android.material.transition.Hold
import com.google.android.material.transition.MaterialContainerTransform
import com.yts.ytscleanarchitecture.utils.AnimationDuration

abstract class BaseFragment<B : ViewDataBinding> : Fragment() {
    private lateinit var binding: B

    protected abstract fun onLayoutId(): Int
    protected abstract fun observer()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, onLayoutId(), container, false)
        binding.lifecycleOwner = this
        return binding.root
    }

    protected fun addBindingVariable(variableId: Int, value: Any) {
        binding.setVariable(variableId, value)
    }

    protected fun setHoldExitTransition() {
        exitTransition = Hold().apply {
            duration = AnimationDuration.SMALL
        }
    }

    protected fun setSharedElementTransition() {
        sharedElementEnterTransition = MaterialContainerTransform().apply {
            duration = AnimationDuration.MEDIUM_EXPANDING
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        observer()
    }

}