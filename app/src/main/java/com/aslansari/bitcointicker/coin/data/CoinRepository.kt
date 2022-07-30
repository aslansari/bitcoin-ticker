package com.aslansari.bitcointicker.coin.data

import android.util.Log
import com.aslansari.bitcointicker.coin.data.local.CoinLocalDataSource
import com.aslansari.bitcointicker.coin.data.remote.CoinDetailsResponse
import com.aslansari.bitcointicker.coin.data.remote.CoinRemoteDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import kotlin.time.Duration.Companion.minutes

class CoinRepository @Inject constructor(
    private val coinRemoteDataSource: CoinRemoteDataSource,
    private val coinLocalDataSource: CoinLocalDataSource,
) {

    suspend fun getCoinList(): Flow<List<CoinDTO>> {
        if (!coinLocalDataSource.hasCoins()) {
            val list = coinRemoteDataSource.getCoinList()
            list.onSuccess { coinLocalDataSource.addCoins(it) }
                .onFailure { Log.e("CoinRepository", "Coin list fetch error") }
        }
        return coinLocalDataSource.getCoinList()
    }

    suspend fun getCoinDetails(coinId: String): Result<CoinDetailsResponse> {
        return coinRemoteDataSource.getCoinDetail(coinId)
    }

    suspend fun getCoinListFilterBy(query: String): List<CoinDTO> {
        return coinLocalDataSource.getCoinListFilterBy(query)
    }
}