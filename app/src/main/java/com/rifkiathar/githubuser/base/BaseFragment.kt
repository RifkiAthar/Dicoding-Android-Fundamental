package com.rifkiathar.githubuser.base

import android.content.Context
import android.os.Bundle
import android.os.SystemClock
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.viewbinding.ViewBinding
import com.rifkiathar.githubuser.R
import com.rifkiathar.githubuser.di.sharedPreferences.utils.SharedPreferenceManager
import com.rifkiathar.githubuser.utils.DiffCallback
import com.rifkiathar.githubuser.utils.getColorCompat
import org.koin.android.ext.android.inject


abstract class BaseFragment<T: ViewBinding>(
    private val bindingInflater: (layoutInflater: LayoutInflater) -> T
) : Fragment(){

    protected val preferenceManager: SharedPreferenceManager by inject()
    private var _binding: T? = null
    protected val binding get() = _binding!!

    protected val diffCallback: DiffCallback by inject()
    protected var lastClickedTime = 0L

//    private val dataStore: SettingPreferences by inject()

    protected fun <T> LiveData<T>.onResult(action: (T) -> Unit) {
        observe(this@BaseFragment) { data -> data?.let(action) }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = bindingInflater.invoke(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        hideToolbar()
        onViewReady(savedInstanceState)
        observeData()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    protected abstract fun onViewReady(savedInstanceState: Bundle?)
    protected abstract fun observeData()

    protected fun isNotSafeToClick(): Boolean {
        if (SystemClock.elapsedRealtime() - lastClickedTime < DEBOUNCE_TIME) {
            return true
        }
        lastClickedTime = SystemClock.elapsedRealtime()
        return false
    }

    fun setupToolbarProperties(
        toolbarId: Toolbar,
        title: String,
        @DrawableRes drawable: Int = R.drawable.ic_back
    ) {
        (activity as AppCompatActivity).run {
            setSupportActionBar(toolbarId)
            supportActionBar?.let {
                toolbarId.title = title
                toolbarId.setTitleTextColor(
                    requireContext().getColorCompat(
                        R.color.white
                    )
                )
                it.setDisplayHomeAsUpEnabled(true)
                it.setDisplayShowHomeEnabled(true)
                it.setHomeAsUpIndicator(drawable)
            }
        }
    }

    protected fun setToolbarTitle(titleBar: String) {
        (activity as AppCompatActivity).supportActionBar?.title = titleBar
    }

    protected fun hideToolbar(isHide: Boolean = false) {
        (activity as AppCompatActivity).supportActionBar?.let {
            if (isHide) {
                it.hide()
            } else {
                it.show()
            }
        }
    }

    companion object {
        private const val DEBOUNCE_TIME = 1000
    }

}