package com.aslansari.bitcointicker.favourite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import com.aslansari.bitcointicker.BaseFragment
import com.aslansari.bitcointicker.account.ui.LoginUIState
import com.aslansari.bitcointicker.account.ui.LoginViewModel
import com.aslansari.bitcointicker.account.ui.LoginViewModelFactory
import com.aslansari.bitcointicker.databinding.FragmentFavouriteListBinding
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import javax.inject.Inject

class FavouriteListFragment: BaseFragment() {

    @Inject
    lateinit var loginViewModelFactory: LoginViewModelFactory
    private val loginViewModel: LoginViewModel by activityViewModels(
        factoryProducer = { loginViewModelFactory }
    )
    private lateinit var binding: FragmentFavouriteListBinding
    private val signInLauncher = registerForActivityResult(
        FirebaseAuthUIActivityResultContract()
    ) { result ->
        loginViewModel.onSignInResult(result)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModelComponent.inject(this)
        // Inflate the layout for this fragment
        binding = FragmentFavouriteListBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.buttonLogin.setOnClickListener {
            val signInIntent = loginViewModel.getSignInIntent()
            signInLauncher.launch(signInIntent)
        }
        loginViewModel.loginUIState.observe(viewLifecycleOwner) {
            if (it is LoginUIState.Result) {
                if (it.isLoggedIn) {
                    binding.buttonLogin.isVisible = false
                    binding.imageLogin.isVisible = false
                    binding.favouriteList.isVisible = true
                    // todo make a call to Favourites ViewModel
                }
            }
        }
        loginViewModel.init()
    }
}