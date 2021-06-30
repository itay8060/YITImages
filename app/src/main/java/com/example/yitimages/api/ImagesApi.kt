package com.example.yitimages.api

import com.example.yitimages.datamodels.ImagesResponse
import com.example.yitimages.utils.Utils.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ImagesApi {

    @GET("/api/")
    suspend fun imageSearch(
        @Query("q") searchQuery : String,
        @Query("page") pageNum : Int = 1,
        @Query("key") apiKey : String = API_KEY
    ): Response<ImagesResponse>
}