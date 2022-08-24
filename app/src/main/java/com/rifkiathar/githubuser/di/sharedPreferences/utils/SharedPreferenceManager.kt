package com.rifkiathar.githubuser.di.sharedPreferences.utils

import android.content.SharedPreferences
import com.rifkiathar.githubuser.utils.TextUtils.BLANK

class SharedPreferenceManager(prefs: SharedPreferences) {
    var darkMode: Boolean by SharedPreferencesData(prefs, SharedPreferencesKey.MODE, false)
}