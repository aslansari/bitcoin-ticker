package com.aslansari.bitcointicker.coin.domain

import com.aslansari.bitcointicker.coin.data.CoinRepository
import com.aslansari.bitcointicker.coin.data.remote.CoinDetailsResponse
import javax.inject.Inject

class GetCoinDetailsUseCase @Inject constructor(
    private val coinRepository: CoinRepository,
) {

    suspend operator fun invoke(coinId: String): CoinDetailsResponse {
        return coinRepository.getCoinDetails(coinId)
    }
}