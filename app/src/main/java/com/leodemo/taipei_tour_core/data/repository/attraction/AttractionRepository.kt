package com.leodemo.taipei_tour.data.repository.attraction

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.leodemo.taipei_tour.data.api.AttractionApi
import com.leodemo.taipei_tour.data.api.AttractionResponse
import com.leodemo.taipei_tour.data.local.sharePreference.ShareLocalDataSource
import com.leodemo.taipei_tour.data.pagingSource.AttractionPagingSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AttractionRepository @Inject constructor(
    private val attractionApi: AttractionApi,
    private val sharePreferenceDataSource: ShareLocalDataSource
) : AttractionInteractor {
    override suspend fun fetchAttractions(language: String): List<AttractionResponse.Data> {
        return attractionApi.fetchAttractionList(lang = language).data
    }

    override fun getAttractionPagingSource(): Flow<PagingData<AttractionResponse.Data>> {
        return Pager(
            config = PagingConfig(
                pageSize = 10,
                enablePlaceholders = false,
                initialLoadSize = 5
            ),
            pagingSourceFactory = {
                AttractionPagingSource(attractionApi, sharePreferenceDataSource)
            },
            initialKey = 1,
        ).flow
    }
}