package com.aslansari.bitcointicker.favourite.data

import com.aslansari.bitcointicker.favourite.data.remote.FavouriteRemoteDataSource
import javax.inject.Inject

class FavouriteRepository @Inject constructor(
    private val favouriteRemoteDataSource: FavouriteRemoteDataSource
) {

    suspend fun getFavouriteCoinList(userId: String): List<FavouriteCoin> {
        return favouriteRemoteDataSource.getFavourites(userId)
    }

    suspend fun addToFavourites(userId: String, favouriteCoin: FavouriteCoin): Boolean {
        return favouriteRemoteDataSource.addToFavourites(userId, favouriteCoin)
    }

    suspend fun updateFavCoinPrice(userId: String, favouriteCoin: FavouriteCoin): Boolean {
        return favouriteRemoteDataSource.updateFavouriteCoinPrice(userId, favouriteCoin)
    }

    suspend fun removeFromFavourites(userId: String, favouriteCoin: FavouriteCoin): Boolean {
        return favouriteRemoteDataSource.removeFromFavourites(userId, favouriteCoin)
    }
}