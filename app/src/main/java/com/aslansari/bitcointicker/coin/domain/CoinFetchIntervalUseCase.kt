package com.aslansari.bitcointicker.coin.domain

import com.aslansari.bitcointicker.coin.data.local.FetchIntervalDataStore
import javax.inject.Inject

class CoinFetchIntervalUseCase @Inject constructor(
    private val fetchIntervalDataStore: FetchIntervalDataStore,
) {

    fun getFlow() = fetchIntervalDataStore.getFetchIntervalFlow()

    suspend fun get() = fetchIntervalDataStore.getFetchInterval()

    suspend fun set(interval: Long): Boolean {
        // additional business logic can be added here, such as limiting min/max interval time
        fetchIntervalDataStore.setFetchInterval(interval)
        return true
    }
}