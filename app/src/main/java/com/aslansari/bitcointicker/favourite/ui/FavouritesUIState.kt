package com.aslansari.bitcointicker.favourite.ui

sealed class FavouritesUIState {
    object Error: FavouritesUIState()
    object Loading: FavouritesUIState()
    data class Result(
        val favourites: List<FavouriteCoin>
    ): FavouritesUIState()
}