package com.aslansari.bitcointicker.coin.domain

import com.aslansari.bitcointicker.coin.data.CoinRepository
import com.aslansari.bitcointicker.coin.ui.CoinDetails
import javax.inject.Inject

class GetCoinDetailsUseCase @Inject constructor(
    private val coinRepository: CoinRepository,
) {

    suspend operator fun invoke(coinId: String): Result<CoinDetails> {
        return coinRepository.getCoinDetails(coinId)
            .map { details ->
                CoinDetails(
                    details.id,
                    details.name,
                    details.symbol,
                    details.hashingAlgorithm,
                    details.description.englishDescription,
                    details.marketData.currentPrice.usd,
                    details.marketData.priceChangePercentage24h,
                    details.image?.largeImageUrl,
                )
            }
            .onSuccess { details ->
            return Result.success(details)
        }.onFailure {
            return Result.failure(it)
        }
    }
}