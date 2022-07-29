package com.aslansari.bitcointicker.favourite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.aslansari.bitcointicker.BaseFragment
import com.aslansari.bitcointicker.R
import com.aslansari.bitcointicker.account.ui.LoginUIState
import com.aslansari.bitcointicker.account.ui.LoginViewModel
import com.aslansari.bitcointicker.account.ui.LoginViewModelFactory
import com.aslansari.bitcointicker.coin.ui.MarginItemDecorator
import com.aslansari.bitcointicker.databinding.FragmentFavouriteListBinding
import com.aslansari.bitcointicker.favourite.ui.FavouriteListAdapter
import com.aslansari.bitcointicker.favourite.ui.FavouritesUIState
import com.aslansari.bitcointicker.favourite.ui.FavouritesViewModel
import com.aslansari.bitcointicker.favourite.ui.FavouritesViewModelFactory
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import javax.inject.Inject

class FavouriteListFragment : BaseFragment() {

    @Inject
    lateinit var loginViewModelFactory: LoginViewModelFactory
    private val loginViewModel: LoginViewModel by activityViewModels(
        factoryProducer = { loginViewModelFactory }
    )

    @Inject
    lateinit var favouritesViewModelFactory: FavouritesViewModelFactory
    private val favouritesViewModel: FavouritesViewModel by activityViewModels(
        factoryProducer = { favouritesViewModelFactory }
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
        binding.apply {
            favouriteList.layoutManager =
                LinearLayoutManager(requireActivity(), RecyclerView.VERTICAL, false)
            val verticalMargin = resources.getDimensionPixelSize(R.dimen.currency_margin_vertical)
            val horizontalMargin =
                resources.getDimensionPixelSize(R.dimen.currency_margin_horizontal)
            favouriteList.addItemDecoration(MarginItemDecorator(verticalMargin, horizontalMargin))
            favouriteList.adapter = FavouriteListAdapter()
        }
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
                    favouritesViewModel.fetch()
                }
            }
        }
        favouritesViewModel.favouritesUIState.observe(viewLifecycleOwner) { state ->
            binding.progressBar.isVisible = state is FavouritesUIState.Loading
            when (state) {
                is FavouritesUIState.Result -> {
                    (binding.favouriteList.adapter as FavouriteListAdapter).submitList(state.favourites)
                }
                is FavouritesUIState.Error -> {
                    // todo handle errors
                }
                else -> {}
            }
        }

        loginViewModel.init()
    }
}