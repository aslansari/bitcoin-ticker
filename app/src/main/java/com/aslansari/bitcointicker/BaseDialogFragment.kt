package com.aslansari.bitcointicker

import androidx.fragment.app.DialogFragment
import com.aslansari.bitcointicker.di.viewmodel.ViewModelModule

open class BaseDialogFragment: DialogFragment() {
    protected val activityComponent by lazy { (requireActivity() as BaseActivity).activityComponent }

    protected val viewModelComponent by lazy {
        activityComponent.newViewModelComponent(ViewModelModule())
    }
}