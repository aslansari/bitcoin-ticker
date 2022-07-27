package com.aslansari.bitcointicker.coin.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.aslansari.bitcointicker.BaseFragment
import com.aslansari.bitcointicker.R
import com.aslansari.bitcointicker.databinding.FragmentCoinListBinding
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
        coinListViewModel.coinListLiveData.observe(viewLifecycleOwner) {
            (binding.coinList.adapter as CoinListAdapter).submitList(it)
        }
        coinListViewModel.getCoins()
    }

}