package com.aslansari.bitcointicker.di.activity

import com.aslansari.bitcointicker.coin.data.CoinRepository
import com.aslansari.bitcointicker.coin.data.local.CoinDAO
import com.aslansari.bitcointicker.coin.data.local.CoinLocalDataSource
import com.aslansari.bitcointicker.coin.data.remote.CoinRemoteDataSource
import com.aslansari.bitcointicker.coin.data.remote.CoinService
import com.aslansari.bitcointicker.coin.domain.GetCoinListUseCase
import dagger.Module
import dagger.Provides

@Module
class ActivityModule {

    @Provides
    fun coinRemoteDataSource(coinService: CoinService) = CoinRemoteDataSource(coinService)

    @Provides
    fun coinLocalDataSource(coinDAO: CoinDAO) = CoinLocalDataSource(coinDAO)

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