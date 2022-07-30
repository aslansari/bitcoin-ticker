package com.aslansari.bitcointicker.coin.data.remote

import com.google.gson.annotations.SerializedName

data class CoinDetailsResponse(
    val id: String,
    val symbol: String,
    val name: String,
    @SerializedName("hashing_algorithm")
    val hashingAlgorithm: String = "",
    val description: CoinDescription,
    @SerializedName("market_data")
    val marketData: MarketData,
    val image: CoinImage?,
)