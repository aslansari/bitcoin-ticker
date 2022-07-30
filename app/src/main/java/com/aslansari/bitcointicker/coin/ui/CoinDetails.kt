package com.aslansari.bitcointicker.coin.ui

data class CoinDetails(
    val id: String,
    val name: String,
    val symbol: String,
    val hashAlgorithm: String?,
    val description: String?,
    val priceUSD: Double,
    val roiLastDay: Double,
    val imageUrl: String?,
)