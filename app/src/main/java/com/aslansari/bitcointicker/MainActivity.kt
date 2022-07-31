package com.aslansari.bitcointicker

import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.work.*
import com.aslansari.bitcointicker.coin.domain.PeriodicCoinWorker
import com.aslansari.bitcointicker.databinding.ActivityMainBinding
import java.util.concurrent.TimeUnit

class MainActivity : BaseActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val navController = findNavController(R.id.nav_host_fragment)
        NavigationUI.setupWithNavController(binding.bottomNavBarMain, navController)

        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .setRequiresBatteryNotLow(true)
            .build()
        val coinCheckWorkRequest: WorkRequest =
            PeriodicWorkRequestBuilder<PeriodicCoinWorker>(20L, TimeUnit.MINUTES)
                .setInitialDelay(1L, TimeUnit.MINUTES)
                .setConstraints(constraints)
                .build()
        WorkManager.getInstance(this).enqueue(coinCheckWorkRequest)
    }
}