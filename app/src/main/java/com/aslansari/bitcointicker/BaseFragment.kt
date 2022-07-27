package com.aslansari.bitcointicker

import androidx.fragment.app.Fragment
import com.aslansari.bitcointicker.di.viewmodel.ViewModelModule

open class BaseFragment: Fragment() {
    protected val activityComponent by lazy { (requireActivity() as BaseActivity).activityComponent }

    protected val viewModelComponent by lazy {
        activityComponent.newViewModelComponent(ViewModelModule())
    }
}