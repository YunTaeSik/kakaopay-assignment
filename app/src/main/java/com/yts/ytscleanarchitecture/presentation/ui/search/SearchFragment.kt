package com.yts.ytscleanarchitecture.presentation.ui.search

import android.content.Context.INPUT_METHOD_SERVICE
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.core.app.SharedElementCallback
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.transition.Transition
import androidx.transition.TransitionListenerAdapter
import com.google.android.material.transition.MaterialContainerTransform
import com.yts.ytscleanarchitecture.BR
import com.yts.ytscleanarchitecture.R
import com.yts.ytscleanarchitecture.databinding.FragmentSearchBinding
import com.yts.ytscleanarchitecture.presentation.base.BaseFragment
import com.yts.ytscleanarchitecture.presentation.ui.books.BooksViewModel
import com.yts.ytscleanarchitecture.utils.AnimationDuration
import kotlinx.android.synthetic.main.fragment_search.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class SearchFragment : BaseFragment<FragmentSearchBinding>() {
    private val booksViewModel: BooksViewModel by sharedViewModel()

    override fun onLayoutId(): Int = R.layout.fragment_search

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSharedElementTransition()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        addBindingVariable(BR.booksViewModel, booksViewModel)

        btn_back.setOnClickListener {
            findNavController().navigateUp()
        }

        /*   setEnterSharedElementCallback(object : SharedElementCallback() {
               override fun onSharedElementEnd(
                   sharedElementNames: MutableList<String>?,
                   sharedElements: MutableList<View>?,
                   sharedElementSnapshots: MutableList<View>?
               ) {
                   text_search.isFocusable = true
                   Log.e("test", "onSharedElementEnd")
                   super.onSharedElementEnd(sharedElementNames, sharedElements, sharedElementSnapshots)
               }

               override fun onSharedElementStart(
                   sharedElementNames: MutableList<String>?,
                   sharedElements: MutableList<View>?,
                   sharedElementSnapshots: MutableList<View>?
               ) {
                   Log.e("test", "onSharedElementStart")
                   super.onSharedElementStart(
                       sharedElementNames,
                       sharedElements,
                       sharedElementSnapshots
                   )
               }
           })*/


    }


    override fun observer() {
    }
}