package com.example.yitimages.data

import com.example.yitimages.datamodels.ImagesResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface IImagesRepository {

    suspend fun searchImages(query: String, pageNum: Int = 1): Flow<Response<ImagesResponse>>
}