package com.aslansari.bitcointicker

import androidx.appcompat.app.AppCompatActivity
import com.aslansari.bitcointicker.di.AppComponent
import com.aslansari.bitcointicker.di.activity.ActivityComponent
import com.aslansari.bitcointicker.di.activity.ActivityModule
import com.aslansari.bitcointicker.di.activity.DaggerActivityComponent

open class BaseActivity: AppCompatActivity() {
    private val appComponent: AppComponent get() = (application as TickerApp).appComponent

    val activityComponent: ActivityComponent by lazy {
        DaggerActivityComponent.builder()
            .appComponent(appComponent)
            .activityModule(ActivityModule())
            .build()
    }
}