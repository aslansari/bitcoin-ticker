package com.aslansari.bitcointicker.coin.domain

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent.FLAG_IMMUTABLE
import android.app.PendingIntent.getActivity
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_CLEAR_TASK
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.graphics.Bitmap
import android.graphics.Bitmap.createBitmap
import android.graphics.Canvas
import android.os.Build.VERSION.SDK_INT
import android.os.Build.VERSION_CODES.O
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationCompat.DEFAULT_ALL
import androidx.core.app.NotificationCompat.PRIORITY_MAX
import androidx.core.app.NotificationManagerCompat.IMPORTANCE_HIGH
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.aslansari.bitcointicker.MainActivity
import com.aslansari.bitcointicker.R
import com.aslansari.bitcointicker.favourite.domain.FavouriteCoinDTO
import com.aslansari.bitcointicker.favourite.domain.GetFavouriteCoinsUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject

class PeriodicCoinWorker @AssistedInject constructor(
    @Assisted private val appContext: Context,
    @Assisted private val workerParameters: WorkerParameters,
    private val getFavouriteCoinsUseCase: GetFavouriteCoinsUseCase,
    private val getCoinDetailsUseCase: GetCoinDetailsUseCase,
) : CoroutineWorker(appContext, workerParameters) {

    companion object {
        const val NOTIFICATION_ID = "fav_coin_price_change_id"
        const val NOTIFICATION_NAME = "bitcoin_ticker"
        const val NOTIFICATION_CHANNEL = "ticker_channel_01"
    }

    override suspend fun doWork(): Result {
        val favCoinList = getFavouriteCoinsUseCase()
        val changeList = mutableListOf<PriceChange>()
        if (favCoinList.isNotEmpty()) {
            favCoinList.forEach { coin ->
                val result = getCoinDetailsUseCase(coin.id)
                if (result.isSuccess) {
                    result.onSuccess { details ->
                        if (details.priceUSD != coin.priceUsd) {
                            val isUp = details.priceUSD > coin.priceUsd
                            changeList.add(
                                PriceChange(
                                    coin,
                                    isUp,
                                    details.priceUSD
                                )
                            )
                            val favouriteCoinDTO = FavouriteCoinDTO(
                                coin.id,
                                coin.name,
                                coin.symbol,
                                details.priceUSD
                            )
                            getFavouriteCoinsUseCase.updatePrice(favouriteCoinDTO)
                        }
                    }
                }
            }
            if (changeList.isNotEmpty()) {
                val id = inputData.getLong(NOTIFICATION_ID, 0).toInt()
                sendNotification(id, getNotificationTitle(changeList[0]), getNotificationMessage(changeList[0]))
            }
        }
        return Result.success()
    }

    private fun getNotificationTitle(priceChange: PriceChange): String {
        return appContext.getString(R.string.notification_price_change_title, getPriceChangeString(priceChange.isUp))
    }

    private fun getNotificationMessage(priceChange: PriceChange): String {
        return appContext.getString(R.string.notification_price_change_message, priceChange.favouriteCoin.name, priceChange.newPrice)
    }

    private fun getPriceChangeString(isUp: Boolean): String {
        return if (isUp) {
            appContext.getString(R.string.up)
        } else {
            appContext.getString(R.string.down)
        }
    }

    private fun sendNotification(id: Int, title: String, message: String) {
        val intent = Intent(applicationContext, MainActivity::class.java)
        intent.flags = FLAG_ACTIVITY_NEW_TASK or FLAG_ACTIVITY_CLEAR_TASK
        intent.putExtra(NOTIFICATION_ID, id)

        val notificationManager =
            applicationContext.getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        val bitmap = applicationContext.vectorToBitmap(R.drawable.ic_favourite_filled)
        val pendingIntent = getActivity(applicationContext, 0, intent, FLAG_IMMUTABLE)
        val notification = NotificationCompat.Builder(applicationContext, NOTIFICATION_CHANNEL)
            .setLargeIcon(bitmap).setSmallIcon(R.drawable.ic_favourite_border)
            .setContentTitle(title).setContentText(message)
            .setDefaults(DEFAULT_ALL).setContentIntent(pendingIntent).setAutoCancel(true)

        notification.priority = PRIORITY_MAX

        if (SDK_INT >= O) {
            notification.setChannelId(NOTIFICATION_CHANNEL)

            val channel =
                NotificationChannel(NOTIFICATION_CHANNEL, NOTIFICATION_NAME, IMPORTANCE_HIGH)

            notificationManager.createNotificationChannel(channel)
        }

        notificationManager.notify(id, notification.build())
    }

    @AssistedFactory
    interface Factory {
        fun create(appContext: Context, parameters: WorkerParameters): PeriodicCoinWorker
    }
}

fun Context.vectorToBitmap(drawableId: Int): Bitmap? {
    val drawable = getDrawable(drawableId) ?: return null
    val bitmap = createBitmap(
        drawable.intrinsicWidth, drawable.intrinsicHeight, Bitmap.Config.ARGB_8888
    ) ?: return null
    val canvas = Canvas(bitmap)
    drawable.setBounds(0, 0, canvas.width, canvas.height)
    drawable.draw(canvas)
    return bitmap
}