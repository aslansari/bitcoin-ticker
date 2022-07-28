package com.aslansari.bitcointicker.coin.data

import com.aslansari.bitcointicker.coin.data.local.CoinLocalDataSource
import com.aslansari.bitcointicker.coin.data.remote.CoinRemoteDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import kotlin.time.Duration.Companion.minutes

class CoinRepository @Inject constructor(
    private val coinRemoteDataSource: CoinRemoteDataSource,
    private val coinLocalDataSource: CoinLocalDataSource,
) {

    private val EXPIRE_DURATION = 3.minutes

    suspend fun getCoinList(): Flow<List<CoinDTO>> {
        if (!coinLocalDataSource.hasCoins()) {
            val list = coinRemoteDataSource.getCoinList()
            coinLocalDataSource.addCoins(list)
        }
        return coinLocalDataSource.getCoinList()
    }
}