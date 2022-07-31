package com.aslansari.bitcointicker.coin.ui

import androidx.lifecycle.*
import com.aslansari.bitcointicker.coin.domain.CoinFetchIntervalUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Provider

class FetchIntervalViewModel @Inject constructor(
    private val coinFetchIntervalUseCase: CoinFetchIntervalUseCase,
    private val coroutineDispatcher: CoroutineDispatcher,
) : ViewModel() {

    private val _fetchIntervalUIState = MutableLiveData<FetchIntervalUIState>()
    val fetchIntervalUIState = _fetchIntervalUIState as LiveData<FetchIntervalUIState>

    suspend fun getInterval() = coinFetchIntervalUseCase.getFlow().stateIn(viewModelScope)

    fun update(interval: Long) {
        viewModelScope.launch(coroutineDispatcher) {
            _fetchIntervalUIState.value = FetchIntervalUIState.Loading
            val isSuccessful = coinFetchIntervalUseCase.set(interval)
            if (isSuccessful) {
                _fetchIntervalUIState.value = FetchIntervalUIState.Result(interval)
            } else {
                _fetchIntervalUIState.value = FetchIntervalUIState.Error
            }
        }
    }
}

@Suppress("UNCHECKED_CAST")
class FetchIntervalViewModelFactory @Inject constructor(
    private val coinFetchIntervalUseCaseProvider: Provider<CoinFetchIntervalUseCase>,
    private val coroutineDispatcher: CoroutineDispatcher,
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FetchIntervalViewModel::class.java)) {
            return FetchIntervalViewModel(
                coinFetchIntervalUseCaseProvider.get(),
                coroutineDispatcher
            ) as T
        }
        return super.create(modelClass)
    }

}
