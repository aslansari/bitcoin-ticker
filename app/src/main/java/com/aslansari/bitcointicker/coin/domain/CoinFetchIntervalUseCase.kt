package com.aslansari.bitcointicker.coin.domain

import com.aslansari.bitcointicker.coin.data.local.FetchIntervalDataStore
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class CoinFetchIntervalUseCase @Inject constructor(
    private val fetchIntervalDataStore: FetchIntervalDataStore,
) {

    private val minimumFetchInterval by lazy { TimeUnit.SECONDS.toMillis(10) }

    fun getFlow() = fetchIntervalDataStore.getFetchIntervalFlow()

    suspend fun get() = fetchIntervalDataStore.getFetchInterval()

    suspend fun set(interval: Long): Boolean {
        if (interval < minimumFetchInterval) {
            return false
        }
        fetchIntervalDataStore.setFetchInterval(interval)
        return true
    }
}