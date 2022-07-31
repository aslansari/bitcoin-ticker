package com.aslansari.bitcointicker.coin.ui

sealed class FetchIntervalUIState {
    object Loading : FetchIntervalUIState()
    object Error : FetchIntervalUIState()
    data class Result(
        val timeInterval: Long,
    ) : FetchIntervalUIState()
}
