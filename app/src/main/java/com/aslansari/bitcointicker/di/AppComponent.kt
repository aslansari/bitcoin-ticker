package com.aslansari.bitcointicker.di

import com.aslansari.bitcointicker.coin.data.local.CoinDAO
import com.aslansari.bitcointicker.coin.data.remote.CoinService
import dagger.Component

@AppScope
@Component(modules = [AppModule::class])
interface AppComponent {

    fun coinService(): CoinService

    fun coinDAO(): CoinDAO
}