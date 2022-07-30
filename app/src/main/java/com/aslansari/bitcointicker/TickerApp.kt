package com.aslansari.bitcointicker

import android.app.Application
import com.aslansari.bitcointicker.di.AppComponent
import com.aslansari.bitcointicker.di.AppModule
import com.aslansari.bitcointicker.di.DaggerAppComponent
import com.google.firebase.FirebaseApp

class TickerApp: Application() {

    val appComponent: AppComponent = DaggerAppComponent
        .builder()
        .appModule(AppModule(this))
        .build()

    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)
    }
}