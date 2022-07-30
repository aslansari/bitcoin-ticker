package com.aslansari.bitcointicker.coin.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.aslansari.bitcointicker.BaseDialogFragment
import com.aslansari.bitcointicker.R
import com.aslansari.bitcointicker.databinding.DialogFetchIntervalBinding
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

class FetchIntervalDialogFragment : BaseDialogFragment() {

    @Inject
    lateinit var fetchIntervalViewModelFactory: FetchIntervalViewModelFactory
    private val fetchIntervalViewModel: FetchIntervalViewModel by viewModels(
        factoryProducer = { fetchIntervalViewModelFactory }
    )

    private lateinit var binding: DialogFetchIntervalBinding
    private val textChangeListener = object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) { }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            binding.textFieldFetchInterval.error = null
        }

        override fun afterTextChanged(p0: Editable?) { }
    }

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
        binding = DialogFetchIntervalBinding.inflate(layoutInflater, container, false)
        binding.apply {
            NavigationUI.setupWithNavController(toolbar, findNavController())
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launchWhenStarted {
            fetchIntervalViewModel.getInterval().collectLatest {
                binding.textFieldFetchInterval.editText?.setText(it.toString())
            }
        }
        fetchIntervalViewModel.fetchIntervalUIState.observe(viewLifecycleOwner) { state ->
            binding.progressBar.isVisible = state is FetchIntervalUIState.Loading
            when (state) {
                is FetchIntervalUIState.Result -> {
                    dismiss()
                }
                is FetchIntervalUIState.Error -> {}
                else -> {}
            }
        }

        binding.buttonDiscard.setOnClickListener { dismiss() }
        binding.buttonSaveChanges.setOnClickListener {
            val text = binding.textFieldFetchInterval.editText?.text.toString()
            if (text.isNotBlank()) {
                val interval = text.toLong()
                fetchIntervalViewModel.update(interval)
            } else {
                binding.textFieldFetchInterval.error =
                    getText(R.string.error_field_should_not_be_empty)
            }
        }

        binding.textFieldFetchInterval.editText?.addTextChangedListener(textChangeListener)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.textFieldFetchInterval.editText?.removeTextChangedListener(textChangeListener)
    }
}