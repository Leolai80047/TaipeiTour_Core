package com.leodemo.taipei_tour.data.api

data class AttractionResponse(
    val data: List<Data>
) {
    data class Data(
        val id: String,
        val name: String,
        val introduction: String,
        val address: String,
        val url: String,
        val modified: String,
        private val images: List<ImageSrc>
    ) {
        data class ImageSrc(
            val src: String,
        )

        fun getImage(): String? {
            return images.firstOrNull()?.src
        }
    }
}
