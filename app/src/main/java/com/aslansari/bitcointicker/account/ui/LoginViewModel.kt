package com.aslansari.bitcointicker.account.ui

import android.app.Activity.RESULT_OK
import android.content.Intent
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.aslansari.bitcointicker.R
import com.aslansari.bitcointicker.account.domain.LoginUseCase
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject
import javax.inject.Provider

class LoginViewModel(
    private val loginUseCase: LoginUseCase,
    private val coroutineDispatcher: CoroutineDispatcher,
): ViewModel() {

    private val _loginUIState = MutableLiveData<LoginUIState>()
    val loginUIState = _loginUIState as LiveData<LoginUIState>

    fun init() {
        _loginUIState.value = LoginUIState.Loading
        val isLoggedIn = loginUseCase.isLoggedIn()
        _loginUIState.value = LoginUIState.Result(isLoggedIn)
    }

    // add providers if necessary
    private val providers = arrayListOf(AuthUI.IdpConfig.EmailBuilder().build())

    fun getSignInIntent(): Intent {
        return AuthUI.getInstance()
            .createSignInIntentBuilder()
            .setAvailableProviders(providers)
            .setTheme(R.style.AppTheme)
            .build()
    }

    fun onSignInResult(result: FirebaseAuthUIAuthenticationResult) {
        if (result.resultCode == RESULT_OK) {
            _loginUIState.value = LoginUIState.Result(true)
        } else {
            _loginUIState.value = LoginUIState.Error
        }
    }
}

@Suppress("UNCHECKED_CAST")
class LoginViewModelFactory @Inject constructor(
    private val loginUseCaseProvider: Provider<LoginUseCase>,
    private val coroutineDispatcher: CoroutineDispatcher,
): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            return LoginViewModel(loginUseCaseProvider.get(), coroutineDispatcher) as T
        }
        return super.create(modelClass)
    }

}
