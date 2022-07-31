package com.aslansari.bitcointicker.coin.ui

import androidx.appcompat.widget.SearchView
import androidx.lifecycle.*
import com.aslansari.bitcointicker.coin.domain.GetCoinListUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject
import javax.inject.Provider

class CoinListViewModel @Inject constructor(
    private val getCoinListUseCase: GetCoinListUseCase,
    private val coroutineDispatcher: CoroutineDispatcher,
): ViewModel() {

    private val _coinListState = MutableLiveData<CoinListUIState>()
    val coinListLiveData = _coinListState as LiveData<CoinListUIState>

    suspend fun coinListFlow() = getCoinListUseCase().stateIn(viewModelScope)

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
    }.debounce(500)
        .map {
            if (it.isNullOrBlank()) {
                return@map coinListFlow().value
            }
            _coinListState.value = CoinListUIState.Loading
            val list = getCoinListUseCase.filterBy(it ?: "")
            _coinListState.value = CoinListUIState.Result
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