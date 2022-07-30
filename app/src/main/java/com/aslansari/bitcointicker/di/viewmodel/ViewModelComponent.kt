package com.aslansari.bitcointicker.di.viewmodel

import com.aslansari.bitcointicker.coin.ui.CoinDetailsFragment
import com.aslansari.bitcointicker.coin.ui.CoinListFragment
import com.aslansari.bitcointicker.coin.ui.FetchIntervalDialogFragment
import com.aslansari.bitcointicker.favourite.FavouriteListFragment
import dagger.Subcomponent

@Subcomponent(modules = [ViewModelModule::class])
interface ViewModelComponent {
    fun inject(coinListFragment: CoinListFragment)
    fun inject(favouriteListFragment: FavouriteListFragment)
    fun inject(coinDetailsFragment: CoinDetailsFragment)
    fun inject(fetchIntervalDialogFragment: FetchIntervalDialogFragment)
}