package com.aslansari.bitcointicker.coin.ui

import android.util.Log
import androidx.lifecycle.*
import com.aslansari.bitcointicker.coin.domain.CoinFetchIntervalUseCase
import com.aslansari.bitcointicker.coin.domain.GetCoinDetailsUseCase
import com.aslansari.bitcointicker.favourite.domain.GetFavouriteCoinsUseCase
import com.aslansari.bitcointicker.favourite.domain.RemoveFromFavouriteUseCase
import com.aslansari.bitcointicker.favourite.domain.SaveToFavouriteUseCase
import com.aslansari.bitcointicker.favourite.ui.FavouriteCoin
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Provider

class CoinDetailsViewModel @Inject constructor(
    private val getCoinDetailsUseCase: GetCoinDetailsUseCase,
    private val getFavouriteCoinsUseCase: GetFavouriteCoinsUseCase,
    private val coinFetchIntervalUseCase: CoinFetchIntervalUseCase,
    private val saveToFavouriteUseCase: SaveToFavouriteUseCase,
    private val removeFromFavouriteUseCase: RemoveFromFavouriteUseCase,
    private val coroutineDispatcher: CoroutineDispatcher,
) : ViewModel() {

    private val _coinDetailsUIState = MutableLiveData<CoinDetailsUIState>()
    val coinDetailsUIState = _coinDetailsUIState as LiveData<CoinDetailsUIState>

    private val _favIconState = MutableLiveData<FavIconUIState>()
    val favIconState = _favIconState as LiveData<FavIconUIState>


    fun fetchWithFlow(id: String) {
        viewModelScope.launch(coroutineDispatcher) {
            while (true) {
                delay(coinFetchIntervalUseCase.get())
                _coinDetailsUIState.value = CoinDetailsUIState.Loading
                getCoinDetailsUseCase(id)
                    .onSuccess {
                        val detailsUIState = CoinDetailsUIState.Result(it)
                        _coinDetailsUIState.value = detailsUIState
                    }
                    .onFailure {
                        Log.e("CoinsDetailsViewModel", "coin details fetch error")
                    }
            }
        }
    }

    fun fetch(id: String) {
        viewModelScope.launch(coroutineDispatcher) {
            _coinDetailsUIState.value = CoinDetailsUIState.Loading
            getCoinDetailsUseCase(id)
                .onSuccess {
                    val detailsUIState = CoinDetailsUIState.Result(it)
                    _coinDetailsUIState.value = detailsUIState
                }
                .onFailure {
                    _coinDetailsUIState.value = CoinDetailsUIState.Error
                }
        }
    }

    fun saveToFavourites(favouriteCoin: FavouriteCoin) {
        viewModelScope.launch(coroutineDispatcher) {
            _favIconState.value = FavIconUIState.Loading
            val isSuccess = saveToFavouriteUseCase(favouriteCoin)
            _favIconState.value = if (isSuccess) {
                FavIconUIState.Result(FavouriteAction.SAVE_TO_FAV)
            } else {
                FavIconUIState.Error(FavouriteAction.SAVE_TO_FAV)
            }
        }
    }

    fun removeFromFavourites(favouriteCoin: FavouriteCoin) {
        viewModelScope.launch(coroutineDispatcher) {
            _favIconState.value = FavIconUIState.Loading
            val isSuccess = removeFromFavouriteUseCase(favouriteCoin)
            _favIconState.value = if (isSuccess) {
                FavIconUIState.Result(FavouriteAction.REMOVE_FROM_FAV)
            } else {
                FavIconUIState.Error(FavouriteAction.REMOVE_FROM_FAV)
            }
        }
    }

    suspend fun isCoinFav(id: String): Boolean {
        val favCoin = getFavouriteCoinsUseCase().find { it.id == id }
        return favCoin != null
    }

}

@Suppress("UNCHECKED_CAST")
class CoinDetailsViewModelFactory @Inject constructor(
    private val getCoinDetailsUseCaseProvider: Provider<GetCoinDetailsUseCase>,
    private val getFavouriteCoinsUseCaseProvider: Provider<GetFavouriteCoinsUseCase>,
    private val coinFetchIntervalUseCaseProvider: Provider<CoinFetchIntervalUseCase>,
    private val saveToFavouriteUseCaseProvider: Provider<SaveToFavouriteUseCase>,
    private val removeFromFavouriteUseCaseProvider: Provider<RemoveFromFavouriteUseCase>,
    private val coroutineDispatcher: CoroutineDispatcher,
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CoinDetailsViewModel::class.java)) {
            return CoinDetailsViewModel(
                getCoinDetailsUseCaseProvider.get(),
                getFavouriteCoinsUseCaseProvider.get(),
                coinFetchIntervalUseCaseProvider.get(),
                saveToFavouriteUseCaseProvider.get(),
                removeFromFavouriteUseCaseProvider.get(),
                coroutineDispatcher
            ) as T
        }
        return super.create(modelClass)
    }

}
