package com.aslansari.bitcointicker.coin.data.remote

import com.google.gson.annotations.SerializedName

data class CoinDescription(
    @SerializedName("en")
    val englishDescription: String
)