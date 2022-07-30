package com.aslansari.bitcointicker.coin.ui

sealed class CoinDetailsUIState {
    object Loading : CoinDetailsUIState()
    object Error : CoinDetailsUIState()
    data class Result(
        val coinDetails: CoinDetails
    ) : CoinDetailsUIState()
}