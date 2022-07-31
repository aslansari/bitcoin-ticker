package com.aslansari.bitcointicker.coin.domain

import com.aslansari.bitcointicker.favourite.ui.FavouriteCoin

data class PriceChange(
    val favouriteCoin: FavouriteCoin,
    val isUp: Boolean,
    val newPrice: Double,
)