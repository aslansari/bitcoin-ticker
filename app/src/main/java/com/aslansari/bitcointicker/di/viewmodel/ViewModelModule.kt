package com.aslansari.bitcointicker.di.viewmodel

import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

@Module
class ViewModelModule() {

    @Provides
    fun vmDispatcher(): CoroutineDispatcher = Dispatchers.Main
}