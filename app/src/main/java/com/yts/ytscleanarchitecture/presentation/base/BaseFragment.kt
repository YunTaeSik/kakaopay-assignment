package com.yts.ytscleanarchitecture.presentation.base

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.transition.*
import com.yts.ytscleanarchitecture.R

abstract class BaseFragment<B : ViewDataBinding> : Fragment() {
    protected var binding: B? = null

    protected abstract fun onLayoutId(): Int
    protected abstract fun setupViewModel(): SparseArray<ViewModel>
    protected abstract fun observer()

    private val name = javaClass.simpleName

    override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.e(name, "onAttach")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.e(name, "onCreate")
        sharedElementEnterTransition = transition
        enterTransition = TransitionInflater.from(context)
            .inflateTransition(R.transition.fade_in_trans)
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.e(name, "onDestroy")
        sharedElementReturnTransition = transition
        exitTransition = TransitionInflater.from(context)
            .inflateTransition(R.transition.fade_out_trans)
    }

    private val transition: Transition
        get() {
            val set = TransitionSet()
            set.ordering = TransitionSet.ORDERING_TOGETHER
            set.addTransition(ChangeBounds())
            set.addTransition(ChangeImageTransform())
            set.addTransition(ChangeTransform())
            return set
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.e(name, "onCreateView")
        binding = DataBindingUtil.inflate(inflater, onLayoutId(), container, false)
        for (i in 0 until setupViewModel().size()) {
            binding!!.setVariable(setupViewModel().keyAt(i), setupViewModel().valueAt(i))
        }
        binding!!.lifecycleOwner = this
        return binding!!.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Log.e(name, "onActivityCreated")
        observer()
    }

    override fun onStart() {
        super.onStart()
        Log.e(name, "onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.e(name, "onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.e(name, "onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.e(name, "onStop")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.e(name, "onDestroyView")
    }

    override fun onDetach() {
        super.onDetach()
        Log.e(name, "onDetach")
    }
}