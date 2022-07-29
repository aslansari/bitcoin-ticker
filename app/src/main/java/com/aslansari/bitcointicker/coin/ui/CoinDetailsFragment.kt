package com.aslansari.bitcointicker.coin.ui

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.NavigationUI
import com.aslansari.bitcointicker.BaseDialogFragment
import com.aslansari.bitcointicker.R
import com.aslansari.bitcointicker.databinding.FragmentCoinDetailBinding
import com.aslansari.bitcointicker.favourite.ui.FavouriteCoin
import com.aslansari.bitcointicker.ui.util.DisplayColorUtil
import com.aslansari.bitcointicker.ui.util.DisplayTextUtil
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.abs

// todo input field that use can define the refresh interval of current price
class CoinDetailsFragment : BaseDialogFragment() {

    @Inject
    lateinit var coinDetailsViewModelFactory: CoinDetailsViewModelFactory
    private val coinDetailsViewModel: CoinDetailsViewModel by viewModels(
        factoryProducer = { coinDetailsViewModelFactory }
    )
    private val args: CoinDetailsFragmentArgs by navArgs()

    private lateinit var binding: FragmentCoinDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.AppTheme)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModelComponent.inject(this)
        // Inflate the layout for this fragment
        binding = FragmentCoinDetailBinding.inflate(layoutInflater, container, false)
        binding.apply {
            NavigationUI.setupWithNavController(toolbar, findNavController())
            toolbar.title = args.name
            toolbar.setOnMenuItemClickListener {
                val isFav = it.isChecked
                val state = coinDetailsViewModel.coinDetailsUIState.value
                if (state is CoinDetailsUIState.Result) {
                    val favCoin = FavouriteCoin(state.id, state.name, state.symbol)
                    if (isFav) {
                        coinDetailsViewModel.removeFromFavourites(favCoin)
                        Toast.makeText(requireContext(), getString(R.string.removed_from_favourites), Toast.LENGTH_SHORT).show()
                    } else {
                        coinDetailsViewModel.saveToFavourites(favCoin)
                        Toast.makeText(requireContext(), getString(R.string.added_to_favourites), Toast.LENGTH_SHORT).show()
                    }
                    it.isChecked = it.isChecked.not()
                }
                return@setOnMenuItemClickListener true
            }
        }
        lifecycleScope.launch {
            val isCurrentFav = coinDetailsViewModel.isCoinFav(args.id)
            binding.toolbar.menu.findItem(R.id.favourite).isChecked = isCurrentFav
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        coinDetailsViewModel.fetch(args.id)
        coinDetailsViewModel.coinDetailsUIState.observe(viewLifecycleOwner) { state ->
            binding.progressBar.isVisible = state is CoinDetailsUIState.Loading
            when (state) {
                is CoinDetailsUIState.Result -> {
                    binding.textFieldCoinPrice.text =
                        DisplayTextUtil.Amount.getDollarAmount(state.priceUSD)
                    bindRoiData(state.roiLastDay)
                    bindDescription(state.description)
                    bindHashAlgorithm(state.hashAlgorithm)
                    state.imageUrl?.let {
                        Glide.with(requireContext())
                            .load(it)
                            .addListener(object : RequestListener<Drawable> {
                                override fun onLoadFailed(
                                    e: GlideException?,
                                    model: Any?,
                                    target: Target<Drawable>?,
                                    isFirstResource: Boolean
                                ): Boolean {
                                    binding.imageCoin.isVisible = false
                                    return false
                                }

                                override fun onResourceReady(
                                    resource: Drawable?,
                                    model: Any?,
                                    target: Target<Drawable>?,
                                    dataSource: DataSource?,
                                    isFirstResource: Boolean
                                ): Boolean {
                                    return false
                                }
                            })
                            .into(binding.imageCoin)
                    }
                }
                else -> {}
            }
        }
        coinDetailsViewModel.favIconState.observe(viewLifecycleOwner) { enabled ->
            binding.toolbar.menu.findItem(R.id.favourite).isEnabled = enabled
        }
    }

    private fun bindDescription(description: String?) {
        description?.let {
            binding.textFieldDescription.text = it
            binding.textFieldDescriptionTitle.isVisible = true
            binding.textFieldDescription.isVisible = true
        }
    }

    private fun bindHashAlgorithm(hashAlgorithm: String?) {
        hashAlgorithm?.let {
            binding.textFieldHashAlgorithm.text = it
            binding.textFieldHashAlgorithmTitle.isVisible = true
            binding.textFieldHashAlgorithm.isVisible = true
        }
    }

    private fun bindRoiData(roiLastDay: Double) {
        if (roiLastDay.isNaN().not()) {
            if (roiLastDay > 0) {
                binding.roiChip.isVisible = true
                val chipBackgroundColor = DisplayColorUtil.getGainColor()
                val chipTextColor = DisplayColorUtil.getGainTextColor(resources)
                binding.roiChip.setChipBackgroundColorResource(chipBackgroundColor)
                binding.roiChip.setTextColor(chipTextColor)
                binding.roiChip.text = getString(
                    R.string.positive_rate, DisplayTextUtil.Amount.getRateFormat(
                        abs(roiLastDay)
                    )
                )
            } else if (roiLastDay < 0) {
                binding.roiChip.isVisible = true
                val chipBackgroundColor = DisplayColorUtil.getLossColor(requireContext())
                val chipTextColor = DisplayColorUtil.getLossTextColor(requireContext(), resources)
                binding.roiChip.setChipBackgroundColorResource(chipBackgroundColor)
                binding.roiChip.setTextColor(chipTextColor)
                binding.roiChip.text = getString(
                    R.string.negative_rate, DisplayTextUtil.Amount.getRateFormat(
                        abs(roiLastDay)
                    )
                )
            }
        }

    }
}