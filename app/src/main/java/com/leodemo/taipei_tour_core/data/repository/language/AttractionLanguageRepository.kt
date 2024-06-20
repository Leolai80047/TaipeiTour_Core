package com.leodemo.taipei_tour.data.repository.language

import com.leodemo.taipei_tour.data.local.sharePreference.ShareLocalDataSource
import javax.inject.Inject

class AttractionLanguageRepository @Inject constructor(
    private val sharePreferenceDataSource: ShareLocalDataSource
) : AttractionLanguageInteractor {
    override fun getLastLanguage(): String {
        return sharePreferenceDataSource.lastLanguage
    }

    override fun setLastLanguage(value: String) {
        sharePreferenceDataSource.lastLanguage = value
    }
}