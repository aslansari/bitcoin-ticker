package com.aslansari.bitcointicker.di

import com.aslansari.bitcointicker.coin.data.local.CoinDAO
import com.aslansari.bitcointicker.coin.data.local.FetchIntervalDataStore
import com.aslansari.bitcointicker.coin.data.remote.CoinService
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Component

@AppScope
@Component(modules = [AppModule::class])
interface AppComponent {

    fun coinService(): CoinService

    fun coinDAO(): CoinDAO

    fun auth(): FirebaseAuth

    fun cloudStore(): FirebaseFirestore

    fun fetchIntervalDataStore(): FetchIntervalDataStore
}