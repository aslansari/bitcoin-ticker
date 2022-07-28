package com.aslansari.bitcointicker.coin.data.remote

import com.aslansari.bitcointicker.coin.data.CoinListResponse
import com.google.gson.annotations.SerializedName
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

const val BASE_URL = "https://api.coingecko.com/api/v3/"
interface CoinService {

    @GET("coins/list")
    suspend fun getCoinList(
        @Query("include_platform") includePlatform: Boolean,
    ): CoinListResponse

    @GET("coins/{id}")
    suspend fun getCoinDetails(
        @Path("id") coinId: String,
        @Query("tickers") fetchTickers: Boolean = false,
        @Query("market_data") fetchMarketData: Boolean = true,
        @Query("community_data") fetchCommunityData: Boolean = false,
        @Query("developer_data") fetchDeveloperData: Boolean = false,
        @Query("sparkline") fetchSparkline: Boolean = false,
    ): CoinDetailsResponse
}

data class CoinDetailsResponse(
    val id: String,
    val symbol: String,
    val name: String,
    @SerializedName("hashing_algorithm")
    val hashingAlgorithm: String = "",
    val description: CoinDescription,
    @SerializedName("market_data")
    val marketData: MarketData,
    val image: CoinImage?,
)

data class CoinDescription(
    @SerializedName("en")
    val englishDescription: String
)

data class CoinImage(
    @SerializedName("thumb")
    val thumbNailUrl: String?,
    @SerializedName("small")
    val smallImageUrl : String?,
    @SerializedName("large")
    val largeImageUrl: String?,
)

data class MarketData(
    @SerializedName("current_price")
    val currentPrice: CurrentPrice,
    @SerializedName("price_change_percentage_24h")
    val priceChangePercentage24h: Double
)

data class CurrentPrice(
    val usd: Double
)
