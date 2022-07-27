package com.aslansari.bitcointicker.coin.data.remote

import com.aslansari.bitcointicker.coin.data.CoinListResponse
import retrofit2.http.GET
import retrofit2.http.Query

const val BASE_URL = "https://api.coingecko.com/api/v3/"
interface CoinService {

    @GET("coins/list")
    suspend fun getCoinList(
        @Query("include_platform") includePlatform: Boolean,
    ): CoinListResponse

}
