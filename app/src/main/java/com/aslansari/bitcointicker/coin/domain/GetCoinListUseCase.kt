package com.aslansari.bitcointicker.coin.domain

import com.aslansari.bitcointicker.coin.data.CoinRepository
import com.aslansari.bitcointicker.coin.ui.CoinListItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetCoinListUseCase @Inject constructor(
    private val coinRepository: CoinRepository
) {

    suspend operator fun invoke(): Flow<List<CoinListItem>> = withContext(Dispatchers.IO) {
        coinRepository.getCoinList().map {
            it.map { dto ->
                CoinListItem(
                    dto.id,
                    dto.symbol,
                    dto.name,
                )
            }
        }
    }

    suspend fun filterBy(query: String): List<CoinListItem> = withContext(Dispatchers.IO) {
        coinRepository.getCoinListFilterBy(query).map { dto ->
            CoinListItem(dto.id, dto.symbol, dto.name)
        }
    }
}