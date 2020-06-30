package com.teegarcs.ime_animation

import android.os.Build
import android.os.Bundle
import android.view.WindowInsets
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.teegarcs.ime_animation.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val mainViewModel: MainViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        binding.viewModel = mainViewModel
        binding.lifecycleOwner = this
        setContentView(binding.root)

        val navController = findNavController(R.id.nav_host_fragment)
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        binding.navView.setupWithNavController(navController)


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            //only required if using the KeyboardInsetListener
            window.setDecorFitsSystemWindows(false)
            //only receives callbacks when the inset affects my window.
            binding.container.addKeyboardInsetListener(mainViewModel.keyboardCallback)
        }
    }
}