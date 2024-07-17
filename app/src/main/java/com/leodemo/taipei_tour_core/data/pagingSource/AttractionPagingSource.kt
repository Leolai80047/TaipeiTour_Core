package com.leodemo.taipei_tour.data.pagingSource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.leodemo.taipei_tour.data.api.AttractionResponse
import com.leodemo.taipei_tour.data.local.sharePreference.ShareLocalDataSource
import com.leodemo.taipei_tour.data.repository.attraction.AttractionInteractor
import javax.inject.Inject

class AttractionPagingSource @Inject constructor(
    private val attractionRepository: AttractionInteractor,
    private val sharePreferenceDataSource: ShareLocalDataSource
) : PagingSource<Int, AttractionResponse.Data>() {
    private val page = 1
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, AttractionResponse.Data> {
        val position = params.key ?: page
        return try {
            val data = attractionRepository.fetchAttractions(
                lang = sharePreferenceDataSource.lastLanguage,
                page = position
            )
            LoadResult.Page(
                data = data,
                prevKey = if (position == 1) null else position - 1,
                nextKey = if (data.isEmpty()) null else (position + 1)
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, AttractionResponse.Data>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}