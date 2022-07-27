package com.aslansari.bitcointicker.coin.domain

import com.aslansari.bitcointicker.coin.data.CoinRepository
import com.aslansari.bitcointicker.coin.ui.CoinListItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetCoinListUseCase @Inject constructor(
    private val coinRepository: CoinRepository
) {

    suspend operator fun invoke(): List<CoinListItem> = withContext(Dispatchers.IO) {
        coinRepository.getCoinList().map {
            CoinListItem(
                it.id,
                it.symbol,
                it.name,
            )
        }
    }
}