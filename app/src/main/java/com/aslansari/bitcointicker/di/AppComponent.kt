package com.aslansari.bitcointicker.di

import com.aslansari.bitcointicker.coin.data.remote.CoinService
import dagger.Component

@Component(modules = [AppModule::class])
interface AppComponent {

    fun coinService(): CoinService
}