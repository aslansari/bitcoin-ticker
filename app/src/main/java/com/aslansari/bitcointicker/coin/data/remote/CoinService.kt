package com.aslansari.bitcointicker.coin.data.remote

import com.aslansari.bitcointicker.coin.data.CoinListResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

const val BASE_URL = "https://api.coingecko.com/api/v3/"
interface CoinService {

    @GET("coins/list")
    suspend fun getCoinList(
        @Query("include_platform") includePlatform: Boolean,
    ): Response<CoinListResponse>

    @GET("coins/{id}")
    suspend fun getCoinDetails(
        @Path("id") coinId: String,
        @Query("tickers") fetchTickers: Boolean = false,
        @Query("market_data") fetchMarketData: Boolean = true,
        @Query("community_data") fetchCommunityData: Boolean = false,
        @Query("developer_data") fetchDeveloperData: Boolean = false,
        @Query("sparkline") fetchSparkline: Boolean = false,
    ): Response<CoinDetailsResponse>
}
