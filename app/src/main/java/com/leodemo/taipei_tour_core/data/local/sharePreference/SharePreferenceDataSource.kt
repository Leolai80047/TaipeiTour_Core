package com.leodemo.taipei_tour.data.local.sharePreference

import android.content.SharedPreferences
import com.leodemo.taipei_tour_core.BuildConfig
import javax.inject.Inject

class SharePreferenceDataSource @Inject constructor(
    private val sharedPreferences: SharedPreferences
) : ShareLocalDataSource {

    companion object {
        const val LAST_LANGUAGE = "lastLanguage"
    }

    override var lastLanguage: String
        get() = getString(LAST_LANGUAGE, BuildConfig.DEFAULT_LANGUAGE)
        set(value) {
            setString(LAST_LANGUAGE, value)
        }


    private fun setString(key: String, value: String) {
        sharedPreferences.edit()
            .putString(key, value)
            .apply()
    }

    private fun getString(key: String, default: String = ""): String {
        return sharedPreferences.getString(key, default) ?: default
    }
}