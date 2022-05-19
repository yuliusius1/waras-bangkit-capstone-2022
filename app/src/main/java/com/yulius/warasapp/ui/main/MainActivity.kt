package com.yulius.warasapp.ui.main

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.yulius.warasapp.R
import com.yulius.warasapp.data.model.User
import com.yulius.warasapp.data.model.UserPreference
import com.yulius.warasapp.databinding.ActivityMainBinding
import com.yulius.warasapp.ui.auth.login.LoginActivity
import com.yulius.warasapp.util.ViewModelFactory

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
    name = "settings"
)

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var mainViewModel: MainViewModel
    private lateinit var user: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupViewModel()
        setupFragment()
    }

    private fun setupFragment() {
        val navView: BottomNavigationView = binding.navView

        val navController =
            findNavController(R.id.nav_host_fragment_activity_main)
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home,
                R.id.navigation_diagnose,
                R.id.navigation_article,
                R.id.navigation_profile
            )
        )
        setupActionBarWithNavController(
            navController,
            appBarConfiguration
        )
        navView.setupWithNavController(navController)
    }

    private fun setupViewModel() {
        mainViewModel = ViewModelProvider(
            this,
            ViewModelFactory(UserPreference.getInstance(dataStore))
        )[MainViewModel::class.java]

        mainViewModel.getUser().observe(this) {
            user = User(
                it.full_name,
                it.username,
                it.email,
                it.password,
                it.telephone,
                it.date_of_birth,
                it.isLogin,
                it.created_at,
                it.updated_at,
                it.id
            )
            if (!it.isLogin) {
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }
        }

        mainViewModel.getThemeSettings().observe(this
        ) { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }
    }


}
