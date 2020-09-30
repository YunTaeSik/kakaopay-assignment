package com.yts.ytscleanarchitecture.presentation.ui.bookdetail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.navigation.fragment.findNavController
import com.yts.ytscleanarchitecture.R
import com.yts.ytscleanarchitecture.BR
import com.yts.ytscleanarchitecture.databinding.FragmentBookDetailBinding
import com.yts.ytscleanarchitecture.extension.price
import com.yts.ytscleanarchitecture.extension.visible
import com.yts.ytscleanarchitecture.presentation.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_book_detail.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class BookDetailFragment : BaseFragment<FragmentBookDetailBinding>() {
    private val bookDetailViewModel: BookDetailViewModel by sharedViewModel()

    override fun onLayoutId(): Int = R.layout.fragment_book_detail


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSharedElementTransition()

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        addBindingVariable(BR.bookDetailViewModel, bookDetailViewModel)

        setOnClickListener()
    }

    override fun observer() {
        bookDetailViewModel.position.observe(viewLifecycleOwner, {
            addBindingVariable(BR.position, it)
        })
        bookDetailViewModel.book.observe(viewLifecycleOwner, { book ->
            setAuthors(book.authors)
            setTranslators(book.translators)
            setPrice(book.sale_price, book.price)
        })
    }

    private fun setAuthors(authors: Array<String>?) {
        text_authors.text = authors?.toList().toString().replace("[", "").replace("]", "")
    }

    private fun setTranslators(translators: Array<String>?) {
        chip_translators.visible((translators?.size ?: 0) > 0)
        text_translators.visible((translators?.size ?: 0) > 0)
        text_translators.text = translators?.toList().toString().replace("[", "").replace("]", "")
    }

    private fun setPrice(sale_price: Int?, price: Int?) {
        var finalPrice = price ?: 0
        sale_price?.let {
            if (it > 0) {
                finalPrice = it
            }
        }
        text_price.price(finalPrice)
    }

    private fun setOnClickListener() {
        btn_back.setOnClickListener {
            gotoBack()
        }
        btn_detail.setOnClickListener {
            gotoSeeDetail()
        }
    }

    private fun gotoBack() {
        findNavController().navigateUp()
    }

    private fun gotoSeeDetail() {
        bookDetailViewModel.book.value?.let { book ->
            book.url?.let { url ->
                val webIntent = Intent(Intent.ACTION_VIEW)
                webIntent.data = Uri.parse(url)
                startActivity(webIntent)
            }

        }
    }
}