package com.rifkiathar.githubuser.base

import android.content.Context
import android.os.Bundle
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.rifkiathar.githubuser.di.sharedPreferences.utils.SharedPreferenceManager
import com.rifkiathar.githubuser.utils.DiffCallback
import org.koin.android.ext.android.inject

abstract class BaseActivity : AppCompatActivity() {

    protected val preferenceManager: SharedPreferenceManager by inject()
    protected val diffCallback: DiffCallback by inject()

    protected fun <T> LiveData<T>.onResult(action: (T) -> Unit) {
        observe(this@BaseActivity, Observer {data -> data?.let(action)})
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onSetupLayout(savedInstanceState)
        onViewReady(savedInstanceState)
        observeData()
    }

    fun setupToolbarProperties(
        toolbarId: Toolbar,
        tvTitle: TextView? = null,
        @StringRes title: Int,
        @DrawableRes drawable: Int?
    ) {
        setSupportActionBar(toolbarId)
        supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(false)
            it.setDisplayShowTitleEnabled(
                null != drawable
            )
            it.setDisplayShowHomeEnabled(null != drawable)
            drawable?.let { iconUp ->
                it.setHomeAsUpIndicator(iconUp)
            }
        }
        tvTitle?.setText(title)
    }

    fun setupToolbarProperties(
        toolbarId: Toolbar,
        tvTitle: TextView? = null,
        title: String,
        @DrawableRes drawable: Int?
    ) {
        setSupportActionBar(toolbarId)
        supportActionBar?.let {
            it.setDisplayShowTitleEnabled(false)
            it.setDisplayHomeAsUpEnabled(
                null != drawable
            )
            it.setDisplayShowHomeEnabled(null != drawable)
            drawable?.let { iconUp ->
                it.setHomeAsUpIndicator(iconUp)
            }
        }
        tvTitle?.text = title
    }

    protected abstract fun onSetupLayout(savedInstanceState: Bundle?)
    protected abstract fun onViewReady(savedInstanceState: Bundle?)
    protected abstract fun observeData()
}