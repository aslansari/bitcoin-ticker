package com.aslansari.bitcointicker.coin.data

import com.aslansari.bitcointicker.coin.data.local.CoinLocalDataSource
import com.aslansari.bitcointicker.coin.data.remote.CoinRemoteDataSource
import javax.inject.Inject

class CoinRepository @Inject constructor(
    private val coinRemoteDataSource: CoinRemoteDataSource,
    private val coinLocalDataSource: CoinLocalDataSource,
) {

    suspend fun getCoinList(): List<CoinDTO> {
        // todo use local data source as source of truth
        return coinRemoteDataSource.getCoinList()
    }
}