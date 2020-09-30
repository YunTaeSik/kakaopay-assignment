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
import com.yts.ytscleanarchitecture.extension.priceDived
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

    inner class BookViewHolder(var binding: ItemBookBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Book?) {
            data?.let { book ->
                binding.book = book
                binding.position = bindingAdapterPosition

                setTextPrice(book.price ?: 0)
                setTextAuthors(book.authors)
                setGotoDetailBook(book)
                binding.executePendingBindings()
            }
        }

        private fun setTextPrice(price: Int) {
            binding.textPrice.priceDived(price)
        }

        private fun setTextAuthors(authors: Array<String>?) {
            binding.textAuthors.apply {
                text = authors?.toList().toString()
            }
        }


        private fun setGotoDetailBook(book: Book) {
            binding.root.setOnClickListener {
                booksAdapterListener.gotoDetailBook(
                    book, bindingAdapterPosition,
                    FragmentNavigatorExtras(
                        binding.layoutRoot to TransitionName.BOOKS_ITEM_LAYOUT + bindingAdapterPosition
                    )
                )
            }
        }
    }

}