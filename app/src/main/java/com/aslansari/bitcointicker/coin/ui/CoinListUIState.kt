package com.aslansari.bitcointicker.coin.ui

sealed class CoinListUIState {
    object Loading: CoinListUIState()
    object Error: CoinListUIState()
    object Result: CoinListUIState()
}