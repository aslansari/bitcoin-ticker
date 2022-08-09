package com.aslansari.bitcointicker.di

import android.app.Application
import com.aslansari.bitcointicker.coin.data.local.CoinDAO
import com.aslansari.bitcointicker.coin.data.local.CoinDatabase
import com.aslansari.bitcointicker.coin.data.local.FetchIntervalDataStore
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
import java.security.SecureRandom
import java.security.cert.X509Certificate
import javax.net.ssl.SSLContext
import javax.net.ssl.X509TrustManager

@Module
class AppModule(private val application: Application) {

    @Provides
    fun loggingInterceptor(): Interceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BASIC
    }

    @Provides
    fun client(interceptor: Interceptor): OkHttpClient {
        val clientBuilder = OkHttpClient.Builder()
        trustAll(clientBuilder)
        clientBuilder.addInterceptor(interceptor)
        return clientBuilder.build()
    }

    /**
     * Warning, this configuration is a security vulnerable and should not be used in production
     */
    private fun trustAll(clientBuilder: OkHttpClient.Builder) {
        val trustAll = arrayOf<X509TrustManager>(
            object : X509TrustManager {
                override fun checkClientTrusted(p0: Array<out X509Certificate>?, p1: String?) {
                }

                override fun checkServerTrusted(p0: Array<out X509Certificate>?, p1: String?) {
                }

                override fun getAcceptedIssuers(): Array<X509Certificate> {
                    return arrayOf()
                }
            }
        )
        val sslContext = SSLContext.getInstance("SSL")
        sslContext.init(null, trustAll, SecureRandom())
        clientBuilder.sslSocketFactory(sslContext.socketFactory, trustAll[0])
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

    @Provides
    @AppScope
    fun fetchIntervalDataStore() = FetchIntervalDataStore(application)
}