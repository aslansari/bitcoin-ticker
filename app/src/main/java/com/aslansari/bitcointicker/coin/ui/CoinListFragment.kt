package com.aslansari.bitcointicker.coin.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.aslansari.bitcointicker.databinding.FragmentCoinListBinding

class CoinListFragment : Fragment() {

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
        // Inflate the layout for this fragment
        binding = FragmentCoinListBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

}