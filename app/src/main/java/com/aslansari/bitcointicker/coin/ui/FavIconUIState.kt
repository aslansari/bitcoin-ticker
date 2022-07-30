package com.aslansari.bitcointicker.coin.ui

sealed class FavIconUIState {
    data class Error(
        val favouriteAction: FavouriteAction
    ): FavIconUIState()
    object Loading: FavIconUIState()
    data class Result(
        val favouriteAction: FavouriteAction
    ): FavIconUIState()
}

enum class FavouriteAction {
    SAVE_TO_FAV,
    REMOVE_FROM_FAV,
}
