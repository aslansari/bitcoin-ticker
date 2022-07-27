package com.aslansari.bitcointicker.di.activity

import com.aslansari.bitcointicker.coin.data.CoinRepository
import com.aslansari.bitcointicker.coin.data.local.CoinLocalDataSource
import com.aslansari.bitcointicker.coin.data.remote.BASE_URL
import com.aslansari.bitcointicker.coin.data.remote.CoinRemoteDataSource
import com.aslansari.bitcointicker.coin.data.remote.CoinService
import com.aslansari.bitcointicker.coin.domain.GetCoinListUseCase
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
class ActivityModule {

    @Provides
    fun coinRemoteDataSource(coinService: CoinService) = CoinRemoteDataSource(coinService)

    @Provides
    fun coinLocalDataSource() = CoinLocalDataSource()

    @Provides
    fun coinsRepository(
        coinRemoteDataSource: CoinRemoteDataSource,
        coinLocalDataSource: CoinLocalDataSource
    ) = CoinRepository(coinRemoteDataSource, coinLocalDataSource)

    @Provides
    fun getCoinListUseCase(
        coinRepository: CoinRepository
    ) = GetCoinListUseCase(coinRepository)
}