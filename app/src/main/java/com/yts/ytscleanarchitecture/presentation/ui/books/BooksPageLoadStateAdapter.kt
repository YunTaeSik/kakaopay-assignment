package com.yts.ytscleanarchitecture.presentation.ui.books

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.yts.ytscleanarchitecture.R
import com.yts.ytscleanarchitecture.databinding.ItemNetworkStateBinding

class BooksPageLoadStateAdapter(
    private val adapter: BooksAdapter
) : LoadStateAdapter<BooksPageLoadStateAdapter.NetworkStateItemViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): NetworkStateItemViewHolder {
        val item = DataBindingUtil.inflate<ItemNetworkStateBinding>(
            LayoutInflater.from(parent.context),
            R.layout.item_network_state,
            parent,
            false
        )
        return NetworkStateItemViewHolder(item)
    }

    override fun onBindViewHolder(holder: NetworkStateItemViewHolder, loadState: LoadState) {
        holder.bindTo(loadState)
    }


    inner class NetworkStateItemViewHolder(var binding: ItemNetworkStateBinding) :
        RecyclerView.ViewHolder(binding.root), View.OnClickListener {

        fun bindTo(loadState: LoadState) {
            binding.retryButton.setOnClickListener(this)

            binding.progressBar.isVisible = loadState is LoadState.Loading
            binding.retryButton.isVisible = loadState is LoadState.Error
            binding.errorMsg.isVisible =
                !(loadState as? LoadState.Error)?.error?.message.isNullOrBlank()
        }

        override fun onClick(v: View?) {
            when (v) {
                binding.retryButton -> {
                    adapter.refresh()
                }
            }
        }
    }
}