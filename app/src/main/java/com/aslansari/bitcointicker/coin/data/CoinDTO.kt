package com.aslansari.bitcointicker.coin.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "coins")
data class CoinDTO(
    @field:PrimaryKey(autoGenerate = true)
    val index: Long,
    val id: String,
    val symbol: String,
    val name: String,
)
