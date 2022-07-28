package com.aslansari.bitcointicker.coin.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.aslansari.bitcointicker.coin.data.CoinDTO
import kotlinx.coroutines.flow.Flow

@Dao
interface CoinDAO {

    @Query("SELECT * FROM coins")
    fun getCoinList(): Flow<List<CoinDTO>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addCoinList(coinList: List<CoinDTO>)

    @Query("SELECT EXISTS(SELECT * FROM coins)")
    fun isExists(): Boolean
}