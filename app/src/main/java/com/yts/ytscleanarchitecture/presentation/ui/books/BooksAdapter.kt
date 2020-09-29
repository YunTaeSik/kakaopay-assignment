package com.yts.ytscleanarchitecture.presentation.ui.books

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.yts.domain.entity.Book
import com.yts.ytscleanarchitecture.R
import com.yts.ytscleanarchitecture.databinding.ItemBookBinding
import com.yts.ytscleanarchitecture.utils.CommonDiffUtil

class BooksAdapter : PagingDataAdapter<Book, BooksAdapter.BookViewHolder>(CommonDiffUtil()) {
    private var booksAdapterListener: OnBooksAdapterListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val item = DataBindingUtil.inflate<ItemBookBinding>(
            LayoutInflater.from(parent.context),
            R.layout.item_book,
            parent,
            false
        )
        return BookViewHolder(item)
    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    fun setOnBooksAdapterListener(booksAdapterListener: OnBooksAdapterListener) {
        this.booksAdapterListener = booksAdapterListener
    }

    inner class BookViewHolder(var binding: ItemBookBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Book?) {
            data?.let { book ->
                binding.book = book
                binding.textPrice.apply {
                    book.price?.let { price ->
                        text = "₩" + (price.toFloat() / 1000.0f)
                    }

                }
                binding.textAuthors.apply {
                    text = book.authors?.toList().toString()
                }

                binding.root.setOnClickListener {
                    booksAdapterListener?.gotoDetailBook(data, bindingAdapterPosition)
                }
                binding.executePendingBindings()
            }
        }
    }


}