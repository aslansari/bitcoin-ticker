package com.aslansari.bitcointicker.coin.ui

import androidx.lifecycle.*
import com.aslansari.bitcointicker.coin.domain.GetCoinDetailsUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.*
import javax.inject.Inject
import javax.inject.Provider

class CoinDetailsViewModel @Inject constructor(
    private val getCoinDetailsUseCase: GetCoinDetailsUseCase,
    private val coroutineDispatcher: CoroutineDispatcher,
): ViewModel() {

    private val _coinDetailsUIState = MutableLiveData<CoinDetailsUIState>()
    val coinDetailsUIState = _coinDetailsUIState as LiveData<CoinDetailsUIState>

    fun fetch(id: String) {
        viewModelScope.launch(coroutineDispatcher) {
            _coinDetailsUIState.value = CoinDetailsUIState.Loading
            val details = getCoinDetailsUseCase(id)
            val detailsUIState = CoinDetailsUIState.Result(
                details.name,
                details.hashingAlgorithm,
                details.description.englishDescription,
                details.marketData.currentPrice.usd,
                details.marketData.priceChangePercentage24h,
                details.image?.largeImageUrl,
            )
            _coinDetailsUIState.value = detailsUIState
        }
    }

}

object DisplayTextUtil {

    object Amount {
        private val decimalFormat = DecimalFormat("###,##0.00", DecimalFormatSymbols(Locale.US))
        private val currencyFormat = DecimalFormat("###,##0.################", DecimalFormatSymbols(
            Locale.US)
        )
        private val rateFormat = DecimalFormat("#0.###", DecimalFormatSymbols(Locale.US))

        /**
         * Get dollar amount for display
         * @param amount amount in cents
         * @return return formatted string {$12.45 USD}
         */
        fun getDollarAmount(amount: Double): String {
            val dollarAmount = decimalFormat.format(amount)
            return "$${dollarAmount} USD"
        }

        fun getCurrencyFormat(amount: Double): String = currencyFormat.format(amount)

        fun getRateFormat(amount: Double): String = rateFormat.format(amount)

        fun decimalFormat(amount: Double): String = decimalFormat.format(amount)
    }
}

sealed class CoinDetailsUIState {
    object Loading: CoinDetailsUIState()
    object Error: CoinDetailsUIState()
    data class Result(
        val name: String,
        val hashAlgorithm: String?,
        val description: String?,
        val priceUSD: Double,
        val roiLastDay: Double,
        val imageUrl: String?,
    ): CoinDetailsUIState()
}

@Suppress("UNCHECKED_CAST")
class CoinDetailsViewModelFactory @Inject constructor(
    private val getCoinDetailsUseCaseProvider: Provider<GetCoinDetailsUseCase>,
    private val coroutineDispatcher: CoroutineDispatcher,
): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CoinDetailsViewModel::class.java)) {
            return CoinDetailsViewModel(getCoinDetailsUseCaseProvider.get(), coroutineDispatcher) as T
        }
        return super.create(modelClass)
    }

}
