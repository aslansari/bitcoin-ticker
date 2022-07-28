package com.aslansari.bitcointicker.coin.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.aslansari.bitcointicker.coin.domain.GetCoinListUseCase
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject
import javax.inject.Provider

class CoinListViewModel @Inject constructor(
    private val getCoinListUseCase: GetCoinListUseCase,
    private val coroutineDispatcher: CoroutineDispatcher,
): ViewModel() {

    private val _coinListState = MutableLiveData<List<CoinListItem>>()
    val coinListLiveData = _coinListState as LiveData<List<CoinListItem>>

    suspend fun coinListFlow() = getCoinListUseCase()

}

@Suppress("UNCHECKED_CAST")
class CoinListViewModelFactory @Inject constructor(
    private val getCoinListUseCaseProvider: Provider<GetCoinListUseCase>,
    private val coroutineDispatcher: CoroutineDispatcher,
): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CoinListViewModel::class.java)) {
            return CoinListViewModel(getCoinListUseCaseProvider.get(), coroutineDispatcher) as T
        }
        return super.create(modelClass)
    }

}