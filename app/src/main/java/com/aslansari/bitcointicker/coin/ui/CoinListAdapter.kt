package com.aslansari.bitcointicker.coin.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.aslansari.bitcointicker.databinding.CoinListItemBinding

class CoinListAdapter: ListAdapter<CoinListItem, CoinListAdapter.CoinItemViewHolder>(CoinDiffUtil) {

    var itemClickListener: ((CoinListItem) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoinItemViewHolder {
        val binding = CoinListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val holder = CoinItemViewHolder(binding)
        holder.itemView.setOnClickListener {
            val adapterPos = holder.adapterPosition
            if (adapterPos != RecyclerView.NO_POSITION) {
                itemClickListener?.let { listener ->
                    val item = getItem(adapterPos)
                    listener(item)
                }
            }
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
