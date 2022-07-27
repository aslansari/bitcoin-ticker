package com.aslansari.bitcointicker.coin.data.remote

import com.aslansari.bitcointicker.coin.data.CoinDTO
import javax.inject.Inject

class CoinRemoteDataSource @Inject constructor(
    private val coinService: CoinService
) {

    suspend fun getCoinList(): List<CoinDTO> {
        return coinService.getCoinList(false)
    }
}