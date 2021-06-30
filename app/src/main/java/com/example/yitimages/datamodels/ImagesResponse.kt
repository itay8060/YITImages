package com.example.yitimages.datamodels

data class ImagesResponse(
    val hits: List<ImageResult>,
    val total: Int,
    val totalHits: Int
)