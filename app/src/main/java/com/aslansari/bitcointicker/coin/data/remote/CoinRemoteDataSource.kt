package com.aslansari.bitcointicker.coin.data.remote

import android.accounts.NetworkErrorException
import com.aslansari.bitcointicker.coin.data.CoinDTO
import com.aslansari.bitcointicker.coin.data.CoinListResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CoinRemoteDataSource @Inject constructor(
    private val coinService: CoinService
) {

    suspend fun getCoinList(): Result<List<CoinDTO>> = withContext(Dispatchers.IO) {
        val response = coinService.getCoinList(false)
        if (response.isSuccessful) {
            return@withContext Result.success(response.body() as CoinListResponse)
        } else {
            return@withContext Result.failure(NetworkErrorException("${response.code()}"))
        }
    }

    suspend fun getCoinDetail(coinId: String): Result<CoinDetailsResponse> = withContext(Dispatchers.IO) {
        val response = coinService.getCoinDetails(coinId)
        if (response.isSuccessful) {
            return@withContext Result.success(response.body() as CoinDetailsResponse)
        } else {
            return@withContext Result.failure(NetworkErrorException("${response.code()}"))
        }
    }
}