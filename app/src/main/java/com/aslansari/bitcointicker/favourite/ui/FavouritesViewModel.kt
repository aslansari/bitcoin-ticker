package com.aslansari.bitcointicker.favourite.ui

import androidx.lifecycle.*
import com.aslansari.bitcointicker.favourite.domain.GetFavouriteCoinsUseCase
import com.aslansari.bitcointicker.favourite.domain.SaveToFavouriteUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Provider

class FavouritesViewModel(
    private val getFavouriteCoinsUseCase: GetFavouriteCoinsUseCase,
    private val saveToFavouriteUseCase: SaveToFavouriteUseCase,
    private val coroutineDispatcher: CoroutineDispatcher,
): ViewModel() {

    private val _favouritesUIState = MutableLiveData<FavouritesUIState>()
    val favouritesUIState = _favouritesUIState as LiveData<FavouritesUIState>

    fun fetch() {
        viewModelScope.launch(coroutineDispatcher) {
            _favouritesUIState.value = FavouritesUIState.Loading
            val list = getFavouriteCoinsUseCase()
            _favouritesUIState.value = FavouritesUIState.Result(list)
        }
    }

}

@Suppress("UNCHECKED_CAST")
class FavouritesViewModelFactory @Inject constructor(
    private val getFavouriteCoinsUseCaseProvider: Provider<GetFavouriteCoinsUseCase>,
    private val saveToFavouriteUseCaseProvider: Provider<SaveToFavouriteUseCase>,
    private val coroutineDispatcher: CoroutineDispatcher,
): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FavouritesViewModel::class.java)) {
            return FavouritesViewModel(getFavouriteCoinsUseCaseProvider.get(), saveToFavouriteUseCaseProvider.get(), coroutineDispatcher) as T
        }
        return super.create(modelClass)
    }

}
