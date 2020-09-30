package com.yts.ytscleanarchitecture.presentation.base

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.doOnPreDraw
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.transition.*
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
            duration = AnimationDuration.MEDIUM_EXPANDING
        }
    }

    protected fun setSharedElementTransition() {
        sharedElementEnterTransition =
            TransitionSet().addTransition(ChangeBounds()).addTransition(ChangeTransform())
                .addTransition(ChangeImageTransform()).addTransition(MaterialContainerTransform())
        sharedElementReturnTransition =
            TransitionSet().addTransition(ChangeBounds()).addTransition(ChangeTransform())
                .addTransition(ChangeImageTransform()).addTransition(MaterialContainerTransform())
        /*  sharedElementEnterTransition = MaterialContainerTransform().apply {
              duration = AnimationDuration.MEDIUM_EXPANDING
              scrimColor = ContextCompat.getColor(requireContext(), android.R.color.transparent)
          }
          sharedElementReturnTransition = MaterialContainerTransform().apply {
              duration = AnimationDuration.MEDIUM_COLLAPSING
              scrimColor = ContextCompat.getColor(requireContext(), android.R.color.transparent)
          }*/
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