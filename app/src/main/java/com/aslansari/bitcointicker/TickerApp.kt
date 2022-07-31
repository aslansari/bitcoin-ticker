package com.aslansari.bitcointicker

import android.app.Application
import android.content.Context
import androidx.work.*
import com.aslansari.bitcointicker.coin.domain.PeriodicCoinWorker
import com.aslansari.bitcointicker.di.AppComponent
import com.aslansari.bitcointicker.di.AppModule
import com.aslansari.bitcointicker.di.DaggerAppComponent
import com.google.firebase.FirebaseApp
import dagger.Component
import javax.inject.Inject

class TickerApp: Application() {

    @Inject
    lateinit var coinWorkerFactory: CoinWorkerFactory
    private lateinit var workerComponent: WorkerComponent

    val appComponent: AppComponent = DaggerAppComponent
        .builder()
        .appModule(AppModule(this))
        .build()

    override fun onCreate() {
        workerComponent = DaggerWorkerComponent.builder().appComponent(appComponent).build()
        workerComponent.injectTo(this)
        super.onCreate()
        FirebaseApp.initializeApp(this)

        val workManagerConfig = Configuration.Builder()
            .setWorkerFactory(coinWorkerFactory)
            .build()
        WorkManager.initialize(this, workManagerConfig)
    }
}

class CoinWorkerFactory @Inject constructor(
    private val coinWorkerFactory: PeriodicCoinWorker.Factory,
): WorkerFactory() {

    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters
    ): ListenableWorker? {
        return when (workerClassName) {
            PeriodicCoinWorker::class.java.name -> {
                coinWorkerFactory.create(appContext, workerParameters)
            }
            else -> null
        }
    }
}

@Component(dependencies = [AppComponent::class])
interface WorkerComponent {
    fun injectTo(app: TickerApp)
}