package com.example.yitimages.data

import com.example.yitimages.api.RetrofitApi.mRetrofitApi
import com.example.yitimages.datamodels.ImagesResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response

class ImagesRepository: IImagesRepository {

    override suspend fun searchImages(query: String, pageNum: Int): Flow<Response<ImagesResponse>> {
        return flow {
            emit(mRetrofitApi.imageSearch(query, pageNum))
        }
    }
}