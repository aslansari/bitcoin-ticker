package com.aslansari.bitcointicker

import android.app.Application
import com.aslansari.bitcointicker.di.AppComponent
import com.aslansari.bitcointicker.di.AppModule
import com.aslansari.bitcointicker.di.DaggerAppComponent

class TickerApp: Application() {

    val appComponent: AppComponent = DaggerAppComponent
        .builder()
        .appModule(AppModule())
        .build()
}