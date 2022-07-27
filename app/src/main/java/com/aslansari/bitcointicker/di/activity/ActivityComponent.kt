package com.aslansari.bitcointicker.di.activity

import com.aslansari.bitcointicker.di.AppComponent
import com.aslansari.bitcointicker.di.viewmodel.ViewModelComponent
import com.aslansari.bitcointicker.di.viewmodel.ViewModelModule
import dagger.Component

@ActivityScope
@Component(modules = [ActivityModule::class], dependencies = [AppComponent::class])
interface ActivityComponent {

    fun newViewModelComponent(viewModelModule: ViewModelModule): ViewModelComponent
}