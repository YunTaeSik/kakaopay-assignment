package com.yts.ytscleanarchitecture.presentation.ui.books

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.yts.domain.entity.Book
import com.yts.ytscleanarchitecture.R
import com.yts.ytscleanarchitecture.databinding.ItemBookBinding
import com.yts.ytscleanarchitecture.utils.CommonDiffUtil
import com.yts.ytscleanarchitecture.utils.TransitionName

class BooksAdapter(val booksAdapterListener: OnBooksAdapterListener) :
    PagingDataAdapter<Book, BooksAdapter.BookViewHolder>(CommonDiffUtil()) {

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

    override fun onBindViewHolder(
        holder: BookViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        if (payloads.isNotEmpty()) {
            val item = getItem(position)
            holder.bind(item)
        } else {
            onBindViewHolder(holder, position)
        }
    }


    inner class BookViewHolder(var binding: ItemBookBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Book?) {
            data?.let { book ->
                binding.book = book
                binding.position = bindingAdapterPosition

                binding.textPrice.apply {
                    book.price?.let { price ->
                        text = "₩" + (price.toFloat() / 1000.0f)
                    }

                }
                binding.textAuthors.apply {
                    text = book.authors?.toList().toString()
                }

                binding.root.setOnClickListener {
                    booksAdapterListener?.gotoDetailBook(
                        data, bindingAdapterPosition,
                        FragmentNavigatorExtras(
                            binding.layoutRoot to TransitionName.BOOKS_ITEM_LAYOUT + bindingAdapterPosition
                        )
                    )
                }
                binding.executePendingBindings()
            }
        }
    }

}