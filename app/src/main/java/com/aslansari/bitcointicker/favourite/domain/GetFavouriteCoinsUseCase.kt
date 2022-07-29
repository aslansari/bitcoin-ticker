package com.aslansari.bitcointicker.favourite.domain

import com.aslansari.bitcointicker.account.data.AccountRepository
import com.aslansari.bitcointicker.favourite.data.FavouriteRepository
import com.aslansari.bitcointicker.favourite.ui.FavouriteCoin
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetFavouriteCoinsUseCase @Inject constructor(
    private val favouriteRepository: FavouriteRepository,
    private val accountRepository: AccountRepository,
){

    suspend operator fun invoke(): List<FavouriteCoin> = withContext(Dispatchers.IO) {
        if (accountRepository.isLoggedIn()) {
            val userId = accountRepository.getUserId()
            if (userId != null) {
                favouriteRepository.getFavouriteCoinList(userId)
                    .map { favourite ->
                        FavouriteCoin(
                            id = favourite.id,
                            name = favourite.name,
                            symbol = favourite.symbol
                        )
                    }
            } else {
                // TODO: return error
                emptyList()
            }
        } else {
            // TODO: return error
            emptyList()
        }
    }

}