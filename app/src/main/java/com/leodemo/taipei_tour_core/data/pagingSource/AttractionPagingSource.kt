package com.leodemo.taipei_tour.data.pagingSource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.leodemo.taipei_tour.data.api.AttractionApi
import com.leodemo.taipei_tour.data.api.AttractionResponse
import com.leodemo.taipei_tour.data.local.sharePreference.ShareLocalDataSource
import javax.inject.Inject

class AttractionPagingSource @Inject constructor(
    private val attractionApi: AttractionApi,
    private val sharePreferenceDataSource: ShareLocalDataSource
) : PagingSource<Int, AttractionResponse.Data>() {
    var page = 1
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, AttractionResponse.Data> {
        val position = params.key ?: page
        return try {
            val data = attractionApi.fetchAttractionList(
                lang = sharePreferenceDataSource.lastLanguage,
                page = position
            ).data
            LoadResult.Page(
                data = data,
                prevKey = if (page == 1) null else page - 1,
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