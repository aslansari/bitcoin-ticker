package com.aslansari.bitcointicker.coin.data

data class CoinDTO(
    val id: String,
    val symbol: String,
    val name: String,
    val platforms: HashMap<String, String>,
)
