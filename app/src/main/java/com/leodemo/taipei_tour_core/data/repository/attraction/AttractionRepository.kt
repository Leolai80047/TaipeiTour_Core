package com.leodemo.taipei_tour.data.repository.attraction

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.leodemo.taipei_tour.data.api.AttractionApi
import com.leodemo.taipei_tour.data.api.AttractionResponse
import com.leodemo.taipei_tour.data.local.sharePreference.ShareLocalDataSource
import com.leodemo.taipei_tour.data.pagingSource.AttractionPagingSource
import com.leodemo.taipei_tour_core.data.retrofit.getOrNull
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AttractionRepository @Inject constructor(
    private val attractionApi: AttractionApi,
    private val sharePreferenceDataSource: ShareLocalDataSource
) : AttractionInteractor {
    override suspend fun fetchAttractions(lang: String, page: Int): List<AttractionResponse.Data> {
        return attractionApi.fetchAttractionList(lang = lang, page = page).getOrNull()?.data
            ?: listOf()
    }

    override fun getAttractionPagingSource(): Flow<PagingData<AttractionResponse.Data>> {
        return Pager(
            config = PagingConfig(
                pageSize = 10,
                enablePlaceholders = false,
                initialLoadSize = 3
            ),
            pagingSourceFactory = {
                AttractionPagingSource(this, sharePreferenceDataSource)
            },
            initialKey = 1,
        ).flow
    }
}