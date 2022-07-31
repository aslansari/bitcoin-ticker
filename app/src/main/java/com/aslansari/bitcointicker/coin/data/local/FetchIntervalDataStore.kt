package com.aslansari.bitcointicker.coin.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import java.util.concurrent.TimeUnit

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class FetchIntervalDataStore(private val context: Context) {

    private val initialFetchInterval by lazy { TimeUnit.MINUTES.toMillis(1) }
    private val coinFetchInterval = longPreferencesKey("coin_fetch_interval")

    fun getFetchIntervalFlow(): Flow<Long> {
        return context.dataStore.data
            .map { preferences ->
                preferences[coinFetchInterval] ?: initialFetchInterval
            }
    }

    suspend fun getFetchInterval(): Long = withContext(Dispatchers.IO) {
        context.dataStore.data.first()[coinFetchInterval] ?: initialFetchInterval
    }

    suspend fun setFetchInterval(interval: Long) {
        context.dataStore.edit { settings ->
            settings[coinFetchInterval] = interval
        }
    }
}