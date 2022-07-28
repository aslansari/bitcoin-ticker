package com.aslansari.bitcointicker.account.data

import com.google.firebase.auth.FirebaseAuth
import javax.inject.Inject

class AccountRepository @Inject constructor(
    private val auth: FirebaseAuth
){

    fun isLoggedIn(): Boolean {
        return auth.currentUser != null
    }

}