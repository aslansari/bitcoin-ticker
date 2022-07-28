package com.aslansari.bitcointicker.coin.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.aslansari.bitcointicker.coin.data.CoinDTO

@Database(entities = [CoinDTO::class], version = 2)
abstract class CoinDatabase: RoomDatabase() {

    abstract fun getCoinDao(): CoinDAO

    companion object {
        @Volatile
        private var INSTANCE: CoinDatabase? = null

        fun getDatabase(context: Context): CoinDatabase {
            return INSTANCE?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    CoinDatabase::class.java,
                    "coins.db")
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}