package com.aslansari.bitcointicker.coin.domain

import java.util.concurrent.TimeUnit
import javax.inject.Inject

class CoinFetchIntervalUseCase @Inject constructor(
) {

    // todo store and get this interval from datastore
    private val interval = TimeUnit.MINUTES.toMillis(1)

    suspend fun get(): Long {
        return interval
    }

}