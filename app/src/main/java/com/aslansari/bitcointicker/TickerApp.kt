package com.aslansari.bitcointicker

import android.app.Application
import com.aslansari.bitcointicker.di.AppComponent
import com.aslansari.bitcointicker.di.AppModule
import com.aslansari.bitcointicker.di.DaggerAppComponent
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class TickerApp: Application() {

    val appComponent: AppComponent = DaggerAppComponent
        .builder()
        .appModule(AppModule(this))
        .build()

    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)
        FirebaseAuth.getInstance().useEmulator("192.168.1.21", 9099)
        FirebaseFirestore.getInstance().useEmulator("192.168.1.21", 8082)
    }
}