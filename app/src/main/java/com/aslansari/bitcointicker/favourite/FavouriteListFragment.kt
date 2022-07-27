package com.aslansari.bitcointicker.favourite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.aslansari.bitcointicker.BaseFragment
import com.aslansari.bitcointicker.R
import com.aslansari.bitcointicker.databinding.FragmentFavouriteListBinding

class FavouriteListFragment : BaseFragment() {

    private lateinit var binding: FragmentFavouriteListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModelComponent.inject(this)
        // Inflate the layout for this fragment
        binding = FragmentFavouriteListBinding.inflate(layoutInflater, container, false)
        return binding.root
    }
}