package com.aslansari.bitcointicker.di

import android.app.Application
import com.aslansari.bitcointicker.coin.data.local.CoinDAO
import com.aslansari.bitcointicker.coin.data.local.CoinDatabase
import com.aslansari.bitcointicker.coin.data.remote.BASE_URL
import com.aslansari.bitcointicker.coin.data.remote.CoinService
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
class AppModule(private val application: Application) {

    @Provides
    fun loggingInterceptor(): Interceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BASIC
    }

    @Provides
    fun client(interceptor: Interceptor): OkHttpClient {
        val clientBuilder = OkHttpClient.Builder()
        clientBuilder.addInterceptor(interceptor)
        return clientBuilder.build()
    }

    @Provides
    @AppScope
    fun retrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @AppScope
    fun coinService(retrofit: Retrofit): CoinService {
        return retrofit.create(CoinService::class.java)
    }

    @Provides
    @AppScope
    fun coinDatabase(): CoinDatabase {
        return CoinDatabase.getDatabase(application)
    }

    @Provides
    @AppScope
    fun coinDao(coinDatabase: CoinDatabase): CoinDAO {
        return coinDatabase.getCoinDao()
    }

    @Provides
    @AppScope
    fun auth() = Firebase.auth

    @Provides
    @AppScope
    fun cloudStore() = Firebase.firestore
}