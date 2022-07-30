package com.aslansari.bitcointicker.coin.data.remote

import com.google.gson.annotations.SerializedName

data class CoinImage(
    @SerializedName("thumb")
    val thumbNailUrl: String?,
    @SerializedName("small")
    val smallImageUrl : String?,
    @SerializedName("large")
    val largeImageUrl: String?,
)