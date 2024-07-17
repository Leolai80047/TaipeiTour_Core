package com.leodemo.taipei_tour.data.repository.attraction

import androidx.paging.PagingData
import com.leodemo.taipei_tour.data.api.AttractionResponse
import kotlinx.coroutines.flow.Flow

interface AttractionInteractor {
    suspend fun fetchAttractions(lang: String, page: Int): List<AttractionResponse.Data>
    fun getAttractionPagingSource(): Flow<PagingData<AttractionResponse.Data>>
}