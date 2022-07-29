package com.aslansari.bitcointicker.ui.util

import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.*

object DisplayTextUtil {

    object Amount {
        private val decimalFormat = DecimalFormat("###,##0.00", DecimalFormatSymbols(Locale.US))
        private val currencyFormat = DecimalFormat(
            "###,##0.################", DecimalFormatSymbols(
                Locale.US
            )
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