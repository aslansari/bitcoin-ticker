package com.aslansari.bitcointicker.account.domain

import com.aslansari.bitcointicker.account.data.AccountRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val accountRepository: AccountRepository
) {

    fun isLoggedIn(): Boolean {
        return accountRepository.isLoggedIn()
    }
}