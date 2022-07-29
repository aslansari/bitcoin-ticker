package com.aslansari.bitcointicker.coin.ui

import androidx.appcompat.widget.SearchView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.aslansari.bitcointicker.coin.domain.GetCoinListUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Provider
import kotlin.time.Duration.Companion.seconds

class CoinListViewModel @Inject constructor(
    private val getCoinListUseCase: GetCoinListUseCase,
    private val coroutineDispatcher: CoroutineDispatcher,
): ViewModel() {

    private val _coinListState = MutableLiveData<List<CoinListItem>>()
    val coinListLiveData = _coinListState as LiveData<List<CoinListItem>>

    suspend fun coinListFlow() = getCoinListUseCase()

    fun registerSearch(searchView: SearchView) = callbackFlow {
        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                trySend(newText)
                return true
            }
        })
        awaitClose { searchView.setOnQueryTextListener(null) }
    }.debounce(1000)
        .map {
            val list = getCoinListUseCase.filterBy(it ?: "")
            _coinListState.value = list
            list
        }

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