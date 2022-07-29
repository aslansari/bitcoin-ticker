package com.aslansari.bitcointicker.coin.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuCompat
import androidx.core.view.MenuItemCompat
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.aslansari.bitcointicker.BaseFragment
import com.aslansari.bitcointicker.R
import com.aslansari.bitcointicker.databinding.FragmentCoinListBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

class CoinListFragment : BaseFragment() {

    @Inject
    lateinit var coinListViewModelFactory: CoinListViewModelFactory
    private val coinListViewModel: CoinListViewModel by activityViewModels(
        factoryProducer = { coinListViewModelFactory }
    )

    private lateinit var binding: FragmentCoinListBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModelComponent.inject(this)
        // Inflate the layout for this fragment
        binding = FragmentCoinListBinding.inflate(layoutInflater, container, false)
        binding.apply {
            NavigationUI.setupWithNavController(toolbar, findNavController())
            coinList.layoutManager =
                LinearLayoutManager(requireActivity(), RecyclerView.VERTICAL, false)
            val verticalMargin = resources.getDimensionPixelSize(R.dimen.currency_margin_vertical)
            val horizontalMargin = resources.getDimensionPixelSize(R.dimen.currency_margin_horizontal)
            coinList.addItemDecoration(MarginItemDecorator(verticalMargin, horizontalMargin))
            coinList.adapter = CoinListAdapter()
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launchWhenStarted {
            coinListViewModel.coinListFlow().collectLatest {
                (binding.coinList.adapter as CoinListAdapter).submitList(it)
            }
        }
        lifecycleScope.launchWhenCreated {
            coinListViewModel.registerSearch(binding.toolbar.menu.findItem(R.id.search).actionView as SearchView).collect {
                (binding.coinList.adapter as CoinListAdapter).submitList(it)
            }
        }
        (binding.coinList.adapter as CoinListAdapter).itemClickListener = { item ->
            val directions = CoinListFragmentDirections.goToDetails(id = item.id, name = item.name)
            findNavController().navigate(directions)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        (binding.coinList.adapter as CoinListAdapter).itemClickListener = null
        binding.coinList.adapter = null
    }
}