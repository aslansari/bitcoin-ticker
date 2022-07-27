package com.aslansari.bitcointicker.coin.data.remote

import com.aslansari.bitcointicker.coin.data.CoinDTO
import javax.inject.Inject

class CoinRemoteDataSource @Inject constructor(
    private val coinService: CoinService
) {

    suspend fun getCoinList(): List<CoinDTO> {
        val response = coinService.getCoinList(false)
        return response.coinList
    }
}