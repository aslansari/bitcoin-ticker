package com.aslansari.bitcointicker.coin.ui

sealed class CoinDetailsUIState {
    object Loading : CoinDetailsUIState()
    object Error : CoinDetailsUIState()
    data class Result(
        val id: String,
        val name: String,
        val symbol: String,
        val hashAlgorithm: String?,
        val description: String?,
        val priceUSD: Double,
        val roiLastDay: Double,
        val imageUrl: String?,
    ) : CoinDetailsUIState()
}