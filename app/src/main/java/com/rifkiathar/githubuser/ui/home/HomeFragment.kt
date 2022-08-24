package com.rifkiathar.githubuser.ui.home

import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.inputmethod.EditorInfo
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.bundleOf
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.core.view.isVisible
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.rifkiathar.githubuser.R
import com.rifkiathar.githubuser.base.BaseFragment
import com.rifkiathar.githubuser.base.GeneralRecyclerView
import com.rifkiathar.githubuser.databinding.FragmentHomeBinding
import com.rifkiathar.githubuser.databinding.ItemUserBinding
import com.rifkiathar.githubuser.di.dataStore.DataStoreViewModel
import com.rifkiathar.githubuser.di.dataStore.DataStoreViewModelFactory
import com.rifkiathar.githubuser.di.dataStore.SettingPreferences
import com.rifkiathar.githubuser.model.response.UserResponse
import com.rifkiathar.githubuser.ui.detail.DetailFragment
import com.rifkiathar.githubuser.utils.GridItemDecorations
import com.rifkiathar.githubuser.utils.loadImage
import com.rifkiathar.githubuser.utils.shortToast
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    private val viewModel: HomeViewModel by viewModel()
    private var flagDarkMode = false

    private val searchAdapter by lazy {
        GeneralRecyclerView<UserResponse, ItemUserBinding>(
            diffCallback = diffCallback,
            itemBindingInflater = ItemUserBinding::inflate,
            onBind = { user, _, binding ->
                binding.ivAvatar.loadImage(user.avatarUrl)
                binding.tvUserName.text = user.login
            },
            itemListener = { user, _, _ ->
                findNavController().navigate(
                    R.id.action_homeFragment_to_detailFragment,
                    bundleOf(DetailFragment.USER to user.login)
                )
            }
        )
    }


    override fun onViewReady(savedInstanceState: Bundle?) {
        initMenu()
        initSearchListener()
        initRecyclerView()
    }

    override fun observeData() {
        with(viewModel) {
            observeSearchResponse().onResult { data ->
                if (data != null) {
                    binding.rvSearch.isVisible = true
                    searchAdapter.updateData(data)
                } else {
                    binding.tvDataNotFound.isVisible = true
                }
            }
            observeDataCount().onResult { data ->
                if (data != null) {
                    with(binding.tvDataCount){
                        isVisible = true
                        text = data.toString()
                    }
                    binding.tvDataText.isVisible = true
                } else {
                    binding.tvDataCount.isVisible = false
                    binding.tvDataText.isVisible = false
                }
            }
            observeLoading().onResult {
                binding.progressbar.isVisible = it
            }
            observeError().onResult {
                activity?.shortToast(it.toString())
            }
        }
    }

    private fun initRecyclerView() {
        with(binding.rvSearch) {
            layoutManager = LinearLayoutManager(context)
            adapter = searchAdapter
            addItemDecoration(GridItemDecorations(SPACE_DIVIDER, 1))
            setHasFixedSize(true)
        }
    }

    private fun initSearchListener() {
        with(binding.layoutSearchBar) {
            edtSearch.setOnEditorActionListener { _, action, _ ->
                var handled = false

                if (action == EditorInfo.IME_ACTION_SEARCH) {
                    viewModel.searchUser(edtSearch.text.toString())
                    Timber.d("SEARCH: ${edtSearch.text.toString()}")
                    handled = true
                }
                handled
            }
        }
    }

    private fun initMenu() {

        val pref = context?.let { SettingPreferences.getInstance(it.dataStore) }

        val dataStoreViewModel = pref?.let { data ->
            ViewModelProvider(this, DataStoreViewModelFactory(data)).get(
                DataStoreViewModel::class.java
            )
        }

        dataStoreViewModel?.getThemeSettings()?.observe(this
        ) { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                flagDarkMode = true
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                flagDarkMode = false
            }
        }

        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object: MenuProvider{
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.main_menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.action_home_setting -> {
                        if (flagDarkMode) {
                            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                            dataStoreViewModel?.saveThemeSetting(!flagDarkMode)
                        } else {
                            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                            dataStoreViewModel?.saveThemeSetting(!flagDarkMode)
                        }
                        true
                    }
                    R.id.action_home_favorite -> {
                        findNavController().navigate(R.id.action_homeFragment_to_favoriteFragment)
                        true
                    }
                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    companion object {
        private const val SPACE_DIVIDER = 10
    }
}