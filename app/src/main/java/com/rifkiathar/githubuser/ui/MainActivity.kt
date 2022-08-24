package com.rifkiathar.githubuser.ui

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.MenuProvider
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.rifkiathar.githubuser.R
import com.rifkiathar.githubuser.base.BaseActivity
import com.rifkiathar.githubuser.databinding.ActivityMainBinding
import com.rifkiathar.githubuser.di.dataStore.DataStoreViewModel
import com.rifkiathar.githubuser.di.dataStore.DataStoreViewModelFactory
import com.rifkiathar.githubuser.di.dataStore.SettingPreferences



class MainActivity : BaseActivity() {
    private lateinit var navigationController: NavController

    private lateinit var binding: ActivityMainBinding

    private var flagDarkModeActive = false

    override fun onSetupLayout(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onViewReady(savedInstanceState: Bundle?) {
        navigationController = findNavController(R.id.nav_host_fragment)
    }

    override fun observeData() {
    }

    override fun onSupportNavigateUp() = navigationController.navigateUp()
}