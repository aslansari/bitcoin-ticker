package com.aslansari.bitcointicker.account.ui

sealed class LoginUIState {
    object Error: LoginUIState()
    data class Result(
        val isLoggedIn: Boolean,
    ): LoginUIState()
    object Loading: LoginUIState()
}