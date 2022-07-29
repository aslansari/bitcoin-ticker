package com.aslansari.bitcointicker.coin.data.local

import com.aslansari.bitcointicker.coin.data.CoinDTO
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CoinLocalDataSource @Inject constructor(
    private val coinDAO: CoinDAO,
){

    fun hasCoins(): Boolean {
        return coinDAO.isExists()
    }

    fun getCoinList(): Flow<List<CoinDTO>> {
        return coinDAO.getCoinList()
    }

    suspend fun addCoins(coinList: List<CoinDTO>) {
        coinDAO.addCoinList(coinList)
    }

    suspend fun getCoinListFilterBy(query: String): List<CoinDTO> {
        return coinDAO.getCoinListFilteredBy(query)
    }

}