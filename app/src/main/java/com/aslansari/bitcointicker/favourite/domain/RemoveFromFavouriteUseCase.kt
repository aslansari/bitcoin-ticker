package com.aslansari.bitcointicker.favourite.domain

import com.aslansari.bitcointicker.account.data.AccountRepository
import com.aslansari.bitcointicker.favourite.data.FavouriteRepository
import com.aslansari.bitcointicker.favourite.ui.FavouriteCoin
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RemoveFromFavouriteUseCase @Inject constructor(
    private val favouriteRepository: FavouriteRepository,
    private val accountRepository: AccountRepository,
){

    suspend operator fun invoke(favouriteCoin: FavouriteCoin): Boolean = withContext(Dispatchers.IO) {
        if (accountRepository.isLoggedIn()) {
            val userId = accountRepository.getUserId()
            if (userId != null) {
                val favCoin = favouriteCoin.let {
                    FavouriteCoinDTO(it.id, it.name, it.symbol, it.priceUsd)
                }
                favouriteRepository.removeFromFavourites(userId, favCoin)
            } else {
                false
            }
        } else {
           false
        }
    }
}