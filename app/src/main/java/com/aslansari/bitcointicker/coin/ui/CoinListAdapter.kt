package com.aslansari.bitcointicker.coin.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.aslansari.bitcointicker.databinding.CoinListItemBinding

class CoinListAdapter: ListAdapter<CoinListItem, CoinListAdapter.CoinItemViewHolder>(CoinDiffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoinItemViewHolder {
        val binding = CoinListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val holder = CoinItemViewHolder(binding)
        holder.itemView.setOnClickListener {

        }
        return holder
    }

    override fun onBindViewHolder(holder: CoinItemViewHolder, position: Int) {
        val assetItem = getItem(position)
        assetItem?.let {
            holder.bind(it)
        }
    }

    class CoinItemViewHolder(private val binding: CoinListItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: CoinListItem) {
            binding.textFieldCoinSymbol.text = item.symbol
        }
    }

    object CoinDiffUtil: DiffUtil.ItemCallback<CoinListItem>() {
        override fun areItemsTheSame(oldItem: CoinListItem, newItem: CoinListItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: CoinListItem, newItem: CoinListItem): Boolean {
            return oldItem == newItem
        }
    }
}
