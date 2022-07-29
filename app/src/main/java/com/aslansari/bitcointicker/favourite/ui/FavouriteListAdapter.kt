package com.aslansari.bitcointicker.favourite.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.aslansari.bitcointicker.databinding.FavouriteListItemBinding

class FavouriteListAdapter :
    ListAdapter<FavouriteCoin, FavouriteListAdapter.FavouriteItemHolder>(FavouriteDiffUtil) {

    var itemClickListener: ((FavouriteCoin) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavouriteItemHolder {
        val binding = FavouriteListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val holder = FavouriteItemHolder(binding)
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

    override fun onBindViewHolder(holder: FavouriteItemHolder, position: Int) {
        val assetItem = getItem(position)
        assetItem?.let {
            holder.bind(it)
        }
    }

    class FavouriteItemHolder(private val binding: FavouriteListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: FavouriteCoin) {
            binding.textFieldCoinSymbol.text = item.symbol
        }
    }

    object FavouriteDiffUtil : DiffUtil.ItemCallback<FavouriteCoin>() {
        override fun areItemsTheSame(oldItem: FavouriteCoin, newItem: FavouriteCoin): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: FavouriteCoin, newItem: FavouriteCoin): Boolean {
            return oldItem == newItem
        }

    }
}
